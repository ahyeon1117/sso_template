import "./assets/reset.css";
import "./assets/main.css";

import { createApp } from "vue";
import App from "./App.vue";
import { router } from "./router/index.js";
import axios from "axios";

const app = createApp(App);

app
  .use(router)
  .provide("$axios", axios)
  .provide("$router", router)
  .mount("#app"); // 라우터 사용
