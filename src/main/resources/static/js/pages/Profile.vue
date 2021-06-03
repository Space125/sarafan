<template>
    <v-container fill-height fluid>
      <v-row align="center" justify="center">
        <v-col md="4">
          <v-row class="title">User profile</v-row>
          <v-row>
            <v-col cols="6">
              <v-img :src="profile.userpic"></v-img>
            </v-col>
            <v-row class="px-2 my-2">
              <v-col cols="6">
                <v-row>{{ profile.name }}</v-row>
                <v-row>{{ profile.locale }}</v-row>
                <v-row>{{ profile.gender }}</v-row>
                <v-row>{{ profile.email }}</v-row>
                <v-row>{{ profile.lastVisit }}</v-row>
              </v-col>
            </v-row>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
</template>

<script>
import {mapState} from "vuex";

export default {
  name: "Profile",
  computed: mapState(['profile'])
}
</script>

<style scoped>
img {
  width: auto;
  height: auto;
}
</style>