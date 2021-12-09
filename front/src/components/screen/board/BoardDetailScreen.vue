<template>
  <v-app id="app">
    <!-- 추가된 헤더 컴포넌트 사용 -->
    <Header />

    <Content />
    <!-- 추가된 풋터 컴포넌트 사용 -->
    <Footer />
  </v-app>
</template>

<script>
import Header from "@/components/layout/Header";
import Footer from "@/components/layout/Footer";
import Content from "@/components/board/BoardDetail";

export default {
  name: "App",
  components: {
    Header,
    Footer,
    Content,
  },
  data: () => ({
    //
  }),
};
</script>
