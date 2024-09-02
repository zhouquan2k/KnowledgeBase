## 模块
project-name: knowledgebase
module-name: knowledge

## 用户故事
- Role：知识库用户
- Action：展示和编辑知识库中的tag树，包括修改、删除tag
- Benefit：能够有效地组织和管理知识库的分类标签，确保知识的结构化和易于检索
- Priority: P0

## 场景：展示和编辑tag树
- 知识库管理员登录系统，进入tag管理界面，系统按tagPath和order顺序展示tag树
- 知识库管理员选择某个tag进行更名: 点击编辑图标允许编辑，编辑完成自动保存，系统更新tag信息并刷新tag树
- 知识库管理员选择某个tag进行删除，系统删除该tag，并移除所有使用该tag的知识项中的该tag

## 业务对象：
- Tag：tagId, tagPath, order顺序

## 开发相关
- 暂无
