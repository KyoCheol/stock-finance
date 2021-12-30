import router from "../../router/router";
import axios from "axios";
import COMMON_CONST from "../../config/common/commonConst";
import VueCookies from "vue-cookies";

//import { getAxios as axios } from "../../config/axios/axiosConfig";

const state = {
  token: null,
  user: null,
  accessToken: null,
  refreshToken: null,
};

const getters = {
  token: (state) => state.token,
  user: (state) => state.user,
  loggedIn(state) {
    return !!state.user;
  },
  getToken() {
    let ac = VueCookies.get("accessToken");
    let rf = VueCookies.get("refreshToken");
    return {
      access: ac,
      refresh: rf,
    };
  },
};

const mutations = {
  login(state, item) {
    VueCookies.set("accessToken", item.headers["accesstoken"], "60s");
    VueCookies.set("refreshToken", item.headers["refreshtoken"], "1h");
    state.accessToken = item.headers["accesstoken"];
    state.refreshToken = item.headers["refreshtoken"];

    //////////////////////////////////////////////////////////////////////

    state.token = item.headers["accesstoken"];
    state.user = item;
    localStorage.setItem("user", JSON.stringify(item));
    axios.defaults.headers.common["Authorization"] =
      item.headers["accesstoken"];
    setTokenInLocalStorage(item);
    setAccessTokenInHeader(item.headers["accesstoken"]);
  },
  refreshToken(state, item) {
    VueCookies.set("accessToken", item.headers["accesstoken"], "60s");
    VueCookies.set("refreshToken", item.headers["refreshtoken"], "1h");
    state.accessToken = item.headers["accesstoken"];
  },
  logout(state) {
    deleteTokenInLocalStorage();
    deleteAccessTokenInHeader();
    state.token = null;
    state.user = null;
  },
};

const actions = {
  login({ commit }, { id, password }) {
    const params = {
      email: id,
      password: password,
    };

    let apiUrl = COMMON_CONST.API_URL + "/api/auth/signin";

    axios
      .post(apiUrl, JSON.stringify(params), {
        headers: { "content-type": "application/json" },
      })
      .then((res) => {
        alert("정보가 확인되었습니다.\n환영합니다!");
        commit("login", res);
        router.push("/");
      })
      .catch((e) => {
        console.log(e);
        alert("로그인 요청에 문제가 발생했습니다.");
      });
  },
  refreshtoken({ commit }) {
    let url = "";

    return new Promise((resolve, reject) => {
      axios
        .post(url)
        .then((res) => {
          commit("refreshToken", res.data);
          resolve(res.data);
        })
        .catch((err) => {
          reject(err.config.data);
        });
    });
  },
  logout({ commit }) {
    commit("logout");
    //router.push("/");
  },
};

const setTokenInLocalStorage = (tokenInfo) => {
  localStorage.setItem("access_token", tokenInfo.headers["accesstoken"]);
  localStorage.setItem("refresh_token", tokenInfo.headers["refreshtoken"]);
};

const deleteTokenInLocalStorage = () => {
  localStorage.removeItem("access_token");
  localStorage.removeItem("refresh_token");
};

const setAccessTokenInHeader = (accessToken) => {
  axios.defaults.headers.common["Authorization"] = accessToken;
};

const deleteAccessTokenInHeader = () => {
  axios.defaults.headers.common["Authorization"] = null;
};

export default {
  state,
  getters,
  mutations,
  actions,
};
