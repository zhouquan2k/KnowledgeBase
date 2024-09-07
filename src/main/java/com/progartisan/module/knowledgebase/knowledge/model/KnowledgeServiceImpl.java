package com.progartisan.module.knowledgebase.knowledge.model;

import com.progartisan.component.common.Util;
import com.progartisan.component.framework.Command;
import com.progartisan.component.framework.Query;
import com.progartisan.component.framework.Repository;
import com.progartisan.component.framework.Service;
import com.progartisan.component.framework.helper.CrudServiceImpl2;
import com.progartisan.module.knowledgebase.knowledge.api.KnowledgeService;
import com.progartisan.module.knowledgebase.knowledge.infra.KnowledgeMapper;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Named;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

@Service(value = "知识管理", name = "knowledge")
@Named
@Slf4j
public class KnowledgeServiceImpl extends CrudServiceImpl2<Knowledge> implements KnowledgeService {

    private final Repository<Tag> tagRepository;
    private final Repository<Knowledge> knowledgeRepository;
    private final Repository<Document> documentRepository;
    private final KnowledgeMapper knowledgeMapper;

    public KnowledgeServiceImpl(Repository<Knowledge> knowledgeRepository, Repository<Tag> tagRepository, Repository<Document> documentRepository, KnowledgeMapper knowledgeMapper) {
        super(knowledgeRepository);
        this.tagRepository = tagRepository;
        this.knowledgeRepository = knowledgeRepository;
        this.documentRepository = documentRepository;
        this.knowledgeMapper = knowledgeMapper;
    }

    @Override
    @Command
    public Knowledge saveKnowledge(Knowledge knowledge, String currentTagId) {
        if (Util.isEmpty(knowledge.getKnowledgeId())) {
            knowledge = super.create(knowledge);
            var parentTag = Util.isNotEmpty(currentTagId) ? tagRepository.get(currentTagId).orElseThrow() : null;
            addTagToKnowledge(knowledge.getKnowledgeId(), Tag.builder().tagName(knowledge.getTitle()).parentTagId(currentTagId).parent(parentTag).build());
            return knowledge;
        } else {
            return super.update(knowledge.getKnowledgeId(), knowledge);
        }
    }

    @Override
    @Query
    public Knowledge getKnowledge(String knowledgeId) {
        return knowledgeMapper.getKnowledge(knowledgeId);
    }

    @Override
    @Query
    public List<Knowledge> queryKnowledge(String tagId) {
        return knowledgeMapper.queryKnowledgeByTag(tagId);
    }

    @Override
    @Query
    public List<Tag> getMatchedTags(String text) {
        return knowledgeMapper.getMatchedTags(text);
    }

    @Override
    @Command
    public Tag createTag(Tag tag) {
        if (tag.getTagName().indexOf('/') >= 0) {
            // 分段构建tag
            String[] segments = tag.getTagName().split("/");
            Tag lastParent = tag.getParent();
            Tag newTag = null;
            for (var segment : segments) {
                tag = Tag.builder().tagName(segment).parentTagId(lastParent.getTagId()).parent(lastParent).build();
                newTag = tagRepository.create(tag);
                newTag = (Tag) tagRepository.save(newTag);
                lastParent = newTag;
            }
            return newTag;
        }
        var newTag = tagRepository.create(tag);
        return (Tag) tagRepository.save(newTag);
    }

    @Override
    @Command
    public Knowledge addTagToKnowledge(String knowledgeId, Tag tag) {
        Knowledge knowledge = knowledgeRepository.get(knowledgeId).orElseThrow();
        Tag theTag = null;
        if (Util.isEmpty(tag.getTagId())) {
            theTag = createTag(tag);
        } else { // reuse a tag exist
            theTag = tagRepository.get(tag.getTagId()).orElseThrow();
        }
        knowledge.addTag(theTag);
        return (Knowledge) knowledgeRepository.save(knowledge);
    }

    @Override
    @Command
    public Knowledge removeTagFromKnowledge(String knowledgeId, String tagId) {
        Knowledge knowledge = knowledgeRepository.get(knowledgeId).orElseThrow();
        knowledge.removeTag(tagId);
        return (Knowledge) knowledgeRepository.save(knowledge);
    }

    @Override
    @Query
    public List<Tag> getTagTree(String project) {
        return knowledgeMapper.queryTagTree(project);
    }

