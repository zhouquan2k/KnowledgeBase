package com.progartisan.module.knowledgebase.knowledge.infra;

import com.progartisan.component.data.BaseMapper;
import com.progartisan.module.knowledgebase.knowledge.model.Knowledge;
import com.progartisan.module.knowledgebase.knowledge.model.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KnowledgeMapper extends BaseMapper<Knowledge> {

    // 按tag查询知识项列表
    List<Knowledge> queryKnowledgeByTag(String tagName);

    // 按text查询tags
    List<Tag> getMatchedTags(String text);

    Knowledge getKnowledge(String knowledgeId);
}
