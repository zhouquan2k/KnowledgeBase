<template>
  <el-container class="container">
    <el-aside :width="leftPanelWidth + 'px'">
      <div>
        <el-select v-model="assistantId" placeholder="请选择" style="width: 180px" @change="fetchExamples" clearable>
          <el-option v-for="assistant in assistants" :key="assistant.assistantId" :label="assistant.assistantName"
            :value="assistant.assistantName"></el-option>
        </el-select>
        <el-button style="float: right;margin: 5px;" type="success" plain @click="onAddExample">+
          Example</el-button>
      </div>
      <el-table :data="examples" style="width: 100%; height: 100%" highlight-current-row
        @current-change="onViewExample">
        <el-table-column prop="assistantId" label="Assistant">
        </el-table-column>
        <el-table-column prop="description" label="Desc"></el-table-column>
        <el-table-column label="操作" :width="50">
          <template slot-scope="scope">
            <el-button size="mini" type="danger" plain icon="el-icon-delete" circle
              @click="onDeleteExample(scope.row)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-aside>
    <div class="divider" @mousedown="onMouseDown"></div>
    <el-main class="container">
      <el-form v-if="currentExample" :model="currentExample" label-width="80px" style="width: 100%">
        <el-row>
          <el-col :span="10">
            <el-form-item label="Assistant">
              <el-select v-model="currentExample.assistantId" placeholder="请选择" style="width: 90%">
                <el-option v-for="assistant in assistants" :key="assistant.assistantId" :label="assistant.assistantName"
                  :value="assistant.assistantName"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item label="Description">
              <el-input v-model="currentExample.description"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-tabs value="input">
          <el-tab-pane label="输入文本" name="input">
            <el-input :rows="20" type="textarea" v-model="currentExample.inputText"></el-input>
          </el-tab-pane>
          <el-tab-pane label="输出文本" name="output">
            <el-input :rows="20" type="textarea" v-model="currentExample.outputText"></el-input>
          </el-tab-pane>
        </el-tabs>
        <el-form-item style="text-align: right;margin: 5px;">
          <el-button type="primary" @click="onSaveExample">保存</el-button>
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
</template>

<script>
import { exampleApi } from './example_api';

export default {
  data() {
    return {
      examples: [],
      currentExample: null,
      assistantId: null,
      assistants: [],
      assistantMap: {},
      leftPanelWidth: 300, // 初始左侧面板的宽度
      dragging: false
    };
  },
  created() {
    this.fetchExamples();
    this.fetchAssistants();
  },
  methods: {
    headerRowStyle() {
      return { backgroundColor: '#f5f5f5' };
    },
    async fetchExamples() {
      this.examples = await exampleApi.getExamples(this.assistantId ? { assistantId: this.assistantId } : {});
    },
    async fetchAssistants() {
      this.assistants = await exampleApi.getAssistants();
      this.assistantMap = this.assistants.reduce((map, assistant) => {
        map[assistant.assistantId] = assistant;
        return map;
      }, {});
    },
    onAddExample() {
      this.currentExample = {};
    },
    async onViewExample(example) {
      if (!example) return;
      this.currentExample = await exampleApi.getExample(example.exampleId);
    },
    async onDeleteExample(example) {
      await this.$Confirm('请确认删除?');
      await exampleApi.deleteExample(example.exampleId);
      this.$success('删除成功');
      this.fetchExamples();
    },
    async onSaveExample() {
      await exampleApi.saveExample(this.currentExample);
      this.$success('保存成功');
      this.currentExample = null;
      this.fetchExamples();
    },
    // 可移动分割线
    onMouseDown() {
      this.dragging = true;
      document.addEventListener('mousemove', this.onMouseMove);
      document.addEventListener('mouseup', this.onMouseUp);
    },
    onMouseMove(event) {
      if (this.dragging) {
        const containerLeft = this.$el.getBoundingClientRect().left;
        const newWidth = event.clientX - containerLeft;
        this.leftPanelWidth = Math.max(newWidth, 100);
      }
    },
    onMouseUp() {
      this.dragging = false;
      document.removeEventListener('mousemove', this.onMouseMove);
      document.removeEventListener('mouseup', this.onMouseUp);
    }
  }
};
</script>

<style lang="scss" scoped>
.container {
  display: flex;
  height: 100vh;
  width: 100%;
}

.el-aside {
  background-color: #ffffff;
  padding: 0px 0px;
}

.el-main {
  padding: 7px;
}

::v-deep .el-table th.el-table__cell {
  background-color: #f0f0f0;
}

.divider {
  width: 2px;
  cursor: ew-resize;
  /* 指针显示为水平拖动的形状 */
  background-color: #dcdcdc;
  height: 100%;
}
</style>
