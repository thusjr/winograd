<template>
  <div class="regis">
    <div class="regis-wrap">
      <el-row type="flex" justify="center">
        <el-form ref="regisForm" :model="user" status-icon label-width="60px">
          <h3>注册</h3>
          <hr />
          <el-form-item prop="username" label="用户名">
            <el-input
              v-model="user.username"
              placeholder="请输入用户名"
            ></el-input>
          </el-form-item>
          <el-form-item prop="email" label="邮    箱" style="white-space:pre">
            <el-input v-model="user.email" placeholder="请输入邮箱"></el-input>
          </el-form-item>
          <el-form-item prop="password" label="密    码" style="white-space:pre">
            <el-input
              v-model="user.password"
              show-password
              placeholder="请输入密码"
            ></el-input>
          </el-form-item>
        </el-form>

      </el-row>
      <div style="text-align:center">
            <el-button type="primary" icon @click="doRegister()"
              >注册</el-button
            >
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'UserRegister',
  data () {
    return {
      user: {
        username: '',
        email: '',
        password: ''
      }
    }
  },
  methods: {
    doRegister () {
      if (!this.user.username) {
        this.$message.error({message: '请输入用户名！', duration: 1000})
      } else if (!this.user.email) {
        this.$message.error({message: '请输入邮箱！', duration: 1000})
      } else if (this.user.email != null) {
        var reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
        if (!reg.test(this.user.email)) {
          this.$message.error({message: '请输入有效的邮箱！', duration: 1000})
        } else if (!this.user.password) {
          this.$message.error({message: '请输入密码！', duration: 1000})
        } else {
          axios
            .post('/user/register', {
              name: this.user.username,
              password: this.user.password,
              email: this.user.email
            })
            .then((res) => {
              if (res.status === 200) {
                localStorage.setItem('username', this.user.username)
                localStorage.setItem('useremail', this.user.email)
                localStorage.setItem('password', this.user.password)
                localStorage.setItem('logFlag', true)
                this.$router.push({ path: '/home' })
                alert('注册成功！')
              }
            })
            .catch(function (error) {
              if (error.response) {
                alert('注册失败')
              }
            })
        }
      }
    }
  }
}
</script>

<style scoped>
.regis {
  background-image: linear-gradient(to right, #b8d9ff, #b8a8ff);
  position: absolute;
  left: 0;
  right: 0;
  bottom: 0;
  top: 0;
}
.regis-wrap {
  border-radius: 5px;
  width: 450px;
  height: 350px;
  margin: 215px auto;
  line-height: 20px;
  background-color: rgb(239, 234, 255);
}

h3 {
  color: #0babeab8;
  font-size: 24px;
}
hr {
  background-color: #444;
  margin: 20px auto;
}

.el-button {
  width: 120px;
}
</style>
