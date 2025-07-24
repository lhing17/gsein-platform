import { defineStore } from "pinia";
import { RouteLocationNormalized } from "vue-router";

export interface TabItem {
  name: string;
  title: string;
  path: string;
  fullPath: string;
  icon?: string;
  closable: boolean;
}

export const useTabsStore = defineStore("tabs", {
  state: () => {
    return {
      tabs: [{
        name: "home",
        title: "主页",
        path: "/admin/home",
        fullPath: "/admin/home",
        icon: "home",
        closable: false
      }] as TabItem[],
      activeTab: "/admin/home"
    };
  },

  actions: {
    addTab(route: RouteLocationNormalized) {
      // 如果标签页已存在，则激活它
      const isExist = this.tabs.some(tab => tab.path === route.path);
      if (isExist) {
        this.activeTab = route.path;
        return;
      }

      // 获取路由的标题
      const title = route.meta.title as string || route.name as string;

      // 添加新标签页
      const newTab: TabItem = {
        name: route.name as string,
        title: title,
        path: route.path,
        fullPath: route.fullPath,
        icon: route.meta.icon as string,
        closable: (route.name as string) !== 'home'
      };

      this.tabs.push(newTab);
      this.activeTab = route.path;
    },

    removeTab(path: string) {
      const index = this.tabs.findIndex(tab => tab.path === path);
      if (index === -1) return;

      // 如果关闭的是当前激活的标签页，则激活前一个标签页
      if (this.activeTab === path) {
        const nextTab = this.tabs[index - 1] || this.tabs[index + 1];
        if (nextTab) {
          this.activeTab = nextTab.path;
        }
      }

      this.tabs.splice(index, 1);
    },

    removeOtherTabs(path: string) {
      this.tabs = this.tabs.filter(tab => tab.path === path || !tab.closable);
      this.activeTab = path;
    },

    removeAllTabs() {
      this.tabs = this.tabs.filter(tab => !tab.closable);
      if (this.tabs.length > 0) {
        this.activeTab = this.tabs[0].path;
      } else {
        this.activeTab = "";
      }
    },

    updateTabTitle(path: string, title: string) {
      const tab = this.tabs.find(tab => tab.path === path);
      if (tab) {
        tab.title = title;
      }
    }
  },
  persist: {
    enabled: true,
    strategies: [
      {
        storage: localStorage,
        paths: ["tabs", "activeTab"]
      }
    ]
  }
});