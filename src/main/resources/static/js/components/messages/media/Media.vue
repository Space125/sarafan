<template>
  <v-card
      class="mx-auto"
      >
    <v-row align="center" justify="center">
      <v-col v-if="type === 'href'" cols="6">
        <v-img
            v-if="message.linkCover" :src="message.linkCover"
            height="200px">
        </v-img>
        <v-card-title>
          <div>
            <h3>
              <a :href="message.link">{{ message.linkTitle || message.link }}</a>
            </h3>
            <div v-if="message.linkDescription">{{ message.linkDescription }}</div>
          </div>
        </v-card-title>
      </v-col>
    </v-row>
    <v-row align="center" justify="center">
      <v-col v-if="type === 'image'" cols="6">
        <a :href="message.link">
          <v-img
              v-if="message.linkCover" :src="message.linkCover"
              height="200px">
          </v-img>
          {{ message.link }}
        </a>
      </v-col>
    </v-row>
    <v-row align="center" justify="center">
      <v-col v-if="type === 'youtube'" cols="6">
        <you-tube :src="message.link"></you-tube>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import YouTube from "./YouTube.vue";

export default {
  name: 'Media',
  props: ['message'],
  components: {YouTube},
  data() {
    return {
      type: 'href'
    }
  },
  beforeMount() {
    if (this.message.link.indexOf('youtu') > -1) {
      this.type = 'youtube'
    } else if (this.message.link.match(/\.(jpeg|jpg|gif|png)$/) !== null) {
      this.type = 'image'
    } else {
      this.type = 'href'
    }
  }
}
</script>

<style scoped>

</style>