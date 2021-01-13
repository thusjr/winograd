<template>
    <PageTemplate :request="request" />
</template>

<script>
import PageTemplate from '@/components/PageTemplate'

export default {
  name: 'SearchBackPage',
  components: {
    PageTemplate
  },
  data: function () {
    return {
      request: {
        path: '/api/search',
        parms: {
          'entry': '',
          'page': 1
        }
      }
    }
  },
  created: function () {
    this.request.parms.entry = localStorage.getItem('entry')
  }
}
</script>
