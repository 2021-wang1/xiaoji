<template>
  <div class="addEdit-block">
    <el-form
      class="detail-form-content"
      ref="ruleForm"
      :model="ruleForm"
      :rules="rules"
      label-width="80px"
      :style="{ backgroundColor: addEditForm.addEditBoxColor }"
    >
      <el-row>
        <el-col :span="12">
          <el-form-item class="input" v-if="type != 'info'" label="作业编号" prop="zuoyebianhao">
            <el-input v-model="ruleForm.zuoyebianhao" placeholder="作业编号" clearable :readonly="ro.zuoyebianhao"></el-input>
          </el-form-item>
          <div v-else>
            <el-form-item class="input" label="作业编号" prop="zuoyebianhao">
              <el-input v-model="ruleForm.zuoyebianhao" placeholder="作业编号" readonly></el-input>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12">
          <el-form-item class="input" v-if="type != 'info'" label="作业标题" prop="zuoyebiaoti">
            <el-input v-model="ruleForm.zuoyebiaoti" placeholder="作业标题" clearable :readonly="ro.zuoyebiaoti"></el-input>
          </el-form-item>
          <div v-else>
            <el-form-item class="input" label="作业标题" prop="zuoyebiaoti">
              <el-input v-model="ruleForm.zuoyebiaoti" placeholder="作业标题" readonly></el-input>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12" style="">
          <el-form-item class="upload" v-if="type != 'info'" label="作业文件" prop="zuoyewenjian">
            <!-- <el-select v-model="ruleForm.zuoyewenjian" placeholder="请选择作业文件">
              <el-option v-for="item in works" :key="item.url" :label="item.name" :value="item.url"> </el-option>
            </el-select> -->
            <file-upload
              tip="点击上传作业文件"
              action="file/upload"
              :limit="1"
              :multiple="true"
              :fileUrls="ruleForm.zuoyewenjian ? ruleForm.zuoyewenjian : ''"
              @change="zuoyewenjianUploadChange"
            ></file-upload>
          </el-form-item>
          <div v-else>
            <el-form-item v-if="ruleForm.zuoyewenjian" label="作业文件" prop="zuoyewenjian">
              <el-button type="text" size="small" @click="download(ruleForm.zuoyewenjian)">下载</el-button>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12">
          <el-form-item class="input" v-if="type != 'info'" label="作业内容" prop="zuoyeneirong">
            <el-input v-model="ruleForm.zuoyeneirong" placeholder="作业内容" clearable :readonly="ro.zuoyeneirong"></el-input>
          </el-form-item>
          <div v-else>
            <el-form-item class="input" label="作业内容" prop="zuoyeneirong">
              <el-input v-model="ruleForm.zuoyeneirong" placeholder="作业内容" readonly></el-input>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12">
          <el-form-item class="date" v-if="type != 'info'" label="发布日期" prop="faburiqi">
            <el-date-picker
              format="yyyy 年 MM 月 dd 日"
              value-format="yyyy-MM-dd"
              v-model="ruleForm.faburiqi"
              type="date"
              placeholder="发布日期"
            >
            </el-date-picker>
          </el-form-item>
          <div v-else>
            <el-form-item class="input" v-if="ruleForm.faburiqi" label="发布日期" prop="faburiqi">
              <el-input v-model="ruleForm.faburiqi" placeholder="发布日期" readonly></el-input>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12">
          <el-form-item class="input" v-if="type != 'info'" label="工号" prop="gonghao">
            <el-input v-model="ruleForm.gonghao" placeholder="工号" clearable :readonly="ro.gonghao"></el-input>
          </el-form-item>
          <div v-else>
            <el-form-item class="input" label="工号" prop="gonghao">
              <el-input v-model="ruleForm.gonghao" placeholder="工号" readonly></el-input>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12">
          <el-form-item class="input" v-if="type != 'info'" label="教师姓名" prop="jiaoshixingming">
            <el-input
              v-model="ruleForm.jiaoshixingming"
              placeholder="教师姓名"
              clearable
              :readonly="ro.jiaoshixingming"
            ></el-input>
          </el-form-item>
          <div v-else>
            <el-form-item class="input" label="教师姓名" prop="jiaoshixingming">
              <el-input v-model="ruleForm.jiaoshixingming" placeholder="教师姓名" readonly></el-input>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12">
          <el-form-item class="select" v-if="type != 'info'" label="班级" prop="banji">
            <el-select v-model="ruleForm.banji" placeholder="班级" clearable :readonly="ro.banji">
              <el-option v-for="(item, index) in banjiOptions" v-bind:key="index" :label="item" :value="item"> </el-option>
            </el-select>
          </el-form-item>
          <div v-else>
            <el-form-item class="input" label="班级" prop="banji">
              <el-input v-model="ruleForm.banji" placeholder="班级" readonly></el-input>
            </el-form-item>
          </div>
        </el-col>
      </el-row>
      <el-form-item class="btn">
        <el-button v-if="type != 'info'" type="primary" class="btn-success" @click="onSubmit">提交</el-button>
        <el-button v-if="type != 'info'" class="btn-close" @click="back()">取消</el-button>
        <el-button v-if="type == 'info'" class="btn-close" @click="back()">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
