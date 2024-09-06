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

    // 按tagPath和order顺序获取标签树数据
    List<Tag> queryTagTree();

    // 查子tag
    List<Tag> getChildrenTags(String tagId);

    Tag getTag(String tagId);

    Knowledge getKnowledge(String knowledgeId);

    // 批量删除某个tagId的所有知识-标签
    void removeTagFromAllKnowledge(@Param("tagId") String tagId);

    // 按tag名精确找到tags
    List<Tag> getTagsByName(@Param("tagName") String tagName);
}
