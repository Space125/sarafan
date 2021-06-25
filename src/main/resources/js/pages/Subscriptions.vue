<template>
  <v-container>
    <v-row justify="center">
      <v-col>
        <v-list>
          <v-list-item
              v-for="item in subscriptions"
              :key="item.subscriber.id"
          >
            <user-link
                :user="item.subscriber"
                :size="24"
            ></user-link>
            <v-btn
                @click="changeSubscriptionStatus(item.subscriber.id)"
            >
              {{item.active ? "Dismiss" : "Approve"}}
            </v-btn>
          </v-list-item>
        </v-list>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import profileApi from 'api/profile'
import UserLink from 'components/UserLink.vue'

export default {
  name: "Subscriptions",
  components: {UserLink},
  data() {
    return {
      subscriptions: []
    }
  },
  methods: {
    async changeSubscriptionStatus(subscriberId) {
      await profileApi.changeSubscriptionStatus(subscriberId)

      const index = this.subscriptions.findIndex(item => item.subscriber.id === subscriberId)
      const subscription = this.subscriptions[index]

      this.subscriptions = [
          ...this.subscriptions.splice(0, index),
        {
          ...subscription,
          active: !subscription.active
        },
        ...this.subscriptions.slice(index + 1)
      ]
    }
  },
  async beforeMount() {
    const response = await profileApi.subscriberList(this.$store.state.profile.id)
    this.subscriptions = await response.json()
  }
}
</script>

<style scoped>

</style>