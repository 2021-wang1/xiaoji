<template>
  <div class="main-content" style="position: relative;">
    <!-- 列表页 -->
    <div v-if="showFlag">
      <el-form :inline="true" :model="searchForm" class="form-content">
        <el-row
          :gutter="20"
          class="slt"
          :style="{
            justifyContent:
              contents.searchBoxPosition == '1' ? 'flex-start' : contents.searchBoxPosition == '2' ? 'center' : 'flex-end',
          }"
        >
          <el-form-item label="题目类型">
            <el-select v-model="searchForm.zuoyebiaoti" placeholder="请选择题目类型" clearable>
              <el-option v-for="item in workType" :key="item.id" :label="item.name" :value="item.id"> </el-option>
            </el-select>
          </el-form-item>

          <!-- <el-form-item>
            <el-button
              v-if="contents.searchBtnIcon == 1 && contents.searchBtnIconPosition == 1"
              icon="el-icon-search"
              type="success"
              @click="search()"
              >{{ contents.searchBtnFont == 1 ? '查询' : '' }}</el-button
            >
            <el-button v-if="contents.searchBtnIcon == 1 && contents.searchBtnIconPosition == 2" type="success" @click="search()"
              >{{ contents.searchBtnFont == 1 ? '查询' : '' }}<i class="el-icon-search el-icon--right"
            /></el-button>
            <el-button v-if="contents.searchBtnIcon == 0" type="success" @click="search()">{{
              contents.searchBtnFont == 1 ? '查询' : ''
            }}</el-button>
          </el-form-item> -->
        </el-row>
        <el-row
          class="ad"
          :style="{
            justifyContent:
              contents.btnAdAllBoxPosition == '1' ? 'flex-start' : contents.btnAdAllBoxPosition == '2' ? 'center' : 'flex-end',
          }"
        >
          <el-form-item>
            <el-button type="success" icon="el-icon-plus" @click="showAddQuestion = true">新增</el-button>
            <el-button
              style="margin-left: 20px;"
              :disabled="dataListSelections.length <= 0"
              type="danger"
              @click="deleteHandler()"
              >删除</el-button
            >
          </el-form-item>
        </el-row>
      </el-form>
      <div class="table-content">
        <el-table
          class="tables"
          :size="contents.tableSize"
          :show-header="contents.tableShowHeader"
          :header-row-style="headerRowStyle"
          :header-cell-style="headerCellStyle"
          :border="contents.tableBorder"
          :fit="contents.tableFit"
          :stripe="contents.tableStripe"
          :row-style="rowStyle"
          :cell-style="cellStyle"
          :style="{ width: '100%', fontSize: contents.tableContentFontSize, color: contents.tableContentFontColor }"
          v-if="isAuth('zuoyedianping', '查看')"
          :data="works"
          v-loading="dataListLoading"
          @selection-change="selectionChangeHandler"
        >
          <el-table-column v-if="contents.tableSelection" type="selection" header-align="center" align="center" width="50">
          </el-table-column>
          <el-table-column label="索引" v-if="contents.tableIndex" type="index" width="50" />
          <el-table-column
            :sortable="contents.tableSortable"
            :align="contents.tableAlign"
            prop="number"
            header-align="center"
            label="题目编号"
          >
            <template slot-scope="scope">
              {{ scope.row.number }}
            </template>
          </el-table-column>
          <el-table-column
            :sortable="contents.tableSortable"
            :align="contents.tableAlign"
            prop="name"
            header-align="center"
            label="题目名称"
          >
            <template slot-scope="scope">
              {{ scope.row.name }}
            </template>
          </el-table-column>
          <el-table-column
            :sortable="contents.tableSortable"
            :align="contents.tableAlign"
            prop="action"
            header-align="center"
            label="上传人"
          >
            <template slot-scope="scope">
              {{ scope.row.action }}
            </template>
          </el-table-column>
          <el-table-column
            :sortable="contents.tableSortable"
            :align="contents.tableAlign"
            prop="fileName"
            header-align="center"
            label="文件"
            style="color: blue; text-decoration: underline;"
          >
            <template slot-scope="scope">
              <span style="color: blue;text-decoration: underline;">
                {{ scope.row.fileName }}
              </span>
            </template>
          </el-table-column>
          <el-table-column
            :sortable="contents.tableSortable"
            :align="contents.tableAlign"
            prop="time"
            header-align="center"
            label="上传时间"
          >
            <template slot-scope="scope">
              {{ scope.row.time }}
            </template>
          </el-table-column>
          <el-table-column width="300" :align="contents.tableAlign" header-align="center" label="操作">
            <template slot-scope="scope">
              <el-button
                v-if="isAuth('zuoyedianping', '查看') && contents.tableBtnIcon == 1 && contents.tableBtnIconPosition == 1"
                type="success"
                icon="el-icon-tickets"
                size="mini"
                @click="addOrUpdateHandler(scope.row.id, 'info')"
                >{{ contents.tableBtnFont == 1 ? '详情' : '' }}</el-button
              >
              <el-button
                v-if="isAuth('zuoyedianping', '查看') && contents.tableBtnIcon == 1 && contents.tableBtnIconPosition == 2"
                type="success"
                size="mini"
                @click="addOrUpdateHandler(scope.row.id, 'info')"
                >{{ contents.tableBtnFont == 1 ? '详情' : '' }}<i class="el-icon-tickets el-icon--right"
              /></el-button>
              <el-button
                v-if="isAuth('zuoyedianping', '查看') && contents.tableBtnIcon == 0"
                type="success"
                size="mini"
                @click="addOrUpdateHandler(scope.row.id, 'info')"
                >{{ contents.tableBtnFont == 1 ? '详情' : '' }}</el-button
              >
              <el-button
                v-if="isAuth('zuoyedianping', '修改') && contents.tableBtnIcon == 1 && contents.tableBtnIconPosition == 1"
                type="primary"
                icon="el-icon-edit"
                size="mini"
                @click="addOrUpdateHandler(scope.row.id)"
                >{{ contents.tableBtnFont == 1 ? '修改' : '' }}</el-button
              >
              <el-button
                v-if="isAuth('zuoyedianping', '修改') && contents.tableBtnIcon == 1 && contents.tableBtnIconPosition == 2"
                type="primary"
                size="mini"
                @click="addOrUpdateHandler(scope.row.id)"
                >{{ contents.tableBtnFont == 1 ? '修改' : '' }}<i class="el-icon-edit el-icon--right"
              /></el-button>
              <el-button
                v-if="isAuth('zuoyedianping', '修改') && contents.tableBtnIcon == 0"
                type="primary"
                size="mini"
                @click="addOrUpdateHandler(scope.row.id)"
                >{{ contents.tableBtnFont == 1 ? '修改' : '' }}</el-button
              >

              <el-button
                v-if="isAuth('zuoyedianping', '删除') && contents.tableBtnIcon == 1 && contents.tableBtnIconPosition == 1"
                type="danger"
                icon="el-icon-delete"
                size="mini"
                @click="deleteHandler(scope.row.id)"
                >{{ contents.tableBtnFont == 1 ? '删除' : '' }}</el-button
              >
              <el-button
                v-if="isAuth('zuoyedianping', '删除') && contents.tableBtnIcon == 1 && contents.tableBtnIconPosition == 2"
                type="danger"
                size="mini"
                @click="deleteHandler(scope.row.id)"
                >{{ contents.tableBtnFont == 1 ? '删除' : '' }}<i class="el-icon-delete el-icon--right"
              /></el-button>
              <el-button
                v-if="isAuth('zuoyedianping', '删除') && contents.tableBtnIcon == 0"
                type="danger"
                size="mini"
                @click="deleteHandler(scope.row.id)"
                >{{ contents.tableBtnFont == 1 ? '删除' : '' }}</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          clsss="pages"
          :layout="layouts"
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageIndex"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="Number(contents.pageEachNum)"
          :total="totalPage"
          :small="contents.pageStyle"
          class="pagination-content"
          :background="contents.pageBtnBG"
          :style="{ textAlign: contents.pagePosition == 1 ? 'left' : contents.pagePosition == 2 ? 'center' : 'right' }"
        ></el-pagination>
      </div>
    </div>
    <AddQuestion @clean="addQuestionClean" v-if="showAddQuestion"></AddQuestion>
  </div>
