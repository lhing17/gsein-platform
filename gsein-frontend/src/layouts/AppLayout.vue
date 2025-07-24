<template>
  <div class="app-layout">
    <navbar />
    <div class="app-layout__content">
      <div class="app-layout__sidebar-wrapper" :class="{ minimized: isSidebarMinimized }">
        <div v-if="isFullScreenSidebar" class="d-flex justify-end">
          <va-button class="px-4 py-4" icon="md_close" preset="plain" color="dark" @click="onCloseSidebarButtonClick" />
        </div>
        <sidebar
          :width="sidebarWidth"
          :minimized="isSidebarMinimized"
          :minimized-width="sidebarMinimizedWidth"
          :animated="!isMobile"
        />
      </div>
      <div class="app-layout__page">
        <div class="app-layout__tabs" v-if="tabs.length > 0">
          <va-tabs v-model="activeTabPath" ref="tabsRef">
            <template #tabs>
              <va-tab 
                v-for="tab in tabs" 
                :key="tab.path" 
                :name="tab.path"
                class="app-tab"
                @contextmenu.prevent="onTabContextMenu($event, tab.path)"
              >
                <div class="app-tab__content">
                  <va-icon v-if="tab.icon" :name="tab.icon" class="app-tab__icon mr-2" />
                  <span>{{ tab.title }}</span>
                  <va-icon 
                    v-if="tab.closable" 
                    name="close" 
                    class="app-tab__close ml-2" 
                    @click.stop="closeTab(tab.path)" 
                  />
                </div>
              </va-tab>
            </template>
          </va-tabs>
          <tab-context-menu
            v-model:visible="contextMenuVisible"
            :top="contextMenuTop"
            :left="contextMenuLeft"
            :tab-path="contextMenuTabPath"
            @close="closeTab"
            @close-others="closeOtherTabs"
            @close-all="closeAllTabs"
          />
        </div>
        <div class="layout fluid va-gutter-5">
          <Suspense>
            <router-view />
            <template #fallback>
              加载中...
            </template>
          </Suspense>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, nextTick, onBeforeUnmount, onMounted, ref, watch } from "vue";
import { storeToRefs } from "pinia";
import { onBeforeRouteUpdate, useRoute, useRouter } from "vue-router";

import { useGlobalStore } from "../stores/global-store";
import { useTabsStore } from "../stores/tabs-store";

import Navbar from "../components/navbar/Navbar.vue";
import Sidebar from "../components/sidebar/Sidebar.vue";
import TabContextMenu from "../components/tabs/TabContextMenu.vue";

const GlobalStore = useGlobalStore();
const TabsStore = useTabsStore();
const route = useRoute();
const router = useRouter();

const mobileBreakPointPX = 640;
const tabletBreakPointPX = 768;

const sidebarWidth = ref("16rem");
const sidebarMinimizedWidth = ref(undefined);

const isMobile = ref(false);
const isTablet = ref(false);
const { isSidebarMinimized } = storeToRefs(GlobalStore);
const { tabs, activeTab } = storeToRefs(TabsStore);
const checkIsTablet = () => window.innerWidth <= tabletBreakPointPX;
const checkIsMobile = () => window.innerWidth <= mobileBreakPointPX;

const tabsRef = ref(null);

// 计算属性：当前激活的标签页路径
const activeTabPath = computed({
  get: () => activeTab.value,
  set: (path) => {
    activeTab.value = path;
    // 找到对应的标签页并导航到它
    const tab = tabs.value.find(tab => tab.path === path);
    if (tab) {
      router.push(tab.fullPath);
    }
  }
});

// 激活标签的函数，确保在DOM更新后正确激活标签
const activateTab = (path) => {
  if (!path || !tabsRef.value || !tabsRef.value.$el) return;
  
  // 使用nextTick确保DOM已更新
  nextTick(() => {
    // 使用更通用的选择器方法找到对应的标签元素
    const tabElements = tabsRef.value.$el.querySelectorAll('.va-tab');
    const tabIndex = tabs.value.findIndex(tab => tab.path === path);
    
    if (tabIndex >= 0 && tabElements[tabIndex]) {
      // 模拟点击事件以激活标签
      tabElements[tabIndex].click();
    }
  });
};

