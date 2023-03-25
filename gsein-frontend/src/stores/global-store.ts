import { defineStore } from "pinia";

// @ts-ignore
export const useGlobalStore = defineStore("global", {
  state: () => {
    return {
      isSidebarMinimized: false,
      userName: "Gsein",
      token: ""
    };
  },

  actions: {
    toggleSidebar() {
      this.isSidebarMinimized = !this.isSidebarMinimized;
    },

    changeUserName(userName: string) {
      this.userName = userName;
    },

    changeToken(token: string) {
      this.token = token;
    }
  },
  persist: {
    enabled: true,
    strategies: [
      {
        storage: localStorage,
        paths: ["token"],
        key: "token"
      }
    ]
  }


});
