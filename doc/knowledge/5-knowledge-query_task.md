## knowledge-query 知识项对外查询接口

### 需求描述

- 支持输入tag名，进行精确匹配，返回tag对应的知识项内容。
- 支持将tag树以html文档方式输出，每个节点包含自身的内容（html），以及到所有子节点的链接。

### 业务对象

- Knowledge （已存在）
- Tag （已存在）

### 开发相关

使用单独的查询接口KnowledgeQueryService
本任务只需要提供rest api，因此不需要涉及ui的开发
