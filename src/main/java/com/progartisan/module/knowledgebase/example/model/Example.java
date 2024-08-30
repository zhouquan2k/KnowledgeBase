package com.progartisan.module.knowledgebase.example.model;

import com.progartisan.component.data.BaseEntity;
import com.progartisan.component.meta.Meta;
import com.progartisan.component.meta.Meta.Type;
import com.progartisan.component.meta.MetaEntity;
import com.progartisan.component.framework.helper.EntityHelper;
import lombok.*;

import static com.progartisan.component.meta.Meta.BooleanEx.False;
import static com.progartisan.component.meta.Meta.BooleanEx.True;

@MetaEntity(tableName="example", defaultUpdatable = true)
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Example extends BaseEntity<Example> {

    private static final EntityHelper<Example> entityHelper = EntityHelper.getInstance(Example.class);

    @Meta(value = Type.ID, label = "ExampleID")
    private String exampleId;

    @Meta(value = Type.String, label = "描述")
    private String description;

    @Meta(value = Type.RefIDStr, label = "AssistantID", nullable = False, searchable = True, updatable = True)
    private String assistantId;

    @Meta(value = Type.Text, label = "输入文本", nullable = False)
    private String inputText;

    @Meta(value = Type.Text, label = "输出文本", nullable = False)
    private String outputText;

    @Override
    public void update(Example example) {
        entityHelper.update(this, example);
    }
}
