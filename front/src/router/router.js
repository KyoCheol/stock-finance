import Vue from "vue";
import VueRouter from "vue-router";
import loginPage from "@/page/loginPage";
import homeScreen from "@/components/screen/homeScreen";
import loginScreen from "@/components/screen/loginScreen";
import registerScreen from "@/components/screen/registerScreen";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    component: homeScreen,
  },
  {
    path: "/login",
    component: loginScreen,
    redirect: "/login",
    children: [{ path: "/login", name: "login", component: loginPage }],
  },
  {
    path: "/register",
    component: registerScreen,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
