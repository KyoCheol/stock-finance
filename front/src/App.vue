<template>
  <v-app id="app">
    <router-view />
    <!--    &lt;!&ndash; 추가된 헤더 컴포넌트 사용 &ndash;&gt;-->
    <!--    <Header />-->

    <!--    <Content />-->
    <!--    &lt;!&ndash; 추가된 풋터 컴포넌트 사용 &ndash;&gt;-->
    <!--    <Footer />-->
  </v-app>
</template>

<script>
// import Header from "@/components/layout/Header";
// import Footer from "@/components/layout/Footer";
// import Content from "@/components/HelloWorld";

export default {
  name: "App",
  components: {
    // Header,
    // Footer,
    // Content,
  },
  data: () => ({
    //
  }),
};
</script>
