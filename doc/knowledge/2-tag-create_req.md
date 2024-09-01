## 模块
project-name: knowledgebase
module-name: knowledge

## 用户故事
- Role：知识库用户
- Action：为知识项添加自定义tag，支持层级表达，并通过输入匹配已有的tag或自动创建新tag
- Benefit：能够更好地组织和分类知识，提高知识检索和管理效率
- Priority: P1

## 场景：创建或选择Knowledge-tag
- 用户登录系统，进入知识管理界面
- 用户选择某个知识项，进入知识项详情页面
- 用户在tag输入框中输入tag名称（部分）
- 系统实时匹配已有的tag，并显示匹配结果供用户选择
- 用户从匹配结果中选择一个tag，或输入一个不存在的tag
- 系统为知识项添加选中的tag，若tag不存在则自动创建该tag并添加到知识项

## 业务对象：
- Knowledge知识：knowledgeId, title, content
- KnowledgeTag：knowledgeId, tagId
- Tag：tagId, tagPath, order顺序

## 开发相关
- 无
