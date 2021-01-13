<template>
  <div id="MainPage">
    <el-container>
      <el-header class="header">
        <div>
          <div style="float: left">
            <img
              style="height: 50px; vertical-align: bottom; margin-left: 50px"
              src="../assets/winograd.png"
              alt="logo"
            />
          </div>
          <SearchBox @getKeyWord="getKeyWord" :clr="clrSearchBox" />
          <div style="float: right; margin-right: 20px">
            <el-button type="primary" size="default" @click="userRegis">
              注 册
            </el-button>
          </div>
          <div style="float: right; margin-right: 20px">
            <el-button
              type="primary"
              size="default"
              @click="loginDialog.visible = true"
              v-if="loginDialog.loginSuccess == false"
            >
              登 录
            </el-button>
          </div>
          <div style="float: right; margin-right: 20px">
            <el-dropdown
              @command="UserMenuClick"
              v-if="loginDialog.loginSuccess == true"
            >
              <el-button type="primary">
                <i class="el-icon-user-solid" />
                {{ loginDialog.form.username }}
                <i class="el-icon-arrow-down el-icon--right"></i>
              </el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item></el-dropdown-item>
                <el-dropdown-item command="userCenter"
                  >我的主页</el-dropdown-item
                >
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </div>
      </el-header>
      <el-container>
        <el-aside class="aside">
          <MainPageAside @clickMenu="clickMenu" />
        </el-aside>
        <el-main class="mainContent">
          <!-- Main content -->
          <NewsScrollList :keyWord="keyWord" :newsType="newsType" />
        </el-main>
      </el-container>
    </el-container>
    <UserLogin
      :dialogVisible="loginDialog.visible"
      v-on:closeLogin="closeLogin"
      @userLogin="userLogin"
    />
  </div>
</template>

<script>
import SearchBox from '@/components/SearchBox'
import UserLogin from '@/components/UserLogin'
// import NewsCollection from '@/components/NewsCollection'
import NewsContent from '@/components/NewsContent'
import UserRegister from '@/components/UserRegister'
import NewsScrollList from '@/components/NewsScrollList'
import MainPageAside from '@/components/MainPageAside'

export default {
  name: 'MainPage',
  components: {
    SearchBox,
    UserLogin,
    // NewsCollection,
    NewsContent,
    UserRegister,
    NewsScrollList,
    MainPageAside
  },
  data () {
    return {
      loginDialog: {
        visible: false,
        form: {
          username: '',
          password: ''
        },
        loginSuccess: false
      },
      newsTabActive: 'home',
      keyWord: null,
      newsType: null,
      clrSearchBox: true
    }
  },
  methods: {
    closeLogin: function (vis) {
      this.loginDialog.visible = vis
    },
    userLogin: function (form) {
      this.loginDialog.loginSuccess = true
      this.loginDialog.form.username = form.username
      this.loginDialog.form.password = form.password
    },
    userRegis: function () {
      this.$router.push('/regis')
    },
    UserMenuClick: function (command) {
      if (command === 'logout') {
        this.loginDialog.loginSuccess = false
      } else if (command === 'userCenter') {
        console.log('userCenter')
      }
    },
    getKeyWord: function (kw) {
      this.keyWord = kw
      this.newsType = null
    },
    clickMenu: function (type) {
      this.newsType = type
      this.keyWord = null
      this.clrSearchBox = !this.clrSearchBox
    }
  }
}
</script>

<style>
.header {
  box-shadow: 0px 3px 5px #d4d4d4;
  text-align: center;
  margin: 20px 0px 20px 0px;
  min-width: 1000px;
  position: relative;
}

.el-aside {
  display: block;
  position: absolute;
  left: 0;
  top:90px;
  bottom: 0;
}

.el-main {
  position: absolute;
  left: 200px;
  right: 0;
  top:90px;
  bottom: 0;
  overflow-y: scroll;
}

.mainContent {
  text-align: left;
  padding: 0px;
  margin: 0px 0px 0px 80px;
  min-width: 1000px;
}
.footer {
  text-align: center;
  height: 40px;
  background-color: rgb(222, 239, 255);
  margin: 40px 0px 0px 0px;
  box-shadow: 0px 3px 5px #dfdfdf;
}
</style>