// 监听activeTab变化，手动激活对应的标签
watch(() => activeTab.value, (newPath) => {
  activateTab(newPath);
});

// 监听tabs数组变化，确保在关闭标签后正确激活当前标签
watch(() => tabs.value.length, () => {
  activateTab(activeTab.value);
});

// 右键菜单相关状态
const contextMenuVisible = ref(false);
const contextMenuTop = ref(0);
const contextMenuLeft = ref(0);
const contextMenuTabPath = ref('');

// 处理标签页右键菜单
const onTabContextMenu = (event, path) => {
  event.preventDefault();
  contextMenuVisible.value = true;
  contextMenuTop.value = event.clientY;
  contextMenuLeft.value = event.clientX;
  contextMenuTabPath.value = path;
};

// 关闭标签页
const closeTab = (path) => {
  TabsStore.removeTab(path);
};

// 关闭其他标签页
const closeOtherTabs = (path) => {
  TabsStore.removeOtherTabs(path);
};

// 关闭所有标签页
const closeAllTabs = () => {
  TabsStore.removeAllTabs();
};

const onResize = () => {
  isSidebarMinimized.value = checkIsTablet();

  isMobile.value = checkIsMobile();
  isTablet.value = checkIsTablet();
  sidebarMinimizedWidth.value = isMobile.value ? "0" : "4.5rem";
  sidebarWidth.value = isTablet.value ? "100%" : "16rem";
};

onMounted(() => {
  window.addEventListener("resize", onResize);
  
  // 初始化时添加当前路由到标签页
  if (route.name) {
    TabsStore.addTab(route);
  } else {
    // 如果没有路由，默认导航到主页
    router.push('/admin/home');
  }
});

onBeforeUnmount(() => {
  window.removeEventListener("resize", onResize);
});

onBeforeRouteUpdate((to) => {
  if (checkIsTablet()) {
    // Collapse sidebar after route change for Mobile
    isSidebarMinimized.value = true;
  }
  
  // 路由更新时添加到标签页
  if (to.name) {
    TabsStore.addTab(to);
  }
});

onResize();

const isFullScreenSidebar = computed(() => isTablet.value && !isSidebarMinimized.value);

const onCloseSidebarButtonClick = () => {
  isSidebarMinimized.value = true;
};
</script>

<style lang="scss">
$mobileBreakPointPX: 640px;
$tabletBreakPointPX: 768px;

.app-layout {
  height: 100vh;
  display: flex;
  flex-direction: column;

  &__navbar {
    min-height: 4rem;
  }

  &__content {
    display: flex;
    height: calc(100vh - 4rem);
    flex: 1;

    @media screen and (max-width: $tabletBreakPointPX) {
      height: calc(100vh - 6.5rem);
    }

    .app-layout__sidebar-wrapper {
      position: relative;
      height: 100%;
      background: #ffffff;

      @media screen and (max-width: $tabletBreakPointPX) {
        &:not(.minimized) {
          width: 100%;
          height: 100%;
          position: fixed;
          top: 0;
          z-index: 999;
        }

        .va-sidebar:not(.va-sidebar--minimized) {
          .va-sidebar__menu {
            padding: 0;
          }
        }
      }
    }

    .app-layout__page {
      .layout {
        height: calc(100% - 41px);
      }
    }
  }

  &__page {
    flex-grow: 2;
    overflow-y: scroll;
    display: flex;
    flex-direction: column;
  }

  &__tabs {
    background-color: #ffffff;
    border-bottom: 1px solid rgba(0, 0, 0, 0.12);
    padding: 0 1rem;

    .va-tabs {
      min-height: 40px;
    }

    .va-tabs__wrapper {
      min-height: 40px;
      .va-tabs__container {
        margin-top: 4px;
      }
    }
  }
}

.app-tab {
  &__content {
    display: flex;
    align-items: center;
    padding: 0 0.25rem;
  }

  &__icon {
    font-size: 1rem;
  }

  &__close {
    font-size: 1rem;
    opacity: 0.5;
    transition: opacity 0.2s;

    &:hover {
      opacity: 1;
      cursor: pointer;
    }
  }
}
</style>
