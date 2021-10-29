<template>
  <v-container>
    <v-row class="text-center">
      <v-col cols="12">
        <Carousel />
      </v-col>
    </v-row>

    <v-row class="text-center">
      <v-col md="2" sm="6">
        <v-card outlined title style="height: 300px" color="#1F7087"> </v-card>
      </v-col>
      <v-col md="2" sm="6">
        <v-card outlined title style="height: 300px" color="#1F7087"> </v-card>
      </v-col>
      <v-col md="2" sm="6">
        <v-card outlined title style="height: 300px" color="#1F7087"> </v-card>
      </v-col>
      <v-col md="2" sm="6">
        <v-card outlined title style="height: 300px" color="#1F7087"> </v-card>
      </v-col>
      <v-col md="2" sm="6">
        <v-card outlined title style="height: 300px" color="#1F7087"> </v-card>
      </v-col>
      <v-col md="2" sm="6">
        <v-card outlined title style="height: 300px" color="#1F7087"> </v-card>
      </v-col>
    </v-row>

    <v-row class="text-center">
      <v-col md="5" sm="6">
        <v-card outlined title style="height: 300px" color="#1F7087"> </v-card>
      </v-col>
      <v-col md="4" sm="6">
        <v-card outlined title style="height: 300px" color="#1F7087"> </v-card>
      </v-col>
      <v-col md="3" sm="6">
        <v-card outlined title style="height: 300px" color="#1F7087"> </v-card>
      </v-col>
    </v-row>

    <v-row>
      <v-col md="8" sm="6">
        <v-card outlined title style="height: 300px" color="#1F7087"> </v-card>
      </v-col>
      <v-col md="4" sm="6">
        <v-card outlined title style="height: 300px" color="#1F7087"> </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Carousel from "@/components/common/Carousel";

export default {
  name: "HelloWorld",
  components: {
    Carousel,
  },
  data: () => ({}),
};
</script>
