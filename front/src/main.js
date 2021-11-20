import Vue from "vue";
import App from "./App.vue";
import router from "./router/router";
import { store } from "./store";
import "vuetify/dist/vuetify.min.css";

Vue.config.productionTip = false;

import { getAxios } from "./config/axios/axiosConfig";
import vuetify from "./plugins/vuetify";
Vue.prototype.$http = getAxios;

//Vue.use(vuetify, {
//  theme: {
//    primary: "#7957d5",
//  },
//});

new Vue({
  router,
  store,
  vuetify,

  //  vuetify,
  render: (h) => h(App),
}).$mount("#app");
