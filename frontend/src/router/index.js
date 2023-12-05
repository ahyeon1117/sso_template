import { createRouter, createWebHistory } from "vue-router";

import Login from "../views/Login.vue";

// 라우터 설계
const routes = [{ path: "/", component: Login }];

// 라우터 생성
const router = createRouter({
  history: createWebHistory(),
  routes
});

// 라우터 추출 (main.js에서 import)
export { router };
