<template>
  <div class="login_container">
    <div class="login_box">
      <!-- logo部分 -->
      <div class="avatar_box">
        <img src="../assets/logo-topbar.gif" alt="avatar" />
        <span>jPetStore后台管理系统</span>
      </div>
      <div>
        <el-form
          ref="loginFormRef"
          :model="loginForm"
          :rules="loginFormRules"
          label-width="60px"
          class="login_form"
        >
          <el-form-item label="账号" prop="username"><!--prop用来指明验证规则-->
            <el-input v-model="loginForm.username" prefix-icon="iconfont icon-user"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input
              v-model="loginForm.password"
              type="password"
              prefix-icon="iconfont icon-3702mima"
            ></el-input>
            <div class="register">没有账号？
              <el-link type="primary" @click="goToRegister">注册</el-link>
            </div>
          </el-form-item>
          <el-form-item class="btns">
            <el-button type="primary" @click="login">登录</el-button>
            <el-button type="info" @click="resetLoginForm">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      loginForm: {
        username: 'asd',
        password: '111111'
      },
      // 表单验证
      loginFormRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入用户密码', trigger: 'blur' },
          { min: 2, max: 18, message: '长度在 2 到 18 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    // 表单重置按钮
    resetLoginForm () {
      this.$refs.loginFormRef.resetFields()
    },
    login () {
      this.$refs.loginFormRef.validate(async valid => {
        if (!valid) return
        const qs = require('qs')
        const { data: res } = await this.$http.post('/account/login', qs.stringify(this.loginForm))
        console.log(res)
        if (res.status !== 200) return this.$message.error('登录失败')
        this.$message.success('登录成功')
        window.sessionStorage.setItem('user', JSON.stringify(res.data))
        await this.$router.push('/home')
      })
    },
    goToRegister () {
      this.$router.push('/register')
    }
  }
}
</script>

<style lang="less" scoped>
  // lang="less" 支持less格式
.login_container {
  background-color: #2c6b48;
  height: 100%;
}
.login_box {
  width: 450px;
  height: 330px;
  background-color: #fff;
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 50%;
  -webkit-transform: translate(-50%, -50%);
  background-color: #fff;

  .avatar_box {
    width: 287px;
    height: 60px;
    border: 1px solid #eee;
    padding: 10px;
    box-shadow: 0 0 10px #ddd;
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;
    img {
      width: 100%;
      height: 100%;
      background-color: #383838;
    };
    span {
      padding-top: 24px;
      display: block;
      justify-content: center;
      text-align: center;
      font-weight:bold;
    }
  }
}
.login_form {
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}
.register {
  display: flex;
  justify-content: flex-end;
}
.btns {
  display: flex;
  justify-content: flex-end;
}
.info {
  font-size: 13px;
  margin: 10px 15px;
}
</style>
