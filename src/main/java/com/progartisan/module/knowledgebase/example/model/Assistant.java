package com.progartisan.module.knowledgebase.example.model;

import io.leanddd.component.data.BaseEntity;
import io.leanddd.component.data.EntityHelper;
import io.leanddd.component.meta.Meta;
import io.leanddd.component.meta.Meta.Type;
import io.leanddd.component.meta.MetaEntity;

import lombok.*;

import static io.leanddd.component.meta.Meta.BooleanEx.False;
import static io.leanddd.component.meta.Meta.BooleanEx.True;

@MetaEntity(tableName="kb_assistant", defaultUpdatable = true)
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Assistant extends BaseEntity<Assistant> {

    private static final EntityHelper<Assistant> entityHelper = EntityHelper
            .<Assistant>getInstance(Assistant.class);

    @Meta(value = Type.ID)
    private String assistantId;

    @Meta(value = Type.String, label = "Assistant名称", nullable = False, searchable = True)
    private String assistantName;

    @Meta(value = Type.String, label = "描述")
    private String description;

    @Meta(value = Type.Text, label = "系统文本")
    private String systemText;

    @Override
    public void update(Object assistant) {
        entityHelper.update(this, assistant);
    }
}
