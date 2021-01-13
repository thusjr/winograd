<template>
  <ul class="infinite-list"  v-infinite-scroll="load"  infinite-scroll-disabled="disabled" >
    <NewsCard v-for="i in count"
      :key="i"
      :singleNews="newsList[i]" :hasSummary="hasSummary">
    </NewsCard>
  </ul>
</template>

<script>
import NewsCard from '@/components/NewsCard'
import axios from 'axios'

export default {
  name: 'NewsScrollList',
  components: {
    NewsCard
  },
  props: {
    keyWord: String,
    newsType: String
  },
  data () {
    return {
      newsList: Array,
      count: 0,
      entry: null,
      curPage: Number,
      hasSummary: true,
      loading: false,
      totalnewscount: 0
    }
  },
  watch: {
    keyWord () {
      if (!this.keyWord) {
        return
      }
      axios
        .get('api/search', {
          params: {
            'entry': this.keyWord,
            'page': 1
          }
        })
        .then((res) => {
          this.newsList = res.data.newsList
          this.entry = this.keyWord
          this.curPage = 1
          this.count = this.newsList.length
          this.totalnewscount = res.data.recordCount
          this.hasSummary = true
        })
        .catch(function (error) {
          if (error.response) {
            alert('搜索失败')
          }
        })
    },
    newsType () {
      if (!this.newsType) {
        return
      }
      axios
        .get('api/latest', {
          params: {
            'type': this.newsType
          }
        })
        .then((res) => {
          this.newsList = res.data
          console.log(res.data)
          this.curPage = 1
          this.hasSummary = false
          this.count = this.newsList.length
        })
        .catch(function (error) {
          if (error.response) {
            alert('加载失败')
          }
        })
    }
  },
  computed: {
    noMore () {
      // 当数量大于新闻总数时停止加载
      return this.count >= this.totalnewscount - 1
    },
    disabled () {
      return this.loading || this.noMore
    }
  },
  methods: {
    load: function () {
      this.loading = true
      setTimeout(() => {
        this.curPage += 1 // 页数+1
        this.loading = false
        this.getnews() // 调用接口，此时页数+1，查询下一页数据
      }, 2000)
    },
    getnews: function () {
      axios
        .get('api/search', {
          params: {
            'entry': this.entry,
            'page': this.curPage
          }
        })
        .then((res) => {
          this.newsList = this.newsList.concat(res.data.newsList)
          this.count = this.newsList.length
        })
        .catch(function (error) {
          if (error.response) {
            alert('加载失败')
          }
        })
    }
  }
}
</script>
