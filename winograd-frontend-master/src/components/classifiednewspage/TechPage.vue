<template>
  <PageTemplate :request="request" />
</template>

<script>
import PageTemplate from '@/components/PageTemplate'

export default {
  name: 'TechPage',
  components: {
    PageTemplate
  },
  data: function () {
    return {
      request: {
        path: 'api/latest',
        parms: {
          'type': 'tech'
        }
      }
    }
  }
}
</script>
