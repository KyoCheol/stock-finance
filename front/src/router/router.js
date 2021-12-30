import Vue from "vue";
import VueRouter from "vue-router";

// import VueCookies from "vue-cookies";

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

function freePass(uri) {
  return uri === "/" || uri === "/login";
}

// 네비게잉션 가드(뷰 라우터로 url 접근에 대해서처리 할 수 있음)
router.beforeEach(async (to, from, next) => {
  /*
   * to: 이동할 url 정보가 담긴 라우터 객체
   * from: 현재 url 정보가 담긴 라우터 객체
   * next: to에서 지정한 url로 이동하기 위해 꼭 호출해야하는 함수
   * next()가 호출되기 전까지 화면 전환되지않음
   * */
  console.log("vue cookies > > > > > > > > ");
  console.log(next);
  console.log("routing : ", from.path, "->", to.path);

  if (freePass(to.path)) {
    next();
  }
});
