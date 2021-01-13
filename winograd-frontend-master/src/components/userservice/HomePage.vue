<template>
    <PageTemplate :request="request" />
</template>

<script>

import PageTemplate from '@/components/PageTemplate'

export default {
  name: 'HomePage',
  components: {
    PageTemplate
  },
  data: function () {
    return {
      login: Boolean,
      request: {
        path: 'api/latest',
        parms: {
          'type': '24hours'
        }
      }
    }
  }
}
</script>
