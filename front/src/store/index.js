// import store from "./legoStore";
//
// export default store;

import Vue from "vue";
import Vuex from "vuex";
import modules from "./modules/modules";
import createPersistedState from "vuex-persistedstate";

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
});
