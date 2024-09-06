## 设计思路

- 在现有的Knowledge模块中，增加一个新的查询接口，通过tag名精确匹配并返回对应的知识项内容。
- 使用单独的查询接口KnowledgeQueryService来实现该功能。
- 这是一个REST API，不需要UI。

## 模块

project-name: knowledgebase
module-name: knowledge

## API设计

### Endpoints

- GET /api/knowledge/query/tag
    - Operation: queryKnowledgeByTag
    - QueryParam：tagName, needParent(是否需要父tag的内容)
    - Response: List<Knowledge>
    - Description：通过tag名精确匹配并返回对应的知识项内容。

## Artifacts

| 变更模块      | backend/frontend | 变更文件类型       | 变更文件                                 | 需要变更? |
|-----------|------------------|--------------|--------------------------------------|-------|
| knowledge | backend          | api          | api/KnowledgeQueryService.java       | 是     |
| knowledge | backend          | service_impl | model/KnowledgeQueryServiceImpl.java | 是     |
| knowledge | backend          | mapper       | infra/KnowledgeMapper.java           | 是     |
| knowledge | backend          | mapper_xml   | infra/KnowledgeMapper.xml            | 是     |

## 开发相关

- 使用单独的查询接口KnowledgeQueryService
- 这是一个REST API，不需要UI
