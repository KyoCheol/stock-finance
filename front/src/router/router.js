import Vue from "vue";
import VueRouter from "vue-router";

import homeScreen from "@/components/screen/homeScreen";

import loginScreen from "@/components/screen/login/loginScreen";
import loginPage from "@/page/loginPage";

import registerScreen from "@/components/screen/login/registerScreen";

import boardListScreen from "@/components/screen/board/BoardListScreen";
import boardListView from "@/components/board/BoardList";

import boardWriteScreen from "@/components/screen/board/BoardWriteScreen";
import boardWriteView from "@/components/board/BoardWrite";

import boardDetailScreen from "@/components/screen/board/BoardDetailScreen";
import boardDetailView from "@/components/board/BoardDetail";

import boardEditScreen from "@/components/screen/board/BoardEditScreen";
import boardEditView from "@/components/board/BoardEdit";

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
  {
    path: "/list",
    component: boardListScreen,
    children: [{ path: "/list", name: "boardList", component: boardListView }],
  },
  {
    path: "/write",
    component: boardWriteScreen,
    children: [
      { path: "/write", name: "boardWrite", component: boardWriteView },
    ],
  },
  {
    path: "/detail",
    component: boardDetailScreen,
    children: [
      { path: "/detail", name: "boardDetail", component: boardDetailView },
    ],
  },
  {
    path: "/edit",
    component: boardEditScreen,
    children: [{ path: "/edit", name: "boardEdit", component: boardEditView }],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
