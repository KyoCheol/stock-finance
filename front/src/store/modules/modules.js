import router from "../../router/router";
import axios from "axios";
import COMMON_CONST from "../../config/common/commonConst";

//import { getAxios as axios } from "../../config/axios/axiosConfig";

const state = {
  token: null,
  user: null,
  // id: null,
  // name: null,
  // role: null,
  // email: null,
  // nickname: null,
};

const getters = {
  token: (state) => state.token,
  user: (state) => state.user,
  // id: (state) => state.id,
  // email: (state) => state.email,
  // nickname: (state) => state.nickname,
  // role: (state) => state.role,
  loggedIn(state) {
    return !!state.user;
  },
};

const mutations = {
  login(state, item) {
    state.token = item.headers["accesstoken"];
    // state.id = item.data["id"];
    // state.role = item.data["role"];
    // state.email = item.data["email"];
    // state.nickname = item.data["nickname"];
    state.user = item;
    localStorage.setItem("user", JSON.stringify(item));
    axios.defaults.headers.common["Authorization"] =
      item.headers["accesstoken"];
    setTokenInLocalStorage(item);
    setAccessTokenInHeader(item.headers["accesstoken"]);
  },
  logout(state) {
    // state.id = null;
    // state.role = null;
    // state.email = null;
    // state.nickname = null;
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
  logout({ commit }) {
    commit("logout");
    //router.push("/");
  },
};

const setTokenInLocalStorage = (tokenInfo) => {
  localStorage.setItem("access_token", tokenInfo.access_token);
  localStorage.setItem("refresh_token", tokenInfo.refresh_token);
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
