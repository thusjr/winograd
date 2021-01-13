<template>
    <div style="width: 300px;padding:10px 0">
        <header style="text-align:center; margin:10px">
            <div class="popover-user-logo el-icon-user-solid"></div>
        </header>
        <body style="text-align:center">
            <div class="user-name">{{ username }}</div>
            <div class="user-email" v-if="login">{{ userInfo.useremail }}</div>
            <div class="info-manage-btn"
                @click="clkRegBtn" >
                {{ regBtn }}
            </div>
            <el-divider />
            <div class="logout-btn"
                @click="clkLogBtn"
                v-if="!tryingToLog">
                {{ logBtn }}
            </div>
            <div v-if="tryingToLog">
                <UserLogin @cancelLogin="tryingToLog = false" />
            </div>
        </body>
    </div>
</template>

<script>

import UserLogin from '@/components/UserLogin'

export default {
  name: 'UserLogPopover',
  components: {
    UserLogin
  },
  data: function () {
    return {
      userInfo: {
        username: '',
        useremail: '',
        password: ''
      },
      login: false,
      tryingToLog: false
    }
  },
  computed: {
    username: function () {
      if (!this.login) {
        return '未登录'
      } else {
        return this.userInfo.username
      }
    },
    regBtn: function () {
      if (!this.login) {
        return '注册新用户'
      } else {
        return '编辑您的个人信息'
      }
    },
    logBtn: function () {
      if (!this.login) {
        return '登录'
      } else {
        return '登出'
      }
    }
  },
  methods: {
    clkLogBtn: function () {
      if (!this.login) {
        this.tryingToLog = true
      } else {
        this.login = false
        localStorage.setItem('logFlag', false)
        localStorage.removeItem('username')
        localStorage.removeItem('useremail')
        localStorage.removeItem('passward')
        window.open('/', '_self')
      }
    },
    clkRegBtn: function () {
      if (!this.login) {
        window.open('/regis', '_self')
      } else {
        this.$router.push('/edit')
      }
    }
  },
  beforeMount: function () {
    this.login = localStorage.getItem('logFlag') === 'true'
    if (this.login) {
      this.userInfo.username = localStorage.getItem('username')
      this.userInfo.useremail = localStorage.getItem('useremail')
      this.userInfo.password = localStorage.getItem('password')
    }
  }
}
</script>

<style>

.popover-user-logo {
    height: 40px;
    width: 40px;
    padding: 4px;
    border: 1px solid #ddd;
    border-radius: 50%;
    background: #aac7f4;
    font-size: 40px;
}

.user-name {
    font-size: 18px;
    margin-bottom: 5px;
}

.user-email {
    font-size: 16px;
    margin-bottom: 5px;
}

.info-manage-btn {
    font-size: 18px;
    margin: 10px;
    width: 188px;
    height: 24px;
    padding: 3px;
    margin: 5px 50px;
    border: 1px solid #ddd;
    border-radius: 50px;
    text-align: center;
}

.info-manage-btn:hover {
    cursor: pointer;
    background: #d4d4d4;
}

.logout-btn {
    font-size: 18px;
    width: 100px;
    height: 24px;
    padding: 3px;
    margin: 0 100px;
    border: 1px solid #ddd;
    border-radius: 10px;
    text-align: center;
}

.logout-btn:hover {
    cursor: pointer;
    background: #d4d4d4;
}

</style>
