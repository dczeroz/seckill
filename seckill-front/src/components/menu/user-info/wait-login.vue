<template>
  <!-- 未登录 -->
  <div class="wait-login">
    <div class="user" @click="openDialog">
      <i class="iconfont icon-seckillyonghu"></i>
      <p>未登录</p>
    </div>
    <!-- 登录框 -->
    <el-dialog title="登录" :visible.sync="dialogFormVisible" :before-close="handleClose" width="24rem">
      <el-form  v-loading="loading"
                element-loading-text="拼命登录中"
                element-loading-spinner="el-icon-loading"
                element-loading-background="#ffffff"
                :model="userLoginForm" 
                ref="userLoginForm">
        <el-form-item label="手机" :label-width="formLabelWidth" prop="phone" 
                      :rules="[
                      {required: true, message: '手机账号不能为空', trigger: 'blur'},
                      {pattern: /^1[34578]\d{9}$/, message: '手机账号格式不对'}]">
        <el-input placeholder="请输入手机账号" v-model="userLoginForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth" prop="password"
                      :rules="[
                      {required: true, message: '密码不能为空', trigger: 'blur'}
                      ]">
        <el-input placeholder="请输入密码" v-model="userLoginForm.password" show-password></el-input>
        </el-form-item>
      </el-form>
      <!-- 更多登录方式 -->
      <div class="more-sign">
          <h6>社交帐号登录</h6>
          <ul>
            <!-- 微信QQ登录 -->
              <li><a id="weixin" class="weixin" href="#"><i class="iconfont icon-seckillweixin"/></a></li>
              <li><a id="qq" class="qq" target="_blank" href="#"><i class="iconfont icon-seckillqq"/></a></li>
          </ul>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="danger"  v-loading="loading" class="dialog-footer-left">注 册</el-button> 
        <el-button type="primary" v-loading="loading" @click="login('userLoginForm')">登 录</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import user from '@/api/user'
  export default {
    data() {
      return {
        dialogFormVisible: this.$store.getters.rodialogVisibleles,
        formLabelWidth: '3.6rem',
        userLoginForm: {
          phone: '',
          password: ''
        },
        loading: this.$store.getters.loading
      }
    },
    methods: {
      // 打开登录弹窗
      openDialog() {
        this.$store.commit('setDialogVisible')
      },
      handleClose() {
         this.$store.commit('setDialogVisible')
      },
      login(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$store.commit('setLoading',true)
            user.login(this.userLoginForm)
            .then(res=> {
              this.$store.commit('setIsLogin')
              this.handleClose()
            })
            this.$store.commit('setLoading',false)
          } else {
            return false;
          }
        });
      }
    },
    computed:{
      // dialogFormVisible值
      getDialog() {
        this.dialogFormVisible = this.$store.getters.rodialogVisibleles
      },
      getLoading() {
        this.loading = this.$store.getters.loading
      }
    },
    watch: {
      // 监听getLogin计算属性
      getDialog() {
      },
      getLoading(){}
    },
  }
</script>

<style scoped>
  .user {
    display: flex;
    padding-top: 1rem;
    cursor: pointer;
  }
  .icon-seckillyonghu {
    font-size: 2.5rem;
    color: rgba(74, 74, 74);
    margin-left: 1.5rem;
  }
  .user p {
    margin-left: 1.2rem;
    margin-top: 0.5rem;
    color: #4a4a4a;
  }
  a {
    text-decoration: none;
  }
  .dialog-footer-left {
    float: left;
  }
  .more-sign {
    margin-top: 3rem;
  } 
  .more-sign h6 {
    position: relative;
    margin: 0 10px 10px;
    font-size: 12px;
    color: #b5b5b5;
  }
  .more-sign h6:after {
    content: "";
    border-top: 1px solid #b5b5b5;
    display: block;
    position: absolute;
    width: 180px;
    top: 5px;
    right: 30px;
  }
  .more-sign ul {
    margin-bottom: 10px;
    list-style: none;
    padding-left: 0;
  }
  .more-sign ul li {
    margin: 0 5px;
    display: inline-block;
  }
  .more-sign ul a {
      width: 50px;
      height: 50px;
      line-height: 50px;
      display: block;
  }
  .more-sign .icon-seckillweixin {
    color: #00bb29;
  }
  .more-sign .icon-seckillqq {
      color: #498ad5;
  }
  .more-sign ul i {
      font-size: 28px;
  }
</style>