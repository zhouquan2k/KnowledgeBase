package com.progartisan.module.knowledgebase.knowledge.model;

import com.progartisan.component.data.BaseEntity;
import com.progartisan.component.meta.Meta;
import com.progartisan.component.meta.Meta.Type;
import com.progartisan.component.meta.MetaEntity;
import com.progartisan.component.framework.helper.EntityHelper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.progartisan.component.meta.Meta.BooleanEx.True;

@MetaEntity(tableName = "project", defaultUpdatable = true)
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
    public void update(Project project) {
        entityHelper.update(this, project);
    }
}
