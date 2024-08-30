package com.progartisan.module.knowledgebase.example.model;

import com.progartisan.component.data.BaseEntity;
import com.progartisan.component.framework.helper.EntityHelper;
import com.progartisan.component.meta.Meta;
import com.progartisan.component.meta.Meta.Type;
import com.progartisan.component.meta.MetaEntity;

import lombok.*;

import static com.progartisan.component.meta.Meta.BooleanEx.False;
import static com.progartisan.component.meta.Meta.BooleanEx.True;

@MetaEntity(tableName="assistant", defaultUpdatable = true)
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
    public void update(Assistant assistant) {
        entityHelper.update(this, assistant);
    }
}
