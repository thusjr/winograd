<template>
  <div>
    <el-form
      :model="form"
      ref="form"
      label-width="80px"
      :inline="false"
      size="normal"
    >
      <el-form-item label="用户名">
        <el-input v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item label="密    码" style="white-space: pre">
        <el-input show-password v-model="form.password"></el-input>
      </el-form-item>
    </el-form>

    <span slot="footer">
      <el-button @click="cancel">取 消</el-button>
      <el-button type="primary" @click="login">登 录</el-button>
    </span>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'UserLogin',
  props: {
    dialogVisible: {
      type: Boolean,
      default: () => true
    }
  },
  data () {
    return {
      form: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    cancel: function () {
      this.form.username = ''
      this.form.password = ''
      this.$emit('cancelLogin')
    },
    login: function () {
      axios
        .post('/user/login', {
          name: this.form.username,
          password: this.form.password
        })
        .then((res) => {
          console.log(res)
          if (res.status === 200) {
            localStorage.setItem('logFlag', true)
            localStorage.setItem('username', res.data.name)
            localStorage.setItem('useremail', res.data.email)
            localStorage.setItem('password', res.data.password)
            window.open('/home', '_self')
          }
        })
        .catch(function (error) {
          if (error.response) {
            alert('登录失败')
          }
        })
    }
  }
}
</script>
