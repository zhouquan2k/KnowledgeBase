## 设计思路
- 在后端，我们需要创建新的API端点来支持知识项的创建、编辑、查询和tag管理。
- 在前端，我们需要设计一个知识管理界面，支持知识项的创建、编辑、查询和tag管理。

## 模块
project-name: knowledgebase
module-name: knowledge

## API设计

### Endpoints
- POST /api/knowledge
Operation: saveKnowledge 保存知识项
BodyParam: Knowledge
Response: Knowledge
Description: 保存新建/修改的知识项

- GET /api/knowledge
Operation: queryKnowledge 查询知识项
QueryParam: tag
Response: List<Knowledge>
Description: 根据按tag返回匹配的知识项列表，tag为空返回所有知识项

- GET /api/knowledge/tags
Operation: getMatchedTags 获取tag匹配
QueryParam: text
Response: List<Tag>
Description: 根据用户输入的文本，返回匹配的已有tag

- POST /api/knowledge/tags
Operation: createTag 创建tag
BodyParam: Tag
Response: Tag
Description: 在用户输入的tag不存在时自动创建新tag

### Schema
``` yaml
Knowledge:
  type: object
  properties:
    knowledgeId:
      type: string
      description: "知识项ID"
    title:
      type: string
      description: "标题"
    content:
      type: string
      description: "内容"
    tags:
      type: array
      items:
        $ref: '#/components/schemas/Tag'
      description: "标签列表"

Tag:
  type: object
  properties:
    tagId:
      type: string
      description: "标签ID"
    name:
      type: string
      description: "标签名称"
    order:
      type: integer
      description: "标签顺序"
```

## Artifacts
| 变更模块 | backend/frontend | 变更文件类型       | 变更文件                            | 需要变更? |
| --- | --- |--------------|---------------------------------| --- |
| knowledge | backend | api          | api/KnowledgeService.java       | 是 |
| knowledge | backend | domain       | model/Knowledge.java            | 是 |
| knowledge | backend | domain       | model/Tag.java                  | 是 |
| knowledge | backend | service_impl | model/KnowledgeServiceImpl.java | 是 |
| knowledge | frontend | api_js       | knowledge_api.js                | 是 |
| knowledge | frontend | ui_vue       | knowledge.vue                   | 是 |
| knowledge | backend | mapper       | infra/KnowledgeMapper.java      | 是 |
| knowledge | backend | mapper_xml   | infra/KnowledgeMapper.xml       | 是 |