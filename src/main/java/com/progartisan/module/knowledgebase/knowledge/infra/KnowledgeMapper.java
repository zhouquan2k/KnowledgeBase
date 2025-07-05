package com.progartisan.module.knowledgebase.knowledge.infra;

import io.leanddd.component.data.BaseMapper;
import com.progartisan.module.knowledgebase.knowledge.model.Knowledge;
import com.progartisan.module.knowledgebase.knowledge.model.Tag;
import com.progartisan.module.knowledgebase.knowledge.model.Document;
import com.progartisan.module.knowledgebase.knowledge.model.Project;
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
    List<Tag> queryTagTree(@Param("projectName") String projectName);

    // 查子tag
    List<Tag> getChildrenTags(String project, String tagId);

    Tag getTag(String tagId);

    Knowledge getKnowledge(String knowledgeId);

    // 批量删除某个tagId的所有知识-标签
    void removeTagFromAllKnowledge(@Param("tagId") String tagId);

    // 按tag名精确找到tags
    List<Tag> getTagsByName(@Param("tagName") String tagName);

    // 按tagName查询文档列表
    List<Document> queryDocuments(@Param("project") String project, @Param("tagName") String tagName);

    // 按documentId查询文档详情
    Document getDocument(@Param("documentId") String documentId);

    // 查所有项目
    List<Project> queryAllProjects();

    // 按projectName查项目详情
    Project getProject(@Param("projectName") String projectName);

    Knowledge getKnowledgeByTitleTagId(String tagId);

    Document getDocumentByProjectAndName(String project, String baseName);
}
