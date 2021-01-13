<template>
    <div class="box-card" @click="openNews">
      <div class="row">
        <article class="col news">
          <div class="newsTitle">
            <span v-html="singleNews.title"></span>
          </div>
          <div class="infostamp">{{singleNews.time+' '+singleNews.src}}</div>
          <div class="text" v-if="!(singleNews.summary === '')"
            v-html="newsText" />
        </article>
        <el-image class="col img"
          :src="singleNews.picUrl"
          fit="cover"
          v-if="!(singleNews.picUrl === '')">
          <div slot="error" class="image-slot">
            <el-image :src="require('@/assets/winograd.png')" fit="cover" />
          </div>
        </el-image>
      </div>
    </div>
</template>

<script>
export default {
  name: 'DisplayTemplate',
  props: {
    singleNews: {
      id: Number,
      src: String,
      time: String,
      title: String,
      url: String,
      picUrl: String,
      summary: String
    }
  },
  methods: {
    openNews: function () {
      window.open(this.singleNews.url, '_blank')
    }
  },
  computed: {
    newsText: function () {
      if (this.singleNews.summary.length <= 110) {
        return this.singleNews.summary + '...'
      } else {
        return this.singleNews.summary.substr(0, 90) + '...'
      }
    }
  }
}
</script>

<style>

.box-card {
  width: 720px;
/*  height: 146px;*/
  max-height: 146px;
  border: 1px solid #ddd;
  border-radius: 10px;
  margin: 0 0 10px 0;
  padding: 10px 10px 10px 15px;
}

.box-card:link {
  text-decoration: none;
}

.box-card:visited {
  text-decoration: none;
}

.box-card:hover {
  cursor: pointer;
}

.box-card:hover .newsTitle {
  text-decoration: underline;
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
  width: 70%;
  float: left;
}

.newsTitle {
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

.row:after {
  content: "";
  display: table;
  clear: both;
}

</style>
