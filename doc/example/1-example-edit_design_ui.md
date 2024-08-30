## UI设计
### UI结构
- 系统分两栏设计，左边显示example列表，右边显示当前选择的example详情。
- example列表界面：
  - 顶部工具栏：包含“新建example”按钮
  - example表格：显示所有example的列表，每行包括详情和删除按钮
- example详情页面：
  - 基本信息显示区域：显示exampleID、assistantID等。assistant是一个下拉列表、可在所有的assitant中选择。
  - Tab页：
    - 输入文本显示Tab
    - 输出文本显示Tab

### UI交互
- example列表界面：
  - 用户点击“新建example”按钮，在右侧显示新建example页面
  - 用户点击某行的详情按钮，在右侧显示编辑example页面
  - 用户点击某行的删除按钮，删除该条example，并刷新列表
- 新建/编辑/详情example页面：
  - 用户选择assistor，编辑输入文本和输出文本
  - 用户点击“保存”按钮，保存当前example并返回列表界面

### 需要的后端API
- 获取example列表
- 获取单个example的详情
- 新建example
- 编辑example
- 删除example
