<template>
  <div class="register_container">
    <div class="register_box">
      <!-- logo部分 -->
      <div class="avatar_box">
        <img src="../assets/logo-topbar.gif" alt="avatar" />
        <span>jPetStore后台管理系统</span>
      </div>
      <div>
        <el-form
          ref="registerFormRef"
          :model="registerForm"
          :rules="registerFormRules"
          label-width="60px"
          class="register_form"
        >
          <el-form-item label="账号" prop="username"><!--prop用来指明验证规则-->
            <el-input v-model="registerForm.username" prefix-icon="iconfont icon-user"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input
              v-model="registerForm.password"
              type="password"
              prefix-icon="iconfont icon-3702mima"
            ></el-input>
            <div class="register">已有账号？
              <el-link type="primary" @click="goToLogin">登录</el-link>
            </div>
          </el-form-item>
          <el-form-item class="btns">
            <el-button type="primary" @click="register">注册</el-button>
            <el-button type="info" @click="resetregisterForm">重置</el-button>
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
      registerForm: {
        username: 'admin',
        password: '123456'
      },
      // 表单验证
      registerFormRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入用户密码', trigger: 'blur' },
          { min: 6, max: 18, message: '长度在 6 到 18 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    // 表单重置按钮
    resetregisterForm () {
      this.$refs.registerFormRef.resetFields()
    },
    register () {
      this.$refs.registerFormRef.validate(async valid => {
        if (!valid) return false
        const qs = require('qs')
        const { data: res } = await this.$http.post('/account/register', qs.stringify(this.registerForm))
        if (res.status !== 200) return this.$message.error('注册失败')
        this.$message.success('注册成功')
        window.sessionStorage.setItem('user', JSON.stringify(res.data))
        await this.$router.push('/home')
      })
    },
    goToLogin () {
      this.$router.push('/login')
    }
  }
}
</script>

<style lang="less" scoped>
  // lang="less" 支持less格式
  .register_container {
    background-color: #2c6b48;
    height: 100%;
  }
  .register_box {
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
      /*border-radius: 50%;*/
      padding: 10px;
      box-shadow: 0 0 10px #ddd;
      position: absolute;
      left: 50%;
      transform: translate(-50%, -50%);
      background-color: #fff;
      img {
        width: 100%;
        height: 100%;
        /*border-radius: 50%;*/
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
  .register_form {
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
