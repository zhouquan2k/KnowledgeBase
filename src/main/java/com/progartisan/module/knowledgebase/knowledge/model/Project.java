package com.progartisan.module.knowledgebase.knowledge.model;

import io.leanddd.component.data.BaseEntity;
import io.leanddd.component.meta.Meta;
import io.leanddd.component.meta.Meta.Type;
import io.leanddd.component.meta.MetaEntity;
import io.leanddd.component.data.EntityHelper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static io.leanddd.component.meta.Meta.BooleanEx.True;

@MetaEntity(tableName = "kb_project", defaultUpdatable = true)
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Project extends BaseEntity<Project> {

    private static final EntityHelper<Project> entityHelper = EntityHelper.getInstance(Project.class);

    @Meta(value = Type.IDStr, label = "项目名称", searchable = True)
    private String projectName;

    @Meta(value = Type.String, label = "项目描述")
    private String projectDesc;

    @Meta(value = Type.String, label = "项目路径")
    private String projectPath;

    @Override
    public void update(Object project) {
        entityHelper.update(this, project);
    }
}
