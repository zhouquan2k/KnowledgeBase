## 设计思路
- 在后端，我们需要创建新的API端点来获取、更新和删除tag。
- 在前端，我们需要创建一个新的Tag管理界面，允许用户查看、编辑和删除tag。

## 模块
project-name: knowledgebase
module-name: knowledge

## API设计

### Endpoints
- GET /api/knowledge/tags/tree
  - Operation: getTagTree 获取tag树
  - Response: List<Tag>
  - Description: 获取完整的tag树数据

- PUT /api/knowledge/tags/{tagId}
  - Operation: updateTag 更新tag名称
  - PathParam: tagId
  - BodyParam: Tag
  - Response: Tag
  - Description: 更新tag的名称

- DELETE /api/knowledge/tags/{tagId}
  - Operation: deleteTag 删除tag
  - PathParam: tagId
  - Response: void
  - Description: 删除tag并移除所有使用该tag的知识项中的该tag

### Schema
```yaml
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
    order:
      type: integer
      description: 标签顺序
```

## Artifacts
| 变更模块 | backend/frontend | 变更文件类型 | 变更文件                            | 需要变更? |
| --- | --- | --- |---------------------------------| --- |
| knowledge | backend | api | api/KnowledgeService.java       | 是 |
| knowledge | backend | domain | model/Tag.java                  | 是 |
| knowledge | backend | mapper | infra/KnowledgeMapper.java      | 是 |
| knowledge | backend | mapper_xml | infra/KnowledgeMapper.xml       | 是 |
| knowledge | backend | service_impl | model/KnowledgeServiceImpl.java | 是 |
| knowledge | frontend | api_js | knowledge_api.js                | 是 |
| knowledge | frontend | ui_vue | tag_tree.vue                    | 是 |

## 开发相关
- 暂无