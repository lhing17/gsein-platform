import axios from "axios";
import router from "@/router";

import { useGlobalStore } from "@/stores/global-store";
import { useToast } from "vuestic-ui";


const instance = axios.create({
  timeout: 5000
});

instance.interceptors.request.use(
  (config) => {
    // 在每个请求头中加入token，如果有的话，token存储在全局store中

    const GlobalStore = useGlobalStore();
    const token = GlobalStore.token;
    if (token && config.headers) {
      config.headers.Authorization = token;
    }

    return config;
  }
);

instance.interceptors.response.use(
  (response) => {
    // console.log(response)
    if (response.data ) {
      if (response.data.code === 500) {
        const { init } = useToast();
        init({
          message: response.data.message,
        })
        return Promise.reject(response.data);
      }
      if (response.data.code.toString().startsWith("401")) {
        // 返回 401开头的状态码 清除token信息并跳转到登录页面
        localStorage.removeItem("token");

        const GlobalStore = useGlobalStore();
        GlobalStore.changeToken("");
        router.push({ path: "/auth/login" });
      }
    }
    return response;
  },
  (error) => {
    if (error.response) {
      switch (error.response.status) {
        case 401:
          // 返回 401 清除token信息并跳转到登录页面
          localStorage.removeItem("token");

          const GlobalStore = useGlobalStore();
          GlobalStore.changeToken("");
          router.push({ path: "/auth/login" });
      }
    }
    return Promise.reject(error.response.data);
  });

export default instance;
