## Knowledge-tag的创建或选择
### 需求描述
- 支持用户为知识项添加自定义tag：tag支持层级表达 "xxx/yyy", 
- 支持通过输入匹配已有的tag并选择
- 若tag为不存在的tag，则自动创建tag
- 支持用户删除某个tag

### 业务对象
- Knowledge知识：knowledgeId, title, content （已存在）
- KnowledgeTag：knowledgeId, tagId（已存在）
- Tag：tagId, tagPath, order顺序 （已存在）
