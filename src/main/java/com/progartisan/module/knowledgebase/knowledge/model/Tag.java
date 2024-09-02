package com.progartisan.module.knowledgebase.knowledge.model;

import com.progartisan.component.common.Util;
import com.progartisan.component.data.BaseEntity;
import com.progartisan.component.meta.Meta;
import com.progartisan.component.meta.Meta.Type;
import com.progartisan.component.meta.MetaEntity;
import lombok.*;

import static com.progartisan.component.meta.Meta.BooleanEx.False;

@MetaEntity(tableName = "tag", defaultUpdatable = true)
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Tag extends BaseEntity<Tag> {

    @Meta(value = Type.ID, label = "标签ID")
    private String tagId;

    @Meta(value = Type.String, label = "标签名称", nullable = False)
    private String tagName;

    @Meta(value = Type.RefID, label = "父标签ID")
    private String parentTagId;

    @Meta(value = Type.Integer, label = "标签顺序")
    private Integer order;

    // as a cache, need to be updated when rename
    @Meta(value = Type.String, label = "完整路径")
    private String fullPath;

    @Meta(value = Type.None)
    private Tag parent;

    public Tag(Tag tag) {
        this.tagName = tag.tagName;
        this.order = tag.order;
        this.parentTagId = tag.parentTagId;
        this.parent = tag.parent;
        calculate();
    }

    public void calculate() {
        this.fullPath = (this.parent != null && Util.isNotEmpty(this.parent.fullPath)) ?
                this.parent.fullPath + "/" + this.tagName : this.tagName;
    }

    public void rename(String newName) {
        this.tagName = newName;
        calculate();
    }

    public void moveAsChild(Tag newParent) {
        this.parentTagId = newParent.getTagId();
        this.parent = newParent;
        calculate();
    }

    public void enrichParent(Tag parent) {
        this.parent = parent;
        calculate();
    }
}
