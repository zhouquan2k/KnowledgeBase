package com.progartisan.module.knowledgebase.knowledge.model;

import io.leanddd.component.data.BaseEntity;
import io.leanddd.component.data.EntityHelper;
import io.leanddd.component.meta.Meta;
import io.leanddd.component.meta.Meta.Type;
import io.leanddd.component.meta.MetaEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static io.leanddd.component.meta.Meta.BooleanEx.False;
import static io.leanddd.component.meta.Meta.BooleanEx.True;

@MetaEntity(tableName = "kb_document")
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class Document extends BaseEntity<Document> {

    private static final EntityHelper<Document> entityHelper = EntityHelper
            .<Document>getInstance(Document.class);

    @Meta(value = Type.ID, label = "文档ID")
    private String documentId;

    @Meta(value = Type.String, label = "文档名称", nullable = False)
    private String documentName;

    @Meta(value = Type.String, label = "文档路径", nullable = True)
    private String documentPath;

    @Meta(value = Type.String, label = "项目名称", nullable = False)
    private String project;

    @Meta(value = Type.Text, label = "原始输入", editable = True)
    private String rawInput;

    @Meta(value = Type.Text, label = "需求", editable = True)
    private String requirement;

    @Meta(value = Type.Text, label = "UI设计", editable = True)
    private String uiDesign;

    @Meta(value = Type.Text, label = "设计", editable = True)
    private String design;

    @Meta(value = Type.Text, label = "任务设计", editable = True)
    private String taskDesign;

    @Meta(value = Type.ToMany, label = "标签列表")
    private Set<DocumentTag> tags;

    @MetaEntity(tableName = "kb_document_tag")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DocumentTag implements Serializable {
        @Meta(Type.ID)
        private String id;

        @Meta(value = Type.RefID, nullable = False)
        private String documentId;

        @Meta(value = Type.RefID, nullable = False)
        private String tagId;

        @Meta(value = Type.String)
        private String tagType;

        @Meta(value = Type.Integer, hidden = True)
        private Integer index;

        @Meta(persistable = False)
        private Tag tag;
    }

    @Override
    public void update(Object document) {
        entityHelper.update(this, document);
    }

    public void addTag(Tag tag) {
        if (tags == null) {
            tags = new HashSet<>();
        }
        DocumentTag documentTag = DocumentTag.builder().tagId(tag.getTagId()).documentId(this.documentId).build();
        tags.add(documentTag);
    }

    public void removeTag(String tagId) {
        if (tags != null) {
            tags.removeIf(tag -> tag.getTagId().equals(tagId));
        }
    }
}
