<template>
  <PageTemplate :request="request" />
</template>

<script>
import PageTemplate from '@/components/PageTemplate'

export default {
  name: 'GlobalPage',
  components: {
    PageTemplate
  },
  data: function () {
    return {
      request: {
        path: 'api/latest',
        parms: {
          'type': 'world'
        }
      }
    }
  }
}
</script>