// 数字，邮件，手机，url，身份证校验
import { isNumber, isIntNumer, isEmail, isPhone, isMobile, isURL, checkIdCard } from '@/utils/validate'
export default {
  data() {
    let self = this
    var validateIdCard = (rule, value, callback) => {
      if (!value) {
        callback()
      } else if (!checkIdCard(value)) {
        callback(new Error('请输入正确的身份证号码'))
      } else {
        callback()
      }
    }
    var validateUrl = (rule, value, callback) => {
      if (!value) {
        callback()
      } else if (!isURL(value)) {
        callback(new Error('请输入正确的URL地址'))
      } else {
        callback()
      }
    }
    var validateMobile = (rule, value, callback) => {
      if (!value) {
        callback()
      } else if (!isMobile(value)) {
        callback(new Error('请输入正确的手机号码'))
      } else {
        callback()
      }
    }
    var validatePhone = (rule, value, callback) => {
      if (!value) {
        callback()
      } else if (!isPhone(value)) {
        callback(new Error('请输入正确的电话号码'))
      } else {
        callback()
      }
    }
    var validateEmail = (rule, value, callback) => {
      if (!value) {
        callback()
      } else if (!isEmail(value)) {
        callback(new Error('请输入正确的邮箱地址'))
      } else {
        callback()
      }
    }
    var validateNumber = (rule, value, callback) => {
      if (!value) {
        callback()
      } else if (!isNumber(value)) {
        callback(new Error('请输入数字'))
      } else {
        callback()
      }
    }
    var validateIntNumber = (rule, value, callback) => {
      if (!value) {
        callback()
      } else if (!isIntNumer(value)) {
        callback(new Error('请输入整数'))
      } else {
        callback()
      }
    }
    return {
      addEditForm: {
        btnSaveFontColor: '#fff',
        selectFontSize: '14px',
        btnCancelBorderColor: '#DCDFE6',
        inputBorderRadius: '4px',
        inputFontSize: '14px',
        textareaBgColor: '#fff',
        btnSaveFontSize: '14px',
        textareaBorderRadius: '4px',
        uploadBgColor: '#fff',
        textareaBorderStyle: 'solid',
        btnCancelWidth: '88px',
        textareaHeight: '120px',
        dateBgColor: '#fff',
        btnSaveBorderRadius: '4px',
        uploadLableFontSize: '14px',
        textareaBorderWidth: '1px',
        inputLableColor: '#606266',
        addEditBoxColor: '#fff',
        dateIconFontSize: '14px',
        btnSaveBgColor: 'rgba(252, 201, 51, 1)',
        uploadIconFontColor: '#8c939d',
        textareaBorderColor: '#DCDFE6',
        btnCancelBgColor: 'rgba(220, 223, 230, 1)',
        selectLableColor: '#606266',
        btnSaveBorderStyle: 'solid',
        dateBorderWidth: '1px',
        dateLableFontSize: '14px',
        dateBorderRadius: '4px',
        btnCancelBorderStyle: 'solid',
        selectLableFontSize: '14px',
        selectBorderStyle: 'solid',
        selectIconFontColor: '#C0C4CC',
        btnCancelHeight: '44px',
        inputHeight: '40px',
        btnCancelFontColor: 'rgba(81, 81, 81, 1)',
        dateBorderColor: '#DCDFE6',
        dateIconFontColor: '#C0C4CC',
        uploadBorderStyle: 'solid',
        dateBorderStyle: 'solid',
        dateLableColor: '#606266',
        dateFontSize: '14px',
        inputBorderWidth: '1px',
        uploadIconFontSize: '28px',
        selectHeight: '40px',
        inputFontColor: '#606266',
        // uploadHeight: '40px',
        uploadHeight: '60px',
        textareaLableColor: '#606266',
        textareaLableFontSize: '14px',
        btnCancelFontSize: '14px',
        inputBorderStyle: 'solid',
        btnCancelBorderRadius: '4px',
        inputBgColor: '#fff',
        inputLableFontSize: '14px',
        uploadLableColor: '#606266',
        uploadBorderRadius: '4px',
        btnSaveHeight: '44px',
        selectBgColor: '#fff',
        btnSaveWidth: '88px',
        selectIconFontSize: '14px',
        dateHeight: '40px',
        selectBorderColor: '#DCDFE6',
        inputBorderColor: '#DCDFE6',
        uploadBorderColor: '#DCDFE6',
        textareaFontColor: '#606266',
        selectBorderWidth: '1px',
        dateFontColor: '#606266',
        btnCancelBorderWidth: '0px',
        uploadBorderWidth: '1px',
        textareaFontSize: '14px',
        selectBorderRadius: '4px',
        selectFontColor: '#606266',
        btnSaveBorderColor: '#409EFF',
        btnSaveBorderWidth: '0px',
      },
      id: '',
      type: '',
      ro: {
        zuoyebianhao: false,
        zuoyebiaoti: false,
        zuoyewenjian: false,
        zuoyeneirong: false,
        faburiqi: false,
        gonghao: false,
        jiaoshixingming: false,
        banji: false,
      },
      ruleForm: {
        zuoyebianhao: '',
        zuoyebiaoti: '',
        zuoyewenjian: '',
        zuoyeneirong: '',
        faburiqi: '',
        gonghao: '',
        jiaoshixingming: '',
        banji: '',
      },
      // 校验规则
      rules: {
        zuoyebianhao: [],
        zuoyebiaoti: [{ required: true, message: '作业标题不能为空', trigger: 'blur' }],
        zuoyewenjian: [],
        zuoyeneirong: [],
        faburiqi: [],
        gonghao: [],
        jiaoshixingming: [],
        banji: [],
      },
      banjiOptions: [],
      // 作业列表
      works: [
        {
          name: '第一章节-作业01',
          url: 'http://localhost:8080/springboot4prsj/upload/work01.txt',
        },
        {
          name: '第一章节-作业02',
          url: 'http://localhost:8080/springboot4prsj/upload/work02.txt',
        },
        {
          name: '第一章节-作业03',
          url: 'http://localhost:8080/springboot4prsj/upload/work03.txt',
        },
        {
          name: '第一章节-作业04',
          url: 'http://localhost:8080/springboot4prsj/upload/work04.txt',
        },
        {
          name: '第一章节-作业05',
          url: 'http://localhost:8080/springboot4prsj/upload/work05.txt',
        },
      ],
    }
  },
  props: ['parent'],
  computed: {},
  created() {
    this.ruleForm.faburiqi = this.getCurDate()
    this.addEditStyleChange()
    this.addEditUploadStyleChange()
  },
  methods: {
    // 下载
    download(file) {
      window.open(`${file}`)
    },
    // 初始化
    init(id, type) {
      if (id) {
        this.id = id
        this.type = type
      }
      if (this.type == 'info' || this.type == 'else') {
        this.info(id)
      } else if (this.type == 'cross') {
        var obj = this.$storage.getObj('crossObj')
        for (var o in obj) {
          if (o == 'zuoyebianhao') {
            this.ruleForm.zuoyebianhao = obj[o]
            this.ro.zuoyebianhao = true
            continue
          }
          if (o == 'zuoyebiaoti') {
            this.ruleForm.zuoyebiaoti = obj[o]
            this.ro.zuoyebiaoti = true
            continue
          }
          if (o == 'zuoyewenjian') {
            this.ruleForm.zuoyewenjian = obj[o]
            this.ro.zuoyewenjian = true
            continue
          }
          if (o == 'zuoyeneirong') {
            this.ruleForm.zuoyeneirong = obj[o]
            this.ro.zuoyeneirong = true
            continue
          }
          if (o == 'faburiqi') {
            this.ruleForm.faburiqi = obj[o]
            this.ro.faburiqi = true
            continue
          }
          if (o == 'gonghao') {
            this.ruleForm.gonghao = obj[o]
            this.ro.gonghao = true
            continue
          }
          if (o == 'jiaoshixingming') {
            this.ruleForm.jiaoshixingming = obj[o]
            this.ro.jiaoshixingming = true
            continue
          }
          if (o == 'banji') {
            this.ruleForm.banji = obj[o]
            this.ro.banji = true
            continue
          }
        }
      }
      // 获取用户信息
      this.$http({
        url: `${this.$storage.get('sessionTable')}/session`,
        method: 'get',
      }).then(({ data }) => {
        if (data && data.code === 0) {
          var json = data.data
          if (json.gonghao != '' && json.gonghao) {
            this.ruleForm.gonghao = json.gonghao
          }
          if (json.jiaoshixingming != '' && json.jiaoshixingming) {
            this.ruleForm.jiaoshixingming = json.jiaoshixingming
          }
          if (json.banji != '' && json.banji) {
            this.ruleForm.banji = json.banji
          }
        } else {
          this.$message.error(data.msg)
        }
      })
      // 获取班级信息
      this.$http({
        url: `option/banjixinxi/banji`,
        method: 'get',
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.banjiOptions = data.data
        } else {
          this.$message.error(data.msg)
        }
      })
    },
    // 多级联动参数
    info(id) {
      this.$http({
        url: `zuoyexinxi/info/${id}`,
        method: 'get',
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.ruleForm = data.data
          //解决前台上传图片后台不显示的问题
          let reg = new RegExp('../../../upload', 'g') //g代表全部
        } else {
          this.$message.error(data.msg)
        }
      })
    },
    // 提交
    onSubmit() {
      // ${column.compare}
      // ${column.compare}
      // ${column.compare}
      // ${column.compare}
      // ${column.compare}
      // ${column.compare}
      // ${column.compare}
      // ${column.compare}

      this.$refs['ruleForm'].validate((valid) => {
        // 参数认证通过
        if (valid) {
          this.$http({
            url: `zuoyexinxi/${!this.ruleForm.id ? 'save' : 'update'}`,
            method: 'post',
            data: this.ruleForm,
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.parent.showFlag = true
                  this.parent.addOrUpdateFlag = false
                  this.parent.zuoyexinxiCrossAddOrUpdateFlag = false
                  this.parent.search()
                  this.parent.contentStyleChange()
                },
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }
      })
    },
    // 获取uuid
    getUUID() {
      return new Date().getTime()
    },
    // 返回
    back() {
      this.parent.showFlag = true
      this.parent.addOrUpdateFlag = false
      this.parent.zuoyexinxiCrossAddOrUpdateFlag = false
      this.parent.contentStyleChange()
    },
    zuoyewenjianUploadChange(fileUrls) {
      this.ruleForm.zuoyewenjian = fileUrls
      this.addEditUploadStyleChange()
    },
    addEditStyleChange() {
      this.$nextTick(() => {
        // input
        document.querySelectorAll('.addEdit-block .input .el-input__inner').forEach((el) => {
          el.style.height = this.addEditForm.inputHeight
          el.style.color = this.addEditForm.inputFontColor
          el.style.fontSize = this.addEditForm.inputFontSize
          el.style.borderWidth = this.addEditForm.inputBorderWidth
          el.style.borderStyle = this.addEditForm.inputBorderStyle
          el.style.borderColor = this.addEditForm.inputBorderColor
          el.style.borderRadius = this.addEditForm.inputBorderRadius
          el.style.backgroundColor = this.addEditForm.inputBgColor
        })
        document.querySelectorAll('.addEdit-block .input .el-form-item__label').forEach((el) => {
          el.style.lineHeight = this.addEditForm.inputHeight
          el.style.color = this.addEditForm.inputLableColor
          el.style.fontSize = this.addEditForm.inputLableFontSize
        })
        // select
        document.querySelectorAll('.addEdit-block .select .el-input__inner').forEach((el) => {
          el.style.height = this.addEditForm.selectHeight
          el.style.color = this.addEditForm.selectFontColor
          el.style.fontSize = this.addEditForm.selectFontSize
          el.style.borderWidth = this.addEditForm.selectBorderWidth
          el.style.borderStyle = this.addEditForm.selectBorderStyle
          el.style.borderColor = this.addEditForm.selectBorderColor
          el.style.borderRadius = this.addEditForm.selectBorderRadius
          el.style.backgroundColor = this.addEditForm.selectBgColor
        })
        document.querySelectorAll('.addEdit-block .select .el-form-item__label').forEach((el) => {
          el.style.lineHeight = this.addEditForm.selectHeight
          el.style.color = this.addEditForm.selectLableColor
          el.style.fontSize = this.addEditForm.selectLableFontSize
        })
        document.querySelectorAll('.addEdit-block .select .el-select__caret').forEach((el) => {
          el.style.color = this.addEditForm.selectIconFontColor
          el.style.fontSize = this.addEditForm.selectIconFontSize
        })
        // date
        document.querySelectorAll('.addEdit-block .date .el-input__inner').forEach((el) => {
          el.style.height = this.addEditForm.dateHeight
          el.style.color = this.addEditForm.dateFontColor
          el.style.fontSize = this.addEditForm.dateFontSize
          el.style.borderWidth = this.addEditForm.dateBorderWidth
          el.style.borderStyle = this.addEditForm.dateBorderStyle
          el.style.borderColor = this.addEditForm.dateBorderColor
          el.style.borderRadius = this.addEditForm.dateBorderRadius
          el.style.backgroundColor = this.addEditForm.dateBgColor
        })
        document.querySelectorAll('.addEdit-block .date .el-form-item__label').forEach((el) => {
          el.style.lineHeight = this.addEditForm.dateHeight
          el.style.color = this.addEditForm.dateLableColor
          el.style.fontSize = this.addEditForm.dateLableFontSize
        })
        document.querySelectorAll('.addEdit-block .date .el-input__icon').forEach((el) => {
          el.style.color = this.addEditForm.dateIconFontColor
          el.style.fontSize = this.addEditForm.dateIconFontSize
          el.style.lineHeight = this.addEditForm.dateHeight
        })
        // upload
        let iconLineHeight = parseInt(this.addEditForm.uploadHeight) - parseInt(this.addEditForm.uploadBorderWidth) * 2 + 'px'
        document.querySelectorAll('.addEdit-block .upload .el-upload--picture-card').forEach((el) => {
          el.style.width = this.addEditForm.uploadHeight
          el.style.height = this.addEditForm.uploadHeight
          el.style.borderWidth = this.addEditForm.uploadBorderWidth
          el.style.borderStyle = this.addEditForm.uploadBorderStyle
          el.style.borderColor = this.addEditForm.uploadBorderColor
          el.style.borderRadius = this.addEditForm.uploadBorderRadius
          el.style.backgroundColor = this.addEditForm.uploadBgColor
        })
        document.querySelectorAll('.addEdit-block .upload .el-form-item__label').forEach((el) => {
          el.style.lineHeight = this.addEditForm.uploadHeight
          el.style.color = this.addEditForm.uploadLableColor
          el.style.fontSize = this.addEditForm.uploadLableFontSize
        })
        document.querySelectorAll('.addEdit-block .upload .el-icon-plus').forEach((el) => {
          el.style.color = this.addEditForm.uploadIconFontColor
          el.style.fontSize = this.addEditForm.uploadIconFontSize
          el.style.lineHeight = iconLineHeight
          el.style.display = 'block'
        })
        // 多文本输入框
        document.querySelectorAll('.addEdit-block .textarea .el-textarea__inner').forEach((el) => {
          el.style.height = this.addEditForm.textareaHeight
          el.style.color = this.addEditForm.textareaFontColor
          el.style.fontSize = this.addEditForm.textareaFontSize
          el.style.borderWidth = this.addEditForm.textareaBorderWidth
          el.style.borderStyle = this.addEditForm.textareaBorderStyle
          el.style.borderColor = this.addEditForm.textareaBorderColor
          el.style.borderRadius = this.addEditForm.textareaBorderRadius
          el.style.backgroundColor = this.addEditForm.textareaBgColor
        })
        document.querySelectorAll('.addEdit-block .textarea .el-form-item__label').forEach((el) => {
          // el.style.lineHeight = this.addEditForm.textareaHeight
          el.style.color = this.addEditForm.textareaLableColor
          el.style.fontSize = this.addEditForm.textareaLableFontSize
        })
        // 保存
        document.querySelectorAll('.addEdit-block .btn .btn-success').forEach((el) => {
          el.style.width = this.addEditForm.btnSaveWidth
          el.style.height = this.addEditForm.btnSaveHeight
          el.style.color = this.addEditForm.btnSaveFontColor
          el.style.fontSize = this.addEditForm.btnSaveFontSize
          el.style.borderWidth = this.addEditForm.btnSaveBorderWidth
          el.style.borderStyle = this.addEditForm.btnSaveBorderStyle
          el.style.borderColor = this.addEditForm.btnSaveBorderColor
          el.style.borderRadius = this.addEditForm.btnSaveBorderRadius
          el.style.backgroundColor = this.addEditForm.btnSaveBgColor
        })
        // 返回
        document.querySelectorAll('.addEdit-block .btn .btn-close').forEach((el) => {
          el.style.width = this.addEditForm.btnCancelWidth
          el.style.height = this.addEditForm.btnCancelHeight
          el.style.color = this.addEditForm.btnCancelFontColor
          el.style.fontSize = this.addEditForm.btnCancelFontSize
          el.style.borderWidth = this.addEditForm.btnCancelBorderWidth
          el.style.borderStyle = this.addEditForm.btnCancelBorderStyle
          el.style.borderColor = this.addEditForm.btnCancelBorderColor
          el.style.borderRadius = this.addEditForm.btnCancelBorderRadius
          el.style.backgroundColor = this.addEditForm.btnCancelBgColor
        })
      })
    },
    addEditUploadStyleChange() {
      this.$nextTick(() => {
        document.querySelectorAll('.addEdit-block .upload .el-upload-list--picture-card .el-upload-list__item').forEach((el) => {
          el.style.width = this.addEditForm.uploadHeight
          el.style.height = this.addEditForm.uploadHeight
          el.style.borderWidth = this.addEditForm.uploadBorderWidth
          el.style.borderStyle = this.addEditForm.uploadBorderStyle
          el.style.borderColor = this.addEditForm.uploadBorderColor
          el.style.borderRadius = this.addEditForm.uploadBorderRadius
          el.style.backgroundColor = this.addEditForm.uploadBgColor
        })
      })
    },
  },
}
</script>
<style lang="scss">
.editor {
  height: 500px;

  & /deep/ .ql-container {
    height: 310px;
  }
}
.amap-wrapper {
  width: 100%;
  height: 500px;
}
.search-box {
  position: absolute;
}
.addEdit-block {
  margin: -10px;
}
.detail-form-content {
  padding: 12px;
}
.btn .el-button {
  padding: 0;
}
</style>
