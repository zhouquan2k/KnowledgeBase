package com.progartisan.module.knowledgebase.knowledge.model;

import com.progartisan.component.common.Util;
import com.progartisan.component.framework.Command;
import com.progartisan.component.framework.Query;
import com.progartisan.component.framework.Repository;
import com.progartisan.component.framework.Service;
import com.progartisan.component.framework.helper.CrudServiceImpl2;
import com.progartisan.module.knowledgebase.example.infra.ExampleMapper;
import com.progartisan.module.knowledgebase.example.model.Example;
import com.progartisan.module.knowledgebase.knowledge.api.KnowledgeService;
import com.progartisan.module.knowledgebase.knowledge.infra.KnowledgeMapper;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Named;
import java.util.List;

@Service(value = "知识管理", name = "knowledge")
@Named
@Slf4j
public class KnowledgeServiceImpl extends CrudServiceImpl2<Knowledge> implements KnowledgeService {

    private final Repository<Tag> tagRepository;
    private final KnowledgeMapper knowledgeMapper;

    public KnowledgeServiceImpl(Repository<Knowledge> knowledgeRepository, Repository<Tag> tagRepository, KnowledgeMapper knowledgeMapper) {
        super(knowledgeRepository);
        this.tagRepository = tagRepository;
        this.knowledgeMapper = knowledgeMapper;
    }

    @Override
    @Command
    public Knowledge saveKnowledge(Knowledge knowledge) {
        if (Util.isEmpty(knowledge.getKnowledgeId())) {
            return super.create(knowledge);
        } else {
            return super.update(knowledge.getKnowledgeId(), knowledge);
        }
    }

    @Override
    @Query
    public List<Knowledge> queryKnowledge(String tag) {
        return knowledgeMapper.queryKnowledgeByTag(tag);
    }

    @Override
    @Query
    public List<Tag> getMatchedTags(String text) {
        return knowledgeMapper.getMatchedTags(text);
    }

    @Override
    @Command
    public Tag createTag(Tag tag) {
        var newTag = tagRepository.create(tag);
        return (Tag)tagRepository.save(newTag);
    }
}
