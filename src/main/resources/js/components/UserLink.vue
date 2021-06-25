<template>
  <div v-if="user">
    <v-avatar
        v-if="user && user.userpic"
        :size="`${size || 36}`"
    >
      <img
          :src="user.userpic"
          :alt="user.name"
      >
    </v-avatar>
  <router-link
      :to="`/user/${user.id}`"
      class="px-2"
  >
    <span>{{ userName }}</span>
  </router-link>
  </div>
    <div v-else>
    <v-avatar
        color="indigo"
        :size="`${size || 36}`"
    >
      <v-icon dark>
        account_circle
      </v-icon>
    </v-avatar>
    <span class="pl-2">{{ userName }}</span>
  </div>
</template>

<script>
export default {
  name: "UserLink",
  props: ['user', 'size'],
  computed: {
    userName() {
      return this.user ? this.user.name : 'unknown'
    }
  },
}
</script>

<style scoped>

</style>