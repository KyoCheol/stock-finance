<template>
  <v-carousel cycle height="400" hide-delimiter-background show-arrows-on-hover>
    <v-carousel-item v-for="(slide, i) in slides" :key="i">
      <v-sheet :color="colors[i]" height="100%">
        <v-row class="fill-height" align="center" justify="center">
          <div class="text-h2">{{ slide }} Slide</div>
        </v-row>
      </v-sheet>
    </v-carousel-item>
  </v-carousel>
</template>

<script>
export default {
  data() {
    return {
      colors: [
        "indigo",
        "warning",
        "pink darken-2",
        "red lighten-1",
        "deep-purple accent-4",
      ],
      slides: ["First", "Second", "Third", "Fourth", "Fifth"],
    };
  },
};
</script>

<style scoped>
.v-window__next {
  background: rgba(0, 0, 0, 0.15);
  height: 68px;
  width: 68px;
  top: calc(50% - 33px);
  margin-right: 13%;
}
.v-window__prev {
  background: rgba(0, 0, 0, 0.15);
  height: 68px;
  width: 68px;
  top: calc(50% - 33px);
  margin-left: 13%;
}
</style>
