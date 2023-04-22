<template>
  <form @submit.prevent="onsubmit">
    <va-input
      v-model="username"
      class="mb-3"
      type="email"
      :label="t('auth.username')"
      :error="!!usernameErrors.length"
      :error-messages="usernameErrors"
    />

    <va-input
      v-model="password"
      class="mb-3"
      type="password"
      :label="t('auth.password')"
      :error="!!passwordErrors.length"
      :error-messages="passwordErrors"
    />

    <div class="auth-layout__options d-flex align-center justify-space-between">
      <va-checkbox v-model="keepLoggedIn" class="mb-0" :label="t('auth.keep_logged_in')" />
      <router-link class="ml-1 va-link" :to="{ name: 'recover-password' }">{{
          t("auth.recover_password")
        }}
      </router-link>
    </div>

    <div class="d-flex justify-center mt-3">
      <va-button class="my-0" @click="onsubmit">{{ t("auth.login") }}</va-button>
    </div>
  </form>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useI18n } from "vue-i18n";
// import { login } from '@/services/api/login'
import { useGlobalStore } from "@/stores/global-store";
import { login } from "@/services/api/login";

const { t } = useI18n();

const username = ref("");
const password = ref("");
const keepLoggedIn = ref(false);
const usernameErrors = ref<string[]>([]);
const passwordErrors = ref<string[]>([]);
const router = useRouter();
const route = useRoute();

// 载入登录页面时，将token置空
const GlobalStore = useGlobalStore();
GlobalStore.changeToken("");

async function onsubmit() {
  // login()
  usernameErrors.value = username.value ? [] : ["Username is required"];
  passwordErrors.value = password.value ? [] : ["Password is required"];

  // 如果用户名和密码都不为空，就执行登录
  if (usernameErrors.value.length || passwordErrors.value.length) return;

  const response = await login(username.value, password.value);

  const GlobalStore = useGlobalStore();
  GlobalStore.changeToken(response.data);

  // 如果query.path存在，就跳转到query.path，否则跳转到dashboard
  const { path } = route.query;
  if (path && typeof path === "string") {
    await router.push({ path });
  } else {
    await router.push({ name: "dashboard" });
  }
}
</script>
