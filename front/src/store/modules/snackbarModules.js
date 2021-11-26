// 스낵바 설정
const SET_SNACKBAR = "SET_SNACKBAR";

const snackStore = {
  namespaced: true,
  state: {
    sb: {
      show: false,
      msg: "",
      color: "",
    },
  },
  mutations: {
    [SET_SNACKBAR](state, sb) {
      state.sb = sb;
    },
  },
  action: {},
  modules: {},
};

export default snackStore;
