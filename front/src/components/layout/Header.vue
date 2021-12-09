<template>
  <v-card>
    <v-toolbar color="cyan" dark flat>
      <v-app-bar-nav-icon></v-app-bar-nav-icon>

      <v-toolbar-title>Your Dashboard</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn
        color="primary"
        outlined
        @click="goToLoginPage"
        v-if="!this.$store.getters.loggedIn"
      >
        Sign in
      </v-btn>

      <v-btn
        color="primary"
        outlined
        @click="logoutBtn"
        v-else-if="this.$store.getters.loggedIn"
      >
        Logout
      </v-btn>

      <!--      <v-btn icon>-->
      <!--        <v-icon>mdi-dots-vertical</v-icon>-->
      <!--      </v-btn>-->

      <template v-slot:extension>
        <v-tabs v-model="tab" align-with-title>
          <v-tabs-slider color="yellow"></v-tabs-slider>

          <v-tab v-for="item in items" :key="item" @click="movePages(item)">
            {{ item }}
          </v-tab>
        </v-tabs>
      </template>
    </v-toolbar>

    <!--    <v-tabs-items v-model="tab">-->
    <!--      <v-tab-item v-for="item in items" :key="item">-->
    <!--        <v-card flat>-->
    <!--          <v-card-text v-text="text"></v-card-text>-->
    <!--        </v-card>-->
    <!--      </v-tab-item>-->
    <!--    </v-tabs-items>-->
  </v-card>
</template>

<script>
export default {
  data() {
    return {
      tab: null,
      items: ["web", "board", "videos", "images", "news"],
      text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
    };
  },
  methods: {
    goToLoginPage() {
      this.$router.push("/login");
    },
    logoutBtn() {
      this.$store.dispatch("logout");
    },
    movePages(item) {
      console.log("i t e m > > > > > > > >");
      console.log(item);
      let menu = item;

      switch (menu) {
        case "board":
          this.$router.push("/list");
      }
    },
  },
};
</script>

<style scoped></style>