</template>
<script>
import AddQuestion from './addQuestion.vue'
import addQuestion from './addQuestion.vue'
export default {
  data() {
    return {
      showAddQuestion: false,
      workType: [
        {
          id: undefined,
          name: '全部',
        },
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
      searchForm: {
        key: '',
      },
      works: Array.from({ length: 10 }, (_, i) => {
        const names = [
          '第一单元测试题01',
          '第二单元测试题02',
          '第三单元测试题03',
          '第四单元测试题04',
          '第五单元测试题05',
          '第六单元测试题06',
          '第七单元测试题07',
          '第八单元测试题08',
          '第九单元测试题09',
          '第十单元测试题10',
        ]
        const actions = ['王明', '李华', '张伟', '赵强', '孙丽', '周杰', '吴敏', '郑爽', '钱坤', '冯涛']
        const baseNumber = 100001
        const baseDate = new Date(2025, 0, 1)
        return {
          id: i + 1,
          number: baseNumber + i,
          name: names[i],
          fileName: `${names[i]}.doc`,
          action: actions[i],
          time: new Date(baseDate.getTime() + i * 86400000).toISOString().slice(0, 10),
        }
      }),
      form: {},
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      showFlag: true,
      sfshVisiable: false,
      shForm: {},
      chartVisiable: false,
      addOrUpdateFlag: false,
      contents: {
        searchBtnFontColor: '#333',
        pagePosition: '2',
        inputFontSize: '14px',
        inputBorderRadius: '4px',
        tableBtnDelFontColor: 'rgba(249, 104, 104, 1)',
        tableBtnIconPosition: '1',
        searchBtnHeight: '40px',
        inputIconColor: '#C0C4CC',
        searchBtnBorderRadius: '4px',
        tableStripe: true,
        btnAdAllWarnFontColor: 'rgba(252, 197, 37, 1)',
        tableBtnDelBgColor: '#fff',
        searchBtnIcon: '1',
        tableSize: 'medium',
        searchBtnBorderStyle: 'solid',
        tableSelection: true,
        searchBtnBorderWidth: '1px',
        tableContentFontSize: '14px',
        searchBtnBgColor: 'rgba(228, 231, 234, 1)',
        inputTitleSize: '14px',
        btnAdAllBorderColor: 'rgba(144, 238, 144, 1)',
        pageJumper: true,
        btnAdAllIconPosition: '1',
        searchBoxPosition: '1',
        tableBtnDetailFontColor: 'rgba(87, 199, 212, 1)',
        tableBtnHeight: '40px',
        pagePager: true,
        searchBtnBorderColor: '#DCDFE6',
        tableHeaderFontColor: 'rgba(255, 255, 255, 1)',
        inputTitle: '1',
        tableBtnBorderRadius: '4px',
        btnAdAllFont: '1',
        btnAdAllDelFontColor: 'rgba(249, 104, 104, 1)',
        tableBtnIcon: '0',
        btnAdAllHeight: '40px',
        btnAdAllWarnBgColor: '#fff',
        btnAdAllBorderWidth: '1px',
        tableStripeFontColor: '#606266',
        tableBtnBorderStyle: 'solid',
        inputHeight: '40px',
        btnAdAllBorderRadius: '4px',
        btnAdAllDelBgColor: '#fff',
        pagePrevNext: true,
        btnAdAllAddBgColor: '#fff',
        searchBtnFont: '1',
        tableIndex: true,
        btnAdAllIcon: '1',
        tableSortable: true,
        pageSizes: true,
        tableFit: true,
        pageBtnBG: false,
        searchBtnFontSize: '14px',
        tableBtnEditBgColor: '#fff',
        inputBorderWidth: '1px',
        inputFontPosition: '1',
        inputFontColor: '#333',
        pageEachNum: 10,
        tableHeaderBgColor: 'rgba(144, 238, 144, 1)',
        inputTitleColor: '#333',
        btnAdAllBoxPosition: '1',
        tableBtnDetailBgColor: '#fff',
        inputIcon: '0',
        searchBtnIconPosition: '1',
        btnAdAllFontSize: '14px',
        inputBorderStyle: 'solid',
        inputBgColor: '#fff',
        pageStyle: false,
        pageTotal: true,
        btnAdAllAddFontColor: 'rgba(51, 202, 187, 1)',
        tableBtnFont: '1',
        tableContentFontColor: 'rgba(72, 72, 72, 1)',
        inputBorderColor: 'rgba(223, 230, 236, 1)',
        tableShowHeader: true,
        tableBtnFontSize: '14px',
        tableBtnBorderColor: 'rgba(144, 238, 144, 1)',
        inputIconPosition: '1',
        tableBorder: true,
        btnAdAllBorderStyle: 'solid',
        tableBtnBorderWidth: '1px',
        tableStripeBgColor: 'rgba(252, 253, 254, 1)',
        tableBtnEditFontColor: 'rgba(249, 97, 151, 1)',
        tableAlign: 'center',
      },
      layouts: '',
    }
  },
  created() {
    this.init()
    this.getDataList()
    this.contentStyleChange()
  },
  mounted() {},
  filters: {
    htmlfilter: function(val) {
      return val.replace(/<[^>]*>/g).replace(/undefined/g, '')
    },
  },
  methods: {
    addQuestionClean() {
      this.showAddQuestion = false
    },
    contentStyleChange() {
      this.contentSearchStyleChange()
      this.contentBtnAdAllStyleChange()
      this.contentSearchBtnStyleChange()
      this.contentTableBtnStyleChange()
      this.contentPageStyleChange()
    },
    contentSearchStyleChange() {
      this.$nextTick(() => {
        document.querySelectorAll('.form-content .slt .el-input__inner').forEach((el) => {
          let textAlign = 'left'
          if (this.contents.inputFontPosition == 2) textAlign = 'center'
          if (this.contents.inputFontPosition == 3) textAlign = 'right'
          el.style.textAlign = textAlign
          el.style.height = this.contents.inputHeight
          el.style.lineHeight = this.contents.inputHeight
          el.style.color = this.contents.inputFontColor
          el.style.fontSize = this.contents.inputFontSize
          el.style.borderWidth = this.contents.inputBorderWidth
          el.style.borderStyle = this.contents.inputBorderStyle
          el.style.borderColor = this.contents.inputBorderColor
          el.style.borderRadius = this.contents.inputBorderRadius
          el.style.backgroundColor = this.contents.inputBgColor
        })
        if (this.contents.inputTitle) {
          document.querySelectorAll('.form-content .slt .el-form-item__label').forEach((el) => {
            el.style.color = this.contents.inputTitleColor
            el.style.fontSize = this.contents.inputTitleSize
            el.style.lineHeight = this.contents.inputHeight
          })
        }
        setTimeout(() => {
          document.querySelectorAll('.form-content .slt .el-input__prefix').forEach((el) => {
            el.style.color = this.contents.inputIconColor
            el.style.lineHeight = this.contents.inputHeight
          })
          document.querySelectorAll('.form-content .slt .el-input__suffix').forEach((el) => {
            el.style.color = this.contents.inputIconColor
            el.style.lineHeight = this.contents.inputHeight
          })
          document.querySelectorAll('.form-content .slt .el-input__icon').forEach((el) => {
            el.style.lineHeight = this.contents.inputHeight
          })
        }, 10)
      })
    },
    // 搜索按钮
    contentSearchBtnStyleChange() {
      this.$nextTick(() => {
        document.querySelectorAll('.form-content .slt .el-button--success').forEach((el) => {
          el.style.height = this.contents.searchBtnHeight
          el.style.color = this.contents.searchBtnFontColor
          el.style.fontSize = this.contents.searchBtnFontSize
          el.style.borderWidth = this.contents.searchBtnBorderWidth
          el.style.borderStyle = this.contents.searchBtnBorderStyle
          el.style.borderColor = this.contents.searchBtnBorderColor
          el.style.borderRadius = this.contents.searchBtnBorderRadius
          el.style.backgroundColor = this.contents.searchBtnBgColor
        })
      })
    },
    // 新增、批量删除
    contentBtnAdAllStyleChange() {
      this.$nextTick(() => {
        document.querySelectorAll('.form-content .ad .el-button--success').forEach((el) => {
          el.style.height = this.contents.btnAdAllHeight
          el.style.color = this.contents.btnAdAllAddFontColor
          el.style.fontSize = this.contents.btnAdAllFontSize
          el.style.borderWidth = this.contents.btnAdAllBorderWidth
          el.style.borderStyle = this.contents.btnAdAllBorderStyle
          el.style.borderColor = this.contents.btnAdAllBorderColor
          el.style.borderRadius = this.contents.btnAdAllBorderRadius
          el.style.backgroundColor = this.contents.btnAdAllAddBgColor
        })
        document.querySelectorAll('.form-content .ad .el-button--danger').forEach((el) => {
          el.style.height = this.contents.btnAdAllHeight
          el.style.color = this.contents.btnAdAllDelFontColor
          el.style.fontSize = this.contents.btnAdAllFontSize
          el.style.borderWidth = this.contents.btnAdAllBorderWidth
          el.style.borderStyle = this.contents.btnAdAllBorderStyle
          el.style.borderColor = this.contents.btnAdAllBorderColor
          el.style.borderRadius = this.contents.btnAdAllBorderRadius
          el.style.backgroundColor = this.contents.btnAdAllDelBgColor
        })
        document.querySelectorAll('.form-content .ad .el-button--warning').forEach((el) => {
          el.style.height = this.contents.btnAdAllHeight
          el.style.color = this.contents.btnAdAllWarnFontColor
          el.style.fontSize = this.contents.btnAdAllFontSize
          el.style.borderWidth = this.contents.btnAdAllBorderWidth
          el.style.borderStyle = this.contents.btnAdAllBorderStyle
          el.style.borderColor = this.contents.btnAdAllBorderColor
          el.style.borderRadius = this.contents.btnAdAllBorderRadius
          el.style.backgroundColor = this.contents.btnAdAllWarnBgColor
        })
      })
    },
    // 表格
    rowStyle({ row, rowIndex }) {
      if (rowIndex % 2 == 1) {
        if (this.contents.tableStripe) {
          return { color: this.contents.tableStripeFontColor }
        }
      } else {
        return ''
      }
    },
    cellStyle({ row, rowIndex }) {
      if (rowIndex % 2 == 1) {
        if (this.contents.tableStripe) {
          return { backgroundColor: this.contents.tableStripeBgColor }
        }
      } else {
        return ''
      }
    },
    headerRowStyle({ row, rowIndex }) {
      return { color: this.contents.tableHeaderFontColor }
    },
    headerCellStyle({ row, rowIndex }) {
      return { backgroundColor: this.contents.tableHeaderBgColor }
    },
    // 表格按钮
    contentTableBtnStyleChange() {
      // this.$nextTick(()=>{
      //   setTimeout(()=>{
      //     document.querySelectorAll('.table-content .tables .el-table__body .el-button--success').forEach(el=>{
      //       el.style.height = this.contents.tableBtnHeight
      //       el.style.color = this.contents.tableBtnDetailFontColor
      //       el.style.fontSize = this.contents.tableBtnFontSize
      //       el.style.borderWidth = this.contents.tableBtnBorderWidth
      //       el.style.borderStyle = this.contents.tableBtnBorderStyle
      //       el.style.borderColor = this.contents.tableBtnBorderColor
      //       el.style.borderRadius = this.contents.tableBtnBorderRadius
      //       el.style.backgroundColor = this.contents.tableBtnDetailBgColor
      //     })
      //     document.querySelectorAll('.table-content .tables .el-table__body .el-button--primary').forEach(el=>{
      //       el.style.height = this.contents.tableBtnHeight
      //       el.style.color = this.contents.tableBtnEditFontColor
      //       el.style.fontSize = this.contents.tableBtnFontSize
      //       el.style.borderWidth = this.contents.tableBtnBorderWidth
      //       el.style.borderStyle = this.contents.tableBtnBorderStyle
      //       el.style.borderColor = this.contents.tableBtnBorderColor
      //       el.style.borderRadius = this.contents.tableBtnBorderRadius
      //       el.style.backgroundColor = this.contents.tableBtnEditBgColor
      //     })
      //     document.querySelectorAll('.table-content .tables .el-table__body .el-button--danger').forEach(el=>{
      //       el.style.height = this.contents.tableBtnHeight
      //       el.style.color = this.contents.tableBtnDelFontColor
      //       el.style.fontSize = this.contents.tableBtnFontSize
      //       el.style.borderWidth = this.contents.tableBtnBorderWidth
      //       el.style.borderStyle = this.contents.tableBtnBorderStyle
      //       el.style.borderColor = this.contents.tableBtnBorderColor
      //       el.style.borderRadius = this.contents.tableBtnBorderRadius
      //       el.style.backgroundColor = this.contents.tableBtnDelBgColor
      //     })
      //   }, 50)
      // })
    },
    // 分页
    contentPageStyleChange() {
      let arr = []

      if (this.contents.pageTotal) arr.push('total')
      if (this.contents.pageSizes) arr.push('sizes')
      if (this.contents.pagePrevNext) {
        arr.push('prev')
        if (this.contents.pagePager) arr.push('pager')
        arr.push('next')
      }
      if (this.contents.pageJumper) arr.push('jumper')
      this.layouts = arr.join()
      this.contents.pageEachNum = 10
    },

    init() {},
    search() {
      this.pageIndex = 1
      this.getDataList()
    },
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true
      let params = {
        page: this.pageIndex,
        limit: this.pageSize,
        sort: 'id',
      }
      if (this.searchForm.zuoyebiaoti != '' && this.searchForm.zuoyebiaoti != undefined) {
        params['zuoyebiaoti'] = '%' + this.searchForm.zuoyebiaoti + '%'
      }
      if (this.searchForm.xueshengxingming != '' && this.searchForm.xueshengxingming != undefined) {
        params['xueshengxingming'] = '%' + this.searchForm.xueshengxingming + '%'
      }
      this.$http({
        url: 'zuoyedianping/page',
        method: 'get',
        params: params,
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.dataList = data.data.list
          this.totalPage = data.data.total
        } else {
          this.dataList = []
          this.totalPage = 0
        }
        this.dataListLoading = false
      })
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val
      this.getDataList()
    },
    // 多选
    selectionChangeHandler(val) {
      this.dataListSelections = val
    },

    // 查看评论
    // 下载
    download(file) {
      window.open(`${file}`)
    },
    // 删除
    deleteHandler(id) {
      var ids = id
        ? [Number(id)]
        : this.dataListSelections.map((item) => {
            return Number(item.id)
          })
      this.$confirm(`确定进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        this.$http({
          url: 'zuoyedianping/delete',
          method: 'post',
          data: ids,
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.search()
              },
            })
          } else {
            this.$message.error(data.msg)
          }
        })
      })
    },
  },
  components: {
    AddQuestion,
  },
}
</script>
<style lang="scss" scoped>
.slt {
  margin: 0 !important;
  display: flex;
}

.ad {
  margin: 0 !important;
  display: flex;
}

.pages {
  & /deep/ el-pagination__sizes {
    & /deep/ el-input__inner {
      height: 22px;
      line-height: 22px;
    }
  }
}

.el-button + .el-button {
  margin: 0;
}

.tables {
  & /deep/ .el-button--success {
    height: 40px;
    color: rgba(87, 199, 212, 1);
    font-size: 14px;
    border-width: 1px;
    border-style: solid;
    border-color: rgba(144, 238, 144, 1);
    border-radius: 4px;
    background-color: #fff;
  }

  & /deep/ .el-button--primary {
    height: 40px;
    color: rgba(249, 97, 151, 1);
    font-size: 14px;
    border-width: 1px;
    border-style: solid;
    border-color: rgba(144, 238, 144, 1);
    border-radius: 4px;
    background-color: #fff;
  }

  & /deep/ .el-button--danger {
    height: 40px;
    color: rgba(249, 104, 104, 1);
    font-size: 14px;
    border-width: 1px;
    border-style: solid;
    border-color: rgba(144, 238, 144, 1);
    border-radius: 4px;
    background-color: #fff;
  }

  & /deep/ .el-button {
    margin: 4px;
  }
}
</style>