    @Override
    @Command
    public Tag updateTag(String tagId, Tag tag) {
        // Tag existingTag = tagRepository.get(tagId).orElseThrow();
        Tag existingTag = knowledgeMapper.getTag(tagId);
        existingTag.rename(tag.getTagName());
        var returnTag = (Tag) tagRepository.save(existingTag);
        this.updateChildrenFullPath(existingTag);
        return returnTag;
    }

    @Override
    @Command
    public void deleteTag(String tagId) {
        Tag tag = tagRepository.get(tagId).orElseThrow();
        knowledgeMapper.removeTagFromAllKnowledge(tagId);
        tagRepository.remove(tagId);
    }

    @Override
    @Command
    public void moveTag(String tagId, MoveType moveType, Tag refInputTag) {
        Tag existingTag = tagRepository.get(tagId).orElseThrow();
        var refTag = tagRepository.get(refInputTag.getTagId()).orElseThrow();
        var parentTag = moveType == MoveType.Inner ? refTag : tagRepository.get(refTag.getParentTagId()).orElseThrow();
        existingTag.move(moveType, refTag);
        tagRepository.save(existingTag);
        this.updateChildrenFullPath(parentTag);
    }

    // adjust order and full path
    private void updateChildrenFullPath(Tag tag) {
        List<Tag> children = knowledgeMapper.getChildrenTags(tag.getTagId());
        for (int i = 0; i < children.size(); i++) {
            var child = children.get(i);
            var theTag = knowledgeMapper.getTag(child.getTagId());
            theTag.resetOrder((i + 1) * 10);
            theTag.calculate();
            tagRepository.save(theTag);
            updateChildrenFullPath(child);
        }
    }

    @Override
    @Command
    public void deleteKnowledge(String knowledgeId) {
        Knowledge knowledge = knowledgeRepository.get(knowledgeId).orElseThrow();
        knowledgeRepository.remove(knowledgeId);
    }

    @Override
    @Command
    public void importDocuments(String project) {
        var theProject = knowledgeMapper.getProject(project);
        Util.check(theProject != null, "invalid project name", project);
        try {
            Map<String, List<Path>> fileGroups = new HashMap<>();
            Path projectPath = Path.of(theProject.getProjectPath());

            // 首先对文件进行分组
            Files.walk(projectPath)
                    .filter(Files::isRegularFile)
                    .filter(path -> {
                        String fileName = path.getFileName().toString();
                        return fileName.endsWith(".md") || fileName.endsWith(".yaml");
                    })
                    .forEach(filePath -> {
                        String fileName = filePath.getFileName().toString();
                        String baseName = fileName.split("_")[0];
                        fileGroups.computeIfAbsent(baseName, k -> new ArrayList<>()).add(filePath);
                    });

            // 处理每个文件分组
            for (Map.Entry<String, List<Path>> entry : fileGroups.entrySet()) {
                String baseName = entry.getKey();
                List<Path> files = entry.getValue();

                Document.DocumentBuilder builder = Document.builder()
                        .project(project)
                        .documentName(baseName);

                // 获取相对路径
                Path relativePath = projectPath.relativize(files.get(0).getParent());
                builder.documentPath(relativePath.toString());

                for (Path filePath : files) {
                    String fileName = filePath.getFileName().toString();
                    try {
                        String content = Files.readString(filePath, StandardCharsets.UTF_8);
                        if (fileName.endsWith("_task.md")) {
                            builder.rawInput(content);
                        } else if (fileName.endsWith("_req.md")) {
                            builder.requirement(content);
                        } else if (fileName.endsWith("_design_ui.md")) {
                            builder.uiDesign(content);
                        } else if (fileName.endsWith("_design.md")) {
                            builder.design(content);
                        } else if (fileName.endsWith("_design_tasks.yaml")) {
                            builder.taskDesign(content);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException("Error reading file: " + filePath, e);
                    }
                }

                Document document = builder.build();
                documentRepository.save(document);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error walking file tree", e);
        }
    }

    @Override
    @Query
    public List<Document> getDocuments(String project, String tagName) {
        return knowledgeMapper.queryDocuments(project, tagName);
    }

    @Override
    @Query
    public Document getDocument(String documentId) {
        return knowledgeMapper.getDocument(documentId);
    }

    @Override
    @Query
    public List<Project> getProjects() {
        return knowledgeMapper.queryAllProjects();
    }

    @Override
    @Query
    public Project getProject(String projectName) {
        return knowledgeMapper.getProject(projectName);
    }
}
