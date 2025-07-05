package com.progartisan.module.knowledgebase.knowledge.model;

import io.leanddd.component.common.Util;
import io.leanddd.component.data.BaseEntity;
import io.leanddd.component.meta.Meta;
import io.leanddd.component.meta.Meta.Type;
import io.leanddd.component.meta.MetaEntity;
import io.leanddd.component.data.EntityHelper;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static io.leanddd.component.meta.Meta.BooleanEx.False;
import static io.leanddd.component.meta.Meta.BooleanEx.True;

@MetaEntity(tableName = "kb_knowledge")
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Knowledge extends BaseEntity<Knowledge> {

    private static final EntityHelper<Knowledge> entityHelper = EntityHelper
            .<Knowledge>getInstance(Knowledge.class);

    @Meta(value = Type.ID, label = "知识项ID")
    private String knowledgeId;

    @Meta(value = Type.String, label = "标题", nullable = False, searchable = True, editable = True)
    private String title;

    @Meta(value = Type.Text, label = "内容", nullable = False, editable = True)
    private String content;

    @Meta(value = Type.ToMany, label = "标签列表")
    private Set<KnowledgeTag> tags;

    @MetaEntity(tableName = "kb_knowledge_tag")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class KnowledgeTag implements Serializable {
        @Meta(Type.ID)
        private String id;

        @Meta(value = Type.RefID, nullable = False)
        private String knowledgeId;

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
    public void update(Object knowledge) {
        entityHelper.update(this, knowledge);
    }

    public void addTag(Tag tag, String tagType) {
        if (tags == null) {
            tags = new HashSet<>();
        }
        KnowledgeTag knowledgeTag = KnowledgeTag.builder().tagId(tag.getTagId()).knowledgeId(this.knowledgeId).build();
        tags.add(knowledgeTag);
    }

    public void removeTag(String tagId) {
        if (tags != null) {
            tags.removeIf(tag -> tag.getTagId().equals(tagId));
        }
    }

    public String getTitleTagId() {
        var titleTagId = this.tags.stream().filter(tag -> Objects.equals(tag.getTagType(), "title")).findFirst().map(KnowledgeTag::getTagId).orElse(null);
        return titleTagId;
    }


}
