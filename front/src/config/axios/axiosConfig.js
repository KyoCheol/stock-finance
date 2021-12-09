import axios from "axios";

export { getAxios };

let getAxios = createInstance();

function createInstance() {
  const getAxios = axios.create();
  getAxios.defaults.baseURL = "http://localhost:8080";
  getAxios.defaults.headers["Access-Control-Allow-Origin"] = "*";
  getAxios.defaults.headers["Access-Control-Allow-Methods"] =
    "GET,PUT,POST,DELETE,OPTIONS";
  getAxios.defaults.headers["Access-Control-Allow-headers"] =
    "Content-Type, Authorization, Content-Length, X-Requested-With";
  getAxios.defaults.headers["Content-Type"] = "application/json";
  return getAxios;
}
