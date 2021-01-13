<template>
  <div class="regis">
    <div class="regis-wrap">
      <el-row type="flex" justify="center">
        <el-form ref="regisForm" :model="data" status-icon label-width="60px">
          <h3>编辑</h3>
          <hr />
          <el-form-item prop="username" label="用户名">
            <el-input
              v-model="username"
              readonly />
          </el-form-item>
          <el-form-item prop="email" label="邮    箱" style="white-space: pre">
            <el-input v-model="useremail" placeholder="请输入邮箱"></el-input>
          </el-form-item>
        </el-form>
      </el-row>
      <div style="text-align: center">
        <el-button type="primary" icon @click="clkcancelBtn">返回</el-button>
        <el-button type="primary" icon @click="updateInfo">应用</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'ModifyInfoPage',
  data: function () {
    return {
      username: '',
      useremail: '',
      password: ''
    }
  },
  created: function () {
    this.username = localStorage.getItem('username')
    this.useremail = localStorage.getItem('useremail')
    this.password = localStorage.getItem('password')
  },
  methods: {
    updateInfo: function () {
      axios
        .post('/user/update', {
          name: this.username,
          password: this.password,
          email: this.useremail
        })
        .then((res) => {
          console.log(res.status)
          if (res.status === 200) {
            alert('修改成功!')
            localStorage.setItem('useremail', this.useremail)
            location.reload()
          }
        })
        .catch(function (error) {
          if (error.response) {
            alert('修改失败!')
          }
        })
    },
    clkcancelBtn: function () {
      this.$router.go(-1)
    }
  }
}
</script>

<style>
.page-header {
  height: 60px;
  width: 100%;
  box-shadow: 0 2px 5px #d4d4d4;
  margin: 0 0 5px 0;
  text-align: center;
}

.logo {
  height: 50px;
  margin: 10px 50px 0 50px;
}

.input-box {
  width: 300px;
}

.info-edit {
  margin: 10px 550px;
}

.btn {
  width: 80px;
  font-size: 20px;
  padding: 5px;
  border: 1px solid #ddd;
  border-radius: 5px;
  text-align: center;
  float: right;
  margin: 10px 30px 10px 0;
}

.btn:hover {
  cursor: pointer;
  background: #d4d4d4;
}

td {
  text-align: center;
}

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
