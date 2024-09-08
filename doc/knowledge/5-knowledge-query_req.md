## 模块

project-name: knowledgebase
module-name: knowledge

## 用户故事

- Role：外部系统
- Action：通过输入tag名，精确匹配并返回对应的知识项内容
- Benefit：能够快速获取与特定标签相关的知识项，提高知识检索效率
- Priority: P1
-
- Role：外部系统
- Action：通过输入节点的链接，获取该节点的文档内容html，以及所有子节点的超链接
- Benefit：方便外部系统遍历文档树，获取文档内容
- Priority: P1

## 场景：通过tag名查询知识项内容

- 外部系统调用rest接口，输入tag名，
- 系统返回匹配到的tag对应的知识项内容，包括知识项的标题和内容
- 根据需要（参数），可以返回当前匹配知识项，以及所有上层知识项的内容
- 外部系统访问文档节点的html链接，可以获得该节点的文档内容html，以及所有子节点的超链接

## 业务对象：

- Knowledge：（已存在）
- Tag：（已存在）

## 开发相关

- 使用单独的查询接口KnowledgeQueryService
- 这是一个rest api，不需要ui
