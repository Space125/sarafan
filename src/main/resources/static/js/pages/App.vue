<template>
  <v-app>
    <v-app-bar app>
      <v-toolbar-title>Sarafan</v-toolbar-title>
      <v-spacer></v-spacer>
      <span v-if="profile">{{ profile.name }}</span>
      <v-btn v-if="profile" icon href="/logout">
        <v-icon>exit_to_app</v-icon>
      </v-btn>
    </v-app-bar>
    <v-main>
      <v-container v-if="!profile">Необходимо авторизоваться через <a href="/login">Google</a></v-container>
      <v-container v-else>
        <messages-list/>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import MessagesList from 'components/messages/MessageList.vue'
import { addHandler } from 'util/ws'
import { mapState, mapMutations } from "vuex";

export default {
  components: {
    MessagesList
  },
  computed: mapState(['profile']),
  methods: mapMutations(['addMessageMutation', 'updateMessageMutation', 'removeMessageMutation']),
  created() {
    addHandler(data => {
      if (data.objectType === 'MESSAGE') {

        switch (data.eventType) {
          case 'CREATE':
            this.addMessageMutation(data.body)
            break
          case 'UPDATE':
            this.updateMessageMutation(data.body)
            break
          case 'REMOVE':
            this.removeMessageMutation(data.body)
            break
          default:
            console.log(`Looks like the event type if unknown "${data.eventType}"`)
        }

      } else {
        console.log(`Looks like the object type if unknown "${data.objectType}"`)
      }
    })
  }
}
</script>

<style>

</style>