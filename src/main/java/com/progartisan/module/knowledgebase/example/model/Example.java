package com.progartisan.module.knowledgebase.example.model;

import io.leanddd.component.data.BaseEntity;
import io.leanddd.component.meta.Meta;
import io.leanddd.component.meta.Meta.Type;
import io.leanddd.component.meta.MetaEntity;
import io.leanddd.component.data.EntityHelper;
import lombok.*;

import static io.leanddd.component.meta.Meta.BooleanEx.False;
import static io.leanddd.component.meta.Meta.BooleanEx.True;

@MetaEntity(tableName="kb_example", defaultUpdatable = true)
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

    @Meta(value = Type.RefIDStr, label = "AssistantID", nullable = False, searchable = True, editable = True)
    private String assistantId;

    @Meta(value = Type.Text, label = "输入文本", nullable = False)
    private String inputText;

    @Meta(value = Type.Text, label = "输出文本", nullable = False)
    private String outputText;

    @Meta(value = Type.Text, label="原有内容")
    private String originContent;

    @Override
    public void update(Object example) {
        entityHelper.update(this, example);
    }
}
