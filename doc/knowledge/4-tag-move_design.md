## 设计思路

在知识库系统中，tag树的管理是一个重要的功能。为了实现tag树的节点拖放移动，我们需要在后端提供相应的API接口来获取和更新tag树结构。在前端，我们需要实现一个支持拖放操作的tag树组件，并在拖放操作完成后调用后端API更新tag树结构。

## 模块
project-name: knowledgebase
module-name: knowledge

## API设计

### Endpoints
- GET /api/tags/tree (已存在)
Operation: getTags 获取当前tag树结构
Response: List<Tag>

- PUT /api/tags/{tagId}/move
Operation: moveTag 更新tag节点的父节点信息
PathParam：tagId
BodyParam：Tag
实现：使用repository更新tag的parentTagId

- DELETE /api/knowledge/{knowledgeId}
Operation: deleteKnowledge 删除知识项
PathParam：knowledgeId

### Schema
``` yaml
Tag:
  type: object
  description: "tag节点"
  properties:
    tagId:
      type: string
      description: "tag ID"
    tagName:
      type: string
      description: "tag名称"
    parentTagId:
      type: string
      description: "父tag ID"
    order:
      type: integer
      description: "排序"

```

## Artifacts
| 变更模块 | backend/frontend | 变更文件类型 | 变更文件                            | 需要变更? |
|----------|------------------|--------------|---------------------------------|-------|
| knowledge | backend | api | api/KnowledgeService.java       | 是     |
| knowledge | backend | domain | model/Tag.java                  | 否     |
| knowledge | backend | mapper | infra/KnowledgeMapper.java      | 否     |
| knowledge | backend | mapper_xml | infra/KnowledgeMapper.xml       | 否     |
| knowledge | backend | service_impl | model/KnowledgeServiceImpl.java | 是     |
| knowledge | frontend | api_js | knowledge_api.js                | 是     |
| knowledge | frontend | ui_vue | tag_tree.vue                    | 是     |

## 开发相关
- 暂无
