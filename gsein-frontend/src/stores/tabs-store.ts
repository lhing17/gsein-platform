import { defineStore } from "pinia";
import { RouteLocationNormalized } from "vue-router";
import i18n from "@/i18n";
import NavigationRoutes from "@/components/sidebar/NavigationRoutes";

export interface TabItem {
  name: string;
  title: string;
  path: string;
  fullPath: string;
  icon?: string;
  closable: boolean;
  isActive: boolean;
}

// 递归查找路由的displayName
function findDisplayName(routeName: string, routes: any[]): string | null {
  for (const route of routes) {
    if (route.name === routeName) {
      return route.displayName;
    }
    if (route.children) {
      const found = findDisplayName(routeName, route.children);
      if (found) return found;
    }
  }
  return null;
}

// 获取翻译标题的辅助函数
function getTranslatedTitle(routeName: string, metaTitle?: string): string {
  // 优先使用 meta.title
  if (metaTitle) {
    return metaTitle;
  }
  
  try {
    // 从NavigationRoutes中查找对应的displayName
    const displayName = findDisplayName(routeName, NavigationRoutes.routes);
    
    if (displayName) {
      // 使用找到的displayName进行i18n翻译
      const translatedTitle = i18n.global.t(displayName);
      // 如果翻译结果和key相同，说明没有找到翻译，使用原始名称
      if (translatedTitle === displayName) {
        return routeName;
      }
      return translatedTitle;
    } else {
      // 如果在NavigationRoutes中没有找到，尝试使用menu命名空间
      const translatedTitle = i18n.global.t(`menu.${routeName}`);
      if (translatedTitle === `menu.${routeName}`) {
        return routeName;
      }
      return translatedTitle;
    }
  } catch (error) {
    // 如果翻译失败，使用路由名称
    return routeName;
  }
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
    activateTab(tabs: TabItem[], path: string) {
      tabs.forEach(t => t.isActive = false);
      const tab = tabs.find(tab => tab.path === path);
      if (tab) {
        this.activeTab = path;
        tab.isActive = true
      }
    },
    addTab(route: RouteLocationNormalized) {
      // 如果标签页已存在，则激活它
      const isExist = this.tabs.some(tab => tab.path === route.path);
      if (isExist) {
        this.activeTab = route.path;
        return;
      }

      // 获取路由的标题
      const title = getTranslatedTitle(
        route.name as string, 
        route.meta.title as string
      );

      // 添加新标签页
      const newTab: TabItem = {
        name: route.name as string,
        title: title,
        path: route.path,
        fullPath: route.fullPath,
        icon: route.meta.icon as string,
        closable: (route.name as string) !== 'home',
        isActive: true
      };

      this.tabs.push(newTab);
      this.activateTab(this.tabs, route.path)
    },

    removeTab(path: string) {
      const index = this.tabs.findIndex(tab => tab.path === path);
      if (index === -1) return;

      // 如果关闭的是当前激活的标签页，则激活前一个标签页
      if (this.activeTab === path) {
        const nextTab = this.tabs[index - 1] || this.tabs[index + 1];
        if (nextTab) {
          this.activateTab(this.tabs, nextTab.path)
        }
      }

      this.tabs.splice(index, 1);
    },

    removeOtherTabs(path: string) {
      this.tabs = this.tabs.filter(tab => tab.path === path || !tab.closable);
      this.activateTab(this.tabs, path) 
    },

    removeAllTabs() {
      this.tabs = this.tabs.filter(tab => !tab.closable);
      if (this.tabs.length > 0) {
        this.activateTab(this.tabs, this.tabs[0].path) 
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