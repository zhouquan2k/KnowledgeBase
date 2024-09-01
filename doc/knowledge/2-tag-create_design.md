## 设计思路
- 在后端，我们需要创建新的API端点来为知识项添加和删除自定义tag，支持层级表达，并通过输入匹配已有的tag或自动创建新tag。
- 在前端，我们需要修改现有的知识管理界面，添加一个新的输入框和下拉列表组件来支持tag的输入和选择。

## 模块
project-name: knowledgebase
module-name: knowledge

## API设计

### Endpoints
- GET /api/knowledge/{knowledgeId}
Operation: getKnowledge 获取知识项详情
PathParam: knowledgeId
Response: Knowledge
Description: 获取指定知识项的详细信息，包括tags

- POST /api/knowledge/{knowledgeId}/tags
Operation: addTagToKnowledge 为知识项添加tag
PathParam: knowledgeId
BodyParam: Tag
Response: Knowledge
Description: 为指定的知识项添加tag，若tag不存在则自动创建该tag并添加到知识项

- DELETE /api/knowledge/{knowledgeId}/tags/{tagId}
Operation: removeTagFromKnowledge 从知识项中删除tag
PathParam: knowledgeId, tagId
Response: Knowledge
Description: 从指定的知识项中删除tag

### Schema
```yaml
Knowledge:
  type: object
  properties:
    createdTime:
      type: string
      format: date-time
    updatedTime:
      type: string
      format: date-time
    knowledgeId:
      type: string
      description: 知识项ID
    title:
      type: string
      description: 标题
    content:
      type: string
      description: 内容
    tags:
      uniqueItems: true
      type: array
      description: 标签列表
      items:
        $ref: '#/components/schemas/KnowledgeTag'
  description: Knowledge

KnowledgeTag:
  type: object
  properties:
    tagId:
      type: string
      description: 标签ID
    knowledgeId:
      type: string
      description: 知识ID
    tag:
      type: Tag
      $ref: '#/components/schemas/Tag'
  description: KnowledgeTag

Tag:
  type: object
  properties:
    tagId:
      type: string
      description: 标签ID
    tagName:
      type: string
      description: 标签名称
    order:
      type: integer
      description: 标签顺序
      format: int32
  description: Tag
```

## Artifacts
| 变更模块 | backend/frontend | 变更文件类型 | 变更文件 | 需要变更? |
| -------- | ---------------- | ------------ | -------- | --------- |
| knowledge | backend | api | api/KnowledgeService.java | 是 |
| knowledge | backend | service_impl | model/KnowledgeServiceImpl.java | 是 |
| knowledge | backend | domain | model/Knowledge.java | 否 |
| knowledge | backend | domain | model/Tag.java | 否 |
| knowledge | backend | mapper | infra/KnowledgeMapper.java | 否 |
| knowledge | backend | mapper_xml | infra/KnowledgeMapper.xml | 否 |
| knowledge | frontend | api_js | knowledge_api.js | 是 |
| knowledge | frontend | ui_vue | knowledge.vue | 是 |

## 开发相关
- 无