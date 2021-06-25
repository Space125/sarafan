<template>
  <v-row class="px-6 mt-4">
    <v-text-field
        clearable
        label="New message"
        placeholder="Write something"
        v-model="text"
        @keyup.enter="save"
    />
    <v-btn @click="save">
      Save
    </v-btn>
  </v-row>
</template>

<script>
import * as Sentry from "@sentry/vue"
import {mapActions} from 'vuex'

export default {
  props: ['messageAttr'],
  data() {
    return {
      text: '',
      id: null
    }
  },
  watch: {
    messageAttr(newVal) {
      this.text = newVal.text
      this.id = newVal.id
    }
  },
  methods: {
    ...mapActions(['addMessageAction', 'updateMessageAction']),
    save() {
      const message = {id: this.id, text: this.text}

      if (this.id) {
        this.updateMessageAction(message)
      } else {
        this.addMessageAction(message)
      }

      this.text = ''
      this.id = null
    }
  }
}
</script>

<style>

</style>