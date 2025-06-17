<template>
  <div
    class="add-question"
    style="
  top: 0;left: 0;
  width: 100%;
   position: absolute; z-index: 100 ;background-color: white;"
  >
    <div style="margin: 10px;">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="题目类型">
          <el-select v-model="selectedWordType" placeholder="请选择题目类型">
            <el-option v-for="item in workType" :key="item.id" :label="item.name" :value="item.id"> </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div style="margin-left: 20px;margin-right: 20px;height: 1px;background-color: black;"></div>
      <div style="margin: 20px;">
        <div v-for="(item, index) in works" :key="item.name" style="margin-bottom: 30px;">
          <span>第{{ index + 1 }}题</span>
          <div style="margin-top: 10px;">
            <el-input type="textarea" :rows="10" v-model="content" placeholder="请输入题目" class="custom-textarea" />
          </div>
          <div style="display: flex; margin-top: 12px;align-items: center;">
            <span>A</span>
            <el-input v-model="item.a" placeholder="" style="margin-left: 20px;"></el-input>
          </div>
          <div style="display: flex; margin-top: 12px;align-items: center;">
            <span>B</span>
            <el-input v-model="item.a" placeholder="" style="margin-left: 20px;"></el-input>
          </div>
          <div style="display: flex; margin-top: 12px;align-items: center;">
            <span>C</span>
            <el-input v-model="item.a" placeholder="" style="margin-left: 20px;"></el-input>
          </div>
          <div style="display: flex; margin-top: 12px;align-items: center;">
            <span>D</span>
            <el-input v-model="item.a" placeholder="" style="margin-left: 20px;"></el-input>
          </div>
        </div>
      </div>
      <div style="display: flex;justify-content: space-between;">
        <el-button type="primary" @click="addWord">添加题目</el-button>
        <div>
          <el-button type="primary" @click="clean">确定</el-button>
          <el-button type="warning" @click="clean">取消</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AddQuestion',
  data() {
    return {
      selectedWordType: 1,
      // 数据属性
      workType: [
        {
          id: 1,
          name: '选择题',
        },
        {
          id: 2,
          name: '判断题',
        },
        {
          id: 3,
          name: '填空题',
        },
        {
          id: 4,
          name: '主观题',
        },
      ],
      works: [
        {
          title: '',
          a: '',
          b: '',
          c: '',
          d: '',
        },
      ],
    }
  },
  methods: {
    // 方法
    addWord() {
      this.works.push({
        title: '',
        a: '',
        b: '',
        c: '',
        d: '',
      })
    },
    // 确认，取消
    clean() {
      this.$emit('clean', '1')
    },
  },
  created() {
    // 生命周期钩子
  },
}
</script>

<style scoped>
.add-question {
  /* 样式 */
}
</style>
