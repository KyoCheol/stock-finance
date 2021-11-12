<template>
  <v-container> </v-container>
</template>

<script>
export default {
  name: "news",
  components: {},
  data: () => ({
    url: {
      newsList: "/api/v1/finance/stock/getNewsList",
    },
  }),
  created() {
    this.init();
  },
  methods: {
    init() {
      this.getNewsList();
    },
    getNewsList() {
      // let url = this.url.newsList;
      // this.$http
      //   .get(url)
      //   .then((res) => {
      //     console.log(res.data);
      //   })
      //   .catch((err) => {
      //     console.log(err.data);
      //   });
    },
  },
};
</script>
