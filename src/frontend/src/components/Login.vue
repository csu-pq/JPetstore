<template>
  <div class="login_container">
    <div class="login_box">
      <!-- logo部分 -->
      <div class="avatar_box">
        <img src="../assets/logo-topbar.gif" alt="avatar" />
        <span>jPetStore后台管理系统</span>
      </div>
      <!-- 登录表单 model绑定数据，rules表单验证-->
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
        username: '123',
        password: '11'
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
      // 表单预验证
      // valid：bool类型,是整个表单验证完之后的结果，表单验证完后将valid的值作为参数传入回调函数并执行回调函数，可以看文档
      // 当程序跑起来时，一般情况下，应用程序（application program）会时常通过API调用库里所预先备好的函数。但是有些库函数（library function）却要求应用先传给它一个函数，好在合适的时候调用，以完成目标任务。这个被传入的、后又被调用的函数就称为回调函数（callback function）。
      this.$refs.loginFormRef.validate(async valid => {
        // console.log(valid)，valid为真则继续发送请求。
        if (!valid) return false
        const qs = require('qs')
        const { data: res } = await this.$http.post('/account/login', qs.stringify(this.loginForm))
        console.log(res)
        if (res.status !== 200) return this.$message.error('登录失败')
        this.$message.success('登录成功')
        // 1、将登陆成功之后的token, 保存到客户端的sessionStorage中; localStorage中是持久化的保存
        //   1.1 项目中出现了登录之外的其他API接口，必须在登陆之后才能访问
        //   1.2 token 只应在当前网站打开期间生效，所以将token保存在sessionStorage中
        // TODO window.sessionStorage.setItem('token', res.data.token)
        window.sessionStorage.setItem('user', JSON.stringify(res.data))
        // 2、通过编程式导航跳转到后台主页, 路由地址为：/home
        this.$router.push('/home')
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
