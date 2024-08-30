## 模块
example

## 用户故事
- Role：系统管理员
- Action：新建、编辑和查看example的详细信息，包括选择assistor，并在分tab页中分别编辑输入文本和输出文本
- Benefit：能够有效地管理fine tuning所需的example数据
- Priority: P0

## 场景：example的输入、编辑、查看
- 系统管理员登录系统，进入example列表界面。
- example列表界面：显示所有example的列表（每个example行显示编辑和删除入口
- 系统管理员按下“新建example”按钮，显示example的输入页面。系统管理员在详情页面选择assistor，并在分tab页中分别编辑输入文本和输出文本。点击“保存“按钮保存当前example
- 系统管理员在列表对某个example点击“查看”链接，进入example详情页面

## 业务对象：
- example：exampleID、assistantID、输入、输出
- assistant：assistantID、system

## 开发相关
- 无
