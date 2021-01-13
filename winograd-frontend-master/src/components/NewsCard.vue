<template>
    <div class="box-card">
      <a @click="openLink" target="_blank" >
        <div class="row">
          <article class="col news">
            <b>
              <div class="title">
                <span v-html="singleNews.title"></span>
              </div>
            </b>
            <div class="infostamp">{{singleNews.time+' '+singleNews.src}}</div>
            <div class="text" v-show="hasSummary">
              {{singleNews.summary.substr(0, 100)+'...'}}
            </div>
          </article>
          <el-image class="col img"
            :src=singleNews.picUrl
            fit="cover">
            <div slot="error" class="image-slot">
              <el-image :src="require('@/assets/winograd.png')" fit="cover" />
            </div>
          </el-image>
        </div>
      </a>
    </div>
</template>

<script>
export default {
  name: 'NewsCard',
  props: {
    singleNews: { // 传入单条新闻。TODO:之后可能需要再根据返回的结果修改
      id: Number,
      src: String,
      time: String,
      title: String,
      url: String,
      picUrl: String,
      summary: String
    },
    hasSummary: Boolean
  },
  methods: {
    openLink: function () {
      window.open(this.singleNews.url, '_blank')
    }
  }
}
</script>

<style>

div {
  margin: 0;
}

.col {
  padding: 0px;
}

.col.img {
  width: 144px;
  height: 144px;
  border: 1px solid #ddd;
  border-radius: 10px;
  float: right;
}

.col.news {
  width: 75%;
  float: left;
}

.title {
  font-size: 20px;
  font-weight: bold;
  padding-block-end: 1%;
}

.infostamp {
  font-size: 10px;
  font-weight: bold;
  padding-block-end: 2%;
}

.text {
  font-size: 14px;
  overflow: hidden;
}

.box-card {
  width: 720px;
  height: 146px;
  border: 1px solid #ddd;
  border-radius: 10px;
  margin: 0 0 10px 0;
  padding: 10px 10px 10px 15px;
}

.row:after {
  content: "";
  display: table;
  clear: both;
}

a:link {text-decoration: none;}
a:visited {text-decoration: none;}
a:hover {cursor: pointer;}
a:hover b{text-decoration: underline;}

</style>
