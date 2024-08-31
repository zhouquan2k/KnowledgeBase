## 模块
project-name: knowledgebase
module-name: knowledge

## 用户故事
- Role：知识库管理员
- Action：创建和编辑知识项，并为其添加自定义tag
- Benefit：能够有效地管理和组织知识库中的知识项，确保所有知识项都能被正确分类和检索
- Priority: P0

## 场景：知识项的创建和编辑
- 知识库管理员登录系统，进入知识管理界面
- 知识库管理员点击"+"创建新的知识项，输入知识项的标题和内容，点击"保存"
- 系统为新创建的知识项自动添加默认tag "_knowledge"
- 知识库管理员可以为知识项添加自定义tag，支持层级表达，如 "xxx/yyy"
- 系统在用户输入tag时，提供匹配已有tag的选择，如果输入的tag不存在，系统自动创建该tag
- 知识库选择tag "_knowledge",将列出所有的知识标题
- 知识库管理员选择知识标题可以对该知识进行查看和编辑，修改其标题、内容和tag

## 业务对象：
- Knowledge知识：knowledgeId, title, content
- KnowledgeTag：knowledgeId, tagId
