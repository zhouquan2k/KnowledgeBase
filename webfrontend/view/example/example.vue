<template>
  <el-container class="container">
    <el-aside :width="leftPanelWidth + 'px'">
      <div>
        <el-select v-model="assistantId" placeholder="Assistant" style="width: 180px;margin-top:5px;"
          @change="fetchExamples" clearable>
          <el-option v-for="assistant in assistants" :key="assistant.assistantId" :label="assistant.assistantName"
            :value="assistant.assistantName"></el-option>
        </el-select>
        <el-button style="float: right;margin: 5px;" type="success" plain @click="onAddExample">+
          Example</el-button>
      </div>
      <el-table :data="examples" style="width: 100%" height="600" highlight-current-row @current-change="onViewExample">
        <el-table-column prop="assistantId" label="Assistant">
        </el-table-column>
        <el-table-column prop="description" label="Desc"></el-table-column>
        <el-table-column label="" :width="50">
          <template slot-scope="scope">
            <el-button size="mini" type="danger" plain icon="el-icon-minus" circle
              @click="onDeleteExample(scope.row)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-aside>
    <Splitter @resize="(newWidth) => leftPanelWidth = newWidth" />
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
          <el-tab-pane label="Input Text" name="input">
            <el-input :rows="25" type="textarea" v-model="currentExample.inputText"></el-input>
          </el-tab-pane>
          <el-tab-pane label="Output Text" name="output">
            <el-input :rows="25" type="textarea" v-model="currentExample.outputText"></el-input>
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
import Splitter from '@/components/splitter.vue';

export default {
  components: {
    Splitter
  },
  data() {
    return {
      examples: [],
      currentExample: null,
      assistantId: null,
      assistants: [],
      assistantMap: {},
      leftPanelWidth: 300,
    };
  },
  created() {
    this.fetchExamples();
    this.fetchAssistants();
  },
  methods: {
    async fetchExamples() {
      this.examples = await exampleApi.getExamples(this.assistantId ? { assistantId: this.assistantId } : {});
      this.currentExample = null;
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
      if (this.assistantId) {
        this.currentExample.assistantId = this.assistantId;
      }
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
  padding: 0px;
}

.el-form-item {
  margin-bottom: 2px;
}
</style>
