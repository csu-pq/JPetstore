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
      <el-input v-model="userForm.mobile" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit">Update</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  // props: {
  //   user: {
  //     type: Object,
  //     default: () => {
  //       return {
  //         name: '',
  //         email: ''
  //       }
  //     }
  //   }
  // },
  data () {
    return {
      user: JSON.parse(window.sessionStorage.getItem('user')),
      userForm: {
        username: '',
        password: '',
        email: '',
        mobile: ' '
      }
    }
  },
  created () {
    this.userForm.username = this.user.username
    this.userForm.password = this.user.password
    this.userForm.email = this.user.email
    this.userForm.mobile = this.user.phone
  },
  methods: {
    submit () {
      // TODO 表单验证
      this.$message({
        message: 'User information has been updated successfully',
        type: 'success',
        duration: 5 * 1000
      })
    }
  }
}
</script>
