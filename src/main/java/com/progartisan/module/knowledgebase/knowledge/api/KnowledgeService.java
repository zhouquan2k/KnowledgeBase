package com.progartisan.module.knowledgebase.knowledge.api;

import com.progartisan.component.framework.EnumDescription;
import com.progartisan.component.framework.EnumTag;
import com.progartisan.module.knowledgebase.knowledge.model.Knowledge;
import com.progartisan.module.knowledgebase.knowledge.model.Tag;
import com.progartisan.module.knowledgebase.knowledge.model.Document;
import com.progartisan.module.knowledgebase.knowledge.model.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/knowledge")
public interface KnowledgeService {

    @PostMapping
    Knowledge saveKnowledge(@RequestBody Knowledge knowledge, @RequestParam(required = false) String currentTagId);

    @GetMapping("/{knowledgeId}")
    Knowledge getKnowledge(@PathVariable String knowledgeId);

    @GetMapping
    List<Knowledge> queryKnowledge(@RequestParam(required = false) String tagId);

    @GetMapping("/tags")
    List<Tag> getMatchedTags(@RequestParam String text);

    @PostMapping("/tags")
    Tag createTag(@RequestBody Tag tag);

    @PostMapping("/{knowledgeId}/tags")
    Knowledge addTagToKnowledge(@PathVariable String knowledgeId, @RequestParam String tagType, @RequestBody Tag tag);

    @DeleteMapping("/{knowledgeId}/tags/{tagId}")
    Knowledge removeTagFromKnowledge(@PathVariable String knowledgeId, @PathVariable String tagId);

    @GetMapping("/tags/{project}/tree")
    List<Tag> getTagTree(@PathVariable String project);

    @PutMapping("/tags/{tagId}")
    Tag updateTag(@PathVariable String tagId, @RequestBody Tag tag);

    @DeleteMapping("/tags/{tagId}")
    void deleteTag(@PathVariable String tagId);

    @Getter
    @AllArgsConstructor
    public enum MoveType implements EnumDescription {
        Inner("inner"), Before("before"), After("after");
        private String desc;
    }

    @PutMapping("/tags/{tagId}/move/{moveType}")
    void moveTag(@PathVariable String tagId, @PathVariable MoveType moveType, @RequestBody Tag refTag);

    @DeleteMapping("/{knowledgeId}")
    void deleteKnowledge(@PathVariable String knowledgeId);

    @PostMapping("/documents/{project}/import")
    void importDocuments(@PathVariable String project);

    @GetMapping("/documents/{project}")
    List<Document> getDocuments(@PathVariable String project, @RequestParam(required = false) String tagName);

    @GetMapping("/documents/id/{documentId}")
    Document getDocument(@PathVariable String documentId);

    @GetMapping("/projects")
    List<Project> getProjects();

    @GetMapping("/projects/{projectName}")
    Project getProject(@PathVariable String projectName);
}
