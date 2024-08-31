package com.progartisan.module.knowledgebase.knowledge.model;

import com.progartisan.component.data.BaseEntity;
import com.progartisan.component.meta.Meta;
import com.progartisan.component.meta.Meta.Type;
import com.progartisan.component.meta.MetaEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.progartisan.component.meta.Meta.BooleanEx.False;

@MetaEntity(tableName = "tag", defaultUpdatable = true)
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Tag extends BaseEntity<Tag> {

    @Meta(value = Type.ID, label = "标签ID")
    private String tagId;

    @Meta(value = Type.String, label = "标签名称", nullable = False)
    private String tagName;

    @Meta(value = Type.Integer, label = "标签顺序")
    private Integer order;

    @Override
    public void update(Tag tag) {
        this.tagName = tag.tagName;
        this.order = tag.order;
    }
}
