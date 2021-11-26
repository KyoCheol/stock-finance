// import store from "./legoStore";
//
// export default store;

import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

import modules from "./modules/modules";
import snackModules from "./modules/snackbarModules";

Vue.use(Vuex);

const persistedState = createPersistedState({
  paths: ["token", "id", "name", "role", "nickname"],
});

export const store = new Vuex.Store({
  state: modules.state,
  getters: modules.getters,
  mutations: modules.mutations,
  actions: modules.actions,
  plugins: [persistedState],
  modules: {
    snackStore: snackModules,
  },
});
