## 设计思路

- 在现有的知识库模块中，增加文档导入功能。
- 扫描指定目录及其子目录下的所有文件，根据文件名后缀将文件内容导入数据库中相应字段。
    - *_task.md -> rawInput
    - *_req.md -> requirement
    - *_design_ui.md -> uiDesign
    - *_design.md -> design
    - *_design_tasks.md -> taskDesign
- 提供API接口供前端调用，实现文档的导入、查看和管理。
- 增加Project相关的API接口，支持获取Project列表和Project详情。
- 需要增加Tags表里面的project属性，用于标识Tag所属的Project。
- 需要重构Tags API - GET /api/knowledge/tags/tree, 为 GET /api/knowledge/tags/{project}/tree

## 模块

project-name: knowledgebase
module-name: knowledge

## API设计

### Endpoints

- POST /api/knowledge/documents/{project}/import
    - Operation: importDocuments
    - Description: 从指定目录导入文档
    - PathParam: project

- GET /api/knowledge/documents/{project}
    - Operation: getDocuments
    - Description: 获取文档列表
    - PathParam: project
    - QueryParam: tagName
    - Response:
        - type: array
        - items:
            - $ref: '#/components/schemas/Document'

- GET /api/knowledge/documents/id/{documentId}
    - Operation: getDocument
    - Description: 获取文档详情
    - parameters:
        - name: documentId
          in: path
          required: true
          schema:
          type: string
    - Response:
        - $ref: '#/components/schemas/Document'

- GET /api/knowledge/projects
    - Operation: getProjects
    - Description: 获取Project列表
    - Response:
        - type: array
        - items:
            - $ref: '#/components/schemas/Project'

- GET /api/knowledge/projects/{projectName}
    - Operation: getProject
    - Description: 获取Project详情
    - parameters:
        - name: projectName
          in: path
          required: true
          schema:
          type: string
    - Response:
        - $ref: '#/components/schemas/Project'

- GET /api/knowledge/tags/{project}/tree
    - Operation: getTagTree 获取tag树
    - Response: List<Tag>
    - Description: 获取属于Project的tag树数据

### Schema

```yaml
Document:
  type: object
  properties:
    documentId:
      type: string
      description: 文档ID
    documentName:
      type: string
      description: 文档名称
    documentPath:
      type: string
      description: 文档路径
    project:
      type: string
      description: 项目名称
    rawInput:
      type: string
      description: 原始输入
    requirement:
      type: string
      description: 需求
    uiDesign:
      type: string
      description: UI设计
    design:
      type: string
      description: 设计
    taskDesign:
      type: string
      description: 任务设计
    tags:
      type: array
      items:
        $ref: '#/components/schemas/DocumentTag'
      description: "标签列表"

DocumentTag:
  type: object
  properties:
    documentId:
      type: string
      description: 文档ID
    tagId:
      type: string
      description: 标签ID

Project:
  type: object
  properties:
    projectName:
      type: string
      description: 项目名称
    projectDesc:
      type: string
      description: 项目描述
    projectPath:
      type: string
      description: 项目路径

Tag:
  type: object
  properties:
    tagId:
      type: string
      description: 标签ID
    tagName:
      type: string
      description: 标签名称
    parentTagId:
      type: string
      description: 父标签ID
    project:
      type: string
      description: 项目名称
    order:
      type: integer
      description: 标签顺序
```

## Artifacts

| 变更模块      | backend/frontend | 变更文件类型       | 变更文件                            | 需要变更? |
|-----------|------------------|--------------|---------------------------------|-------|
| knowledge | backend          | api          | api/KnowledgeService.java       | 是     |
| knowledge | backend          | service_impl | model/KnowledgeServiceImpl.java | 是     |
| knowledge | backend          | domain       | model/Document.java             | 是     |
| knowledge | backend          | domain       | model/Project.java              | 是     |
| knowledge | backend          | domain       | model/Tag.java                  | 是     |
| knowledge | backend          | mapper       | infra/KnowledgeMapper.java      | 是     |
| knowledge | backend          | mapper_xml   | infra/KnowledgeMapper.xml       | 是     |
| knowledge | frontend         | api_js       | knowledge_api.js                | 是     |
| knowledge | frontend         | ui_vue       | document.vue                    | 是     |

## 开发相关

- 无