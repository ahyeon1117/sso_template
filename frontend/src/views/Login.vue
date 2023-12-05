<template>
  <div class="loginBox">
    <div class="loginBox_sub">
      <div class="loginBox_logoArea">
        <h1 class="loginBox_logoArea_logo mgT20">logo</h1>
      </div>
      <div class="loginBox_inputArea">
        <button class="mgT12">
          <a href="http://localhost:1117/oauth2/authorization/kakao"
            >카카오 로그인</a
          >
        </button>
        <button class="mgT12">
          <a href="http://localhost:1117/oauth2/authorization/naver"
            >네이버 로그인</a
          >
        </button>
        <button class="mgT12">
          <a href="http://localhost:1117/oauth2/authorization/google"
            >구글 로그인</a
          >
        </button>
        <!-- <form @submit="loginUser">
          <div class="loginBox_inputArea_input-container">
            <label :class="data.formClass.id"
              >전화번호, 사용자 이름 또는 이메일</label
            >
            <input v-model="data.input.id" class="mgT4" />
          </div>
          <div class="loginBox_inputArea_input-container">
            <label :class="data.formClass.pw">비밀번호</label>
            <input v-model="data.input.pw" class="mgT4" type="password" />
          </div>
          <button class="mgT12" type="submit" :disabled="data.disable.login">
            로그인
          </button>
        </form> -->
      </div>
    </div>
  </div>
</template>
<script setup>
import { watch, reactive } from "vue";
import { inject } from "vue";
const $router = inject("$router");

const data = reactive({
  input: {
    id: "",
    pw: ""
  },
  disable: {
    login: true
  },
  formClass: {
    id: "loginBox_inputArea_label",
    pw: "loginBox_inputArea_label"
  }
});
watch(
  () => data.input,
  (newValues, prevValues) => {
    if (newValues.id.length > 0) {
      data.formClass.id = "loginBox_inputArea_smallLabel";
    } else {
      data.formClass.id = "loginBox_inputArea_label";
    }
    if (newValues.pw.length > 0) {
      data.formClass.pw = "loginBox_inputArea_smallLabel";
    } else {
      data.formClass.pw = "loginBox_inputArea_label";
    }
    if (validPw(newValues.pw) && validId(newValues.id)) {
      data.disable.login = false;
    }
  },
  { deep: true }
);
const loginUser = () => {
  $router.push("/main");
};

const validId = (id) => {
  return id.length > 0;
};
const validPw = (pw) => {
  return pw.length > 0;
};
</script>
