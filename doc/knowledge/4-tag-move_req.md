## 模块
project-name: knowledgebase
module-name: knowledge

## 用户故事
- Role：知识库用户
- Action：支持tag树的节点的拖放移动，将tag移动到另一个tag的子节点
- Benefit：能够方便地组织和管理知识库的分类结构，提高知识的检索效率
- Priority: P0

## 场景：tag树的展示和编辑
- 知识库管理员登录系统，进入知识库管理界面，系统显示当前的tag树结构
- 知识库管理员选择一个tag节点，并将其拖动到另一个tag节点上 ，系统将该tag节点移动到目标tag节点的子节点位置，并更新tag树结构

## 业务对象：
- Tag：tagId, tagName, parentTagId, order

## 开发相关
- 暂无
