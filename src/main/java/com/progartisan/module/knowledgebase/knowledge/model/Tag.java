package com.progartisan.module.knowledgebase.knowledge.model;

import io.leanddd.component.common.Util;
import io.leanddd.component.data.BaseEntity;
import io.leanddd.component.meta.Meta;
import io.leanddd.component.meta.Meta.Type;
import io.leanddd.component.meta.MetaEntity;
import com.progartisan.module.knowledgebase.knowledge.api.KnowledgeService;
import com.progartisan.module.knowledgebase.knowledge.api.KnowledgeService.MoveType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static io.leanddd.component.meta.Meta.BooleanEx.False;

@MetaEntity(tableName = "kb_tag", defaultUpdatable = true)
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class Tag extends BaseEntity<Tag> {

    @Meta(value = Type.ID, label = "标签ID")
    private String tagId;

    @Meta(value = Type.String, label = "标签名称", nullable = False)
    private String tagName;

    @Meta(value = Type.RefID, label = "父标签ID")
    private String parentTagId;

    @Meta(value = Type.String, label = "项目名称", nullable = False)
    private String project;

    @Meta(value = Type.Integer, label = "标签顺序")
    private Integer order;

    public int getOrder() {
        return order == null ? 0 : order;
    }

    // as a cache, need to be updated when rename
    @Meta(value = Type.String, label = "完整路径", length = 1000)
    private String fullPath;

    @Meta(persistable = False)
    private Tag parent;

    public Tag(Tag tag) {
        this.tagName = tag.tagName;
        this.order = tag.order;
        this.parentTagId = tag.parentTagId;
        this.parent = tag.parent;
        this.project = tag.project;
        calculate();
    }

    public void resetOrder(int order) {
        this.order = order;
    }

    public void calculate() {
        this.fullPath = (this.parent != null && Util.isNotEmpty(this.parent.fullPath)) ?
                this.parent.fullPath + "/" + this.tagName : this.tagName;
    }

    public void rename(String newName) {
        this.tagName = newName;
        calculate();
    }

    public void move(MoveType moveType, Tag refTag) {
        if (moveType == MoveType.Inner) {
            this.parentTagId = refTag.getTagId();
            this.parent = refTag;
        } else if (moveType == MoveType.Before) {
            this.parentTagId = refTag.getParentTagId();
            this.order = refTag.getOrder() - 1;
            this.parent = refTag.getParent();
        } else if (moveType == MoveType.After) {
            this.parentTagId = refTag.getParentTagId();
            this.order = refTag.order + 1;
            this.parent = refTag.getParent();
        }
        calculate();
    }

    public void enrichParent(Tag parent) {
        this.parent = parent;
        calculate();
    }
}
