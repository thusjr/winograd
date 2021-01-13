<template>
    <el-container>
        <el-header>
            <img class="logo" src="@/assets/winograd.png" alt="logo" />
            <el-input class="search-box"
                placeholder="Search for news"
                prefix-icon="el-icon-search"
                v-model="searchKeyWord"
                @keyup.enter.native="searchBack"
                clearable />
            <el-popover ref="logpopover"
                placement="bottom"
                trigger="click">
                <UserLogPopover />
            </el-popover>
            <div class="user-logo el-icon-user-solid"
                v-popover:logpopover />
        </el-header>
        <el-container>
            <el-aside>
                <Menu />
            </el-aside>
            <el-main>
                <ul class="infinite-list"
                    v-infinite-scroll="scrollLoad"
                    infinite-scroll-disabled="disabled">
                    <DisplayTemplate v-for="(news, index) in newsList"
                        :key="index"
                        :singleNews="news" />
                </ul>
            </el-main>
        </el-container>
    </el-container>
</template>

<style>

.el-header {
    width: 100%;
    height: 60px;
    box-shadow: 0 2px 5px #d4d4d4;
    margin: 0 0 5px 0;
    position: relative;
}

.el-aside {
    display: block;
    position: absolute;
    left: 0;
    top: 65px;
}

.el-main {
    position: absolute;
    top: 65px;
    right: 0;
    bottom: 0;
    left: 330px;
    overflow-y: scroll;
}

.logo {
    height: 50px;
    margin: 10px 50px 0 50px;
}

/* /////////////////////////// */
/* The style of the search box */
.search-box {
    width: 550px;
    height: 40px;
    position: absolute;
    top: 10px;
    margin: 0 200px;
    border: 1px solid #e2e2e2;
    border-radius: 6px;
}

.search-box .el-input-group__prepend {
  border: none;
  background-color: transparent;
  padding: 0 10px 0 30px;
}
.search-box .el-input-group__append {
  border: none;
  background-color: transparent;
  padding: 0 10px 0 0;
}
.search-box .el-input__inner {
  border: none;
  margin-left: 10px;
  background-color: transparent;
}
.search-box .el-input__icon {
  padding-right: 8px;
}
.search-box .el-icon-search {
  color: #296acc;
  font-size: 18px;
}
.search-box:hover {
  box-shadow: 0px 0px 3px #b4b4b4;
  border: 0px;
  background: #ffffff;
}
.search-box:focus-within {
  box-shadow: 0px 0px 3px #b4b4b4;
  border: 0px;
  background: #ffffff;
}

/* /////////////////////////// */
/* The style of the user logo */

.user-logo {
  height: 24px;
  width: 24px;
  padding: 8px;
  position: absolute;
  right: 160px;
  top: 10px;
  border: 1px solid #ddd;
  border-radius: 50%;
  background: #aac7f4;
  font-size: 24px;
}

.user-logo:hover {
  cursor: pointer;
  border: 2px solid #d4d4d4;
}

</style>

<script>

import Menu from '@/components/Menu'
import DisplayTemplate from '@/components/DisplayTemplate'
import UserLogPopover from '@/components/userservice/Popover'
import axios from 'axios'

export default {
  name: 'PageTemplate',
  components: {
    Menu,
    DisplayTemplate,
    UserLogPopover
  },
  props: {
    request: {
      path: String,
      parms: Object
    }
  },
  data: function () {
    return {
      searchKeyWord: '',
      newsList: [],
      curPage: 0,
      totCount: Number,
      count: Number,
      loading: false
    }
  },
  methods: {
    load: function (path, parms) {
      axios
        .get(path, {
          params: parms
        })
        .then((res) => {
          if (res.data instanceof Array) {
            this.newsList = res.data
            this.count = this.newsList.length
            this.totCount = this.count
          } else {
            this.newsList = this.newsList.concat(res.data.newsList)
            this.totCount = res.data.recordCount
            this.count = this.newsList.length
          }
          this.curPage += 1
        })
        .catch(function (error) {
          if (error.response) {
            alert('搜索失败')
          }
        })
    },
    searchBack: function () {
      if (this.searchKeyWord === '') {
        return
      }
      localStorage.setItem('entry', this.searchKeyWord)
      window.open('/search', '_self')
    },
    scrollLoad: function () {
      if (this.searchKeyWord === '') {
        return
      }
      this.load('api/search', {'entry': this.searchKeyWord, 'page': this.curPage + 1})
    }
  },
  watch: {
    request: {
      immediate: true,
      handler: function () {
        this.load(this.request.path, this.request.parms)
        if (this.request.parms.entry) {
          this.searchKeyWord = this.request.parms.entry
        }
      }
    }
  },
  computed: {
    noMore: function () {
      return this.count > this.recordCount
    },
    disabled: function () {
      return this.loading || this.noMore
    }
  }
}
</script>
