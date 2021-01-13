<template>
  <div style="display: inline-block; width: 550px">
    <el-input
      style="width: 550px"
      placeholder="Search for news"
      v-model="input"
      class="searchBox"
      prefix-icon="el-icon-search"
      @keyup.enter.native="pushEnter"
      clearable
    >
    </el-input>
  </div>
</template>

<script>

export default {
  name: 'SearchBox',
  data () {
    return {
      input: ''
    }
  },
  props: {
    clr: true
  },
  methods: {
    pushEnter: function () {
      this.$emit('getKeyWord', this.input)
    }
  },
  watch: {
    clr () {
      this.input = ''
    }
  }
}
</script>

<style>
.searchBox {
  border-radius: 6px;
  border: 1px solid #e2e2e2;
  background: rgb(245, 245, 245);
}
.searchBox .el-input-group__prepend {
  border: none;
  background-color: transparent;
  padding: 0 10px 0 30px;
}
.searchBox .el-input-group__append {
  border: none;
  background-color: transparent;
  padding: 0 10px 0 0;
}
.searchBox .el-input__inner {
  border: none;
  margin-left: 10px;
  background-color: transparent;
}
.searchBox .el-input__icon {
  padding-right: 8px;
}
.searchBox .el-icon-search {
  color: #296acc;
  font-size: 18px;
}
.searchBox:hover {
  box-shadow: 0px 0px 3px #b4b4b4;
  border: 0px;
  background: #ffffff;
}
.searchBox:focus-within {
  box-shadow: 0px 0px 3px #b4b4b4;
  border: 0px;
  background: #ffffff;
}
</style>
