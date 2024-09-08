package com.progartisan.module.knowledgebase.knowledge.model;

import com.progartisan.component.common.Util;
import com.progartisan.component.data.BaseEntity;
import com.progartisan.component.meta.Meta;
import com.progartisan.component.meta.Meta.Type;
import com.progartisan.component.meta.MetaEntity;
import com.progartisan.component.framework.helper.EntityHelper;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static com.progartisan.component.meta.Meta.BooleanEx.False;
import static com.progartisan.component.meta.Meta.BooleanEx.True;

@MetaEntity(tableName = "knowledge")
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Knowledge extends BaseEntity<Knowledge> {

    private static final EntityHelper<Knowledge> entityHelper = EntityHelper
            .<Knowledge>getInstance(Knowledge.class);

    @Meta(value = Type.ID, label = "知识项ID")
    private String knowledgeId;

    @Meta(value = Type.String, label = "标题", nullable = False, searchable = True, updatable = True)
    private String title;

    @Meta(value = Type.Text, label = "内容", nullable = False, updatable = True)
    private String content;

    @Meta(value = Type.ToMany, label = "标签列表")
    private Set<KnowledgeTag> tags;

    @MetaEntity(tableName = "knowledge_tag")
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

        @Meta(value = Type.None)
        private Tag tag;
    }

    @Override
    public void update(Knowledge knowledge) {
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
        var titleTagId = this.tags.stream().filter(tag -> Util.equals(tag.getTagType(), "title")).findFirst().map(KnowledgeTag::getTagId).orElse(null);
        return titleTagId;
    }


}
