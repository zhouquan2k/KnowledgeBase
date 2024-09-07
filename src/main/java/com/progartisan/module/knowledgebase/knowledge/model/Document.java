package com.progartisan.module.knowledgebase.knowledge.model;

import com.progartisan.component.data.BaseEntity;
import com.progartisan.component.framework.helper.EntityHelper;
import com.progartisan.component.meta.Meta;
import com.progartisan.component.meta.Meta.Type;
import com.progartisan.component.meta.MetaEntity;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static com.progartisan.component.meta.Meta.BooleanEx.False;
import static com.progartisan.component.meta.Meta.BooleanEx.True;

@MetaEntity(tableName = "document")
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Builder
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

    @Meta(value = Type.Text, label = "原始输入")
    private String rawInput;

    @Meta(value = Type.Text, label = "需求")
    private String requirement;

    @Meta(value = Type.Text, label = "UI设计")
    private String uiDesign;

    @Meta(value = Type.Text, label = "设计")
    private String design;

    @Meta(value = Type.Text, label = "任务设计")
    private String taskDesign;

    @Meta(value = Type.ToMany, label = "标签列表")
    private Set<DocumentTag> tags;

    @MetaEntity(tableName = "document_tag")
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

        @Meta(value = Type.Integer, hidden = True)
        private Integer index;

        @Meta(value = Type.None)
        private Tag tag;
    }

    @Override
    public void update(Document document) {
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
