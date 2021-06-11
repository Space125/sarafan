<template>
  <v-container fill-height fluid>
    <v-row align="center" justify="center">
      <v-col md="4">
        <v-row class="title">User profile</v-row>
        <v-row class="pb-3">
          <v-col cols="6">
            <v-img :src="profile.userpic"></v-img>
          </v-col>
          <v-row class="px-2 my-2">
            <v-col cols="6">
              <v-row>{{ profile.name }}</v-row>
              <v-row>{{ profile.locale }}</v-row>
              <v-row>{{ profile.gender }}</v-row>
              <v-row>{{ profile.lastVisit }}</v-row>
              <v-row>{{ profile.subscriptions && profile.subscriptions.length }} subscriptions</v-row>
              <v-row>{{ profile.subscribers && profile.subscribers.length }} subscribers</v-row>
            </v-col>
          </v-row>
        </v-row>
        <v-btn
            v-if="!isMyProfile"
            @click="changeSubscription"
        >
          {{ isISubscribed ? "Unsubscribe" : "Subscribe" }}
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import profileApi from 'api/profile'

export default {
  name: "Profile",
  data() {
    return {
      profile: {}
    }
  },
  computed: {
    isMyProfile() {
      return !this.$route.params.id ||
          this.$route.params.id === this.$store.state.profile.id
    },
    isISubscribed() {
      return this.profile.subscribers &&
          this.profile.subscribers.find( subscription => {
            return subscription.id === this.$store.state.profile.id
          })
    }
  },
  watch: {
    '$route'() {
      this.updateProfile()
      console.log('watch')
    }
  },
  methods: {
    async changeSubscription() {
      const data = await profileApi.changeSubscription(this.profile.id)

      this.profile = await data.json()

    },
    async updateProfile() {
      const id = this.$route.params.id || this.$store.state.profile.id

      const data = await profileApi.get(id)
      console.log(data.json())
      this.profile = await data.json()

      this.$forceUpdate()
    }
  },
  beforeMount() {
    this.updateProfile()
  }
}
</script>

<style scoped>
img {
  width: auto;
  height: auto;
}
</style>