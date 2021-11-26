import Vue from "vue";
import App from "./App.vue";
import router from "./router/router";
import { store } from "./store";
import vuetify from "./plugins/vuetify";
import "vuetify/dist/vuetify.min.css";
import VuetifyDialogPlugin from "vuetify-dialog";
import "vuetify-dialog/dist/vuetify-dialog.css";

// 사용자 정의 페이지 관련 함수
import page from "@/plugins/page";
// 사용자 정의 dialog 관련 함수
import dialog from "@/plugins/dialog";
// 버튼 전역 컴포넌트
import Button from "@/components/common/Button";

// 사용자 정의 페이지 함수 사용설정
Vue.use(page);
// 사용자 정의 dialog 함수 사용설정
Vue.use(dialog);
Vue.config.productionTip = false;
//Vuetify-dialog 사용 설정
Vue.use(VuetifyDialogPlugin, {
  context: {
    vuetify,
  },
});

// 버튼 전역 컴포넌트 선언
Vue.component("Button", Button);

import { getAxios } from "./config/axios/axiosConfig";
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
  render: (h) => h(App),
}).$mount("#app");
