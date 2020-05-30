<template>
  <el-form :model="userForm">
    <el-form-item label="Name">
      <el-input v-model="userForm.username" />
    </el-form-item>
    <el-form-item label="password">
      <el-input v-model="userForm.password" />
    </el-form-item>
    <el-form-item label="Email">
      <el-input v-model="userForm.email" />
    </el-form-item>
    <el-form-item label="mobile">
      <el-input v-model="userForm.phone" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit">Update</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {

  data () {
    return {
      user: JSON.parse(window.sessionStorage.getItem('user')),
      userForm: {
        username: '',
        password: '',
        email: '',
        phone: ' '
      }
    }
  },
  created () {
    this.userForm.username = this.user.username
    this.userForm.password = this.user.password
    this.userForm.email = this.user.email
    this.userForm.phone = this.user.phone
  },
  methods: {
    submit () {
      const qs = require('qs')
      this.$http.post('/account/editSupplier', qs.stringify(this.userForm))
      this.$message({
        message: '账号信息更新成功',
        type: 'success',
        duration: 5 * 1000
      })
    }
  }
}
</script>
