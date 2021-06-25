<template>
  <span></span>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: "LazyLoader",
  methods: mapActions(['loadPageAction']),
  mounted() {
    window.onscroll = () => {
      const el = document.documentElement
      const isButtonOfScreen = (window.innerHeight + window.pageYOffset ) >= el.offsetHeight - 1

      if(isButtonOfScreen){
        this.loadPageAction()
      }
    }
  },
  beforeDestroy() {
    window.onscroll = null
  }
}
</script>

<style scoped>

</style>