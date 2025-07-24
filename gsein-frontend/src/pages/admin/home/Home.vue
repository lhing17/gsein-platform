<template>
  <div class="home-page">
    <va-card class="mb-4">
      <va-card-title>欢迎使用系统</va-card-title>
      <va-card-content>
        <p>这是系统的主页，您可以在这里查看系统概览和快速导航。</p>
      </va-card-content>
    </va-card>
    
    <div class="row">
      <div class="flex xs12 md6 xl4">
        <va-card class="mb-4">
          <va-card-title>系统状态</va-card-title>
          <va-card-content>
            <p>系统运行正常</p>
          </va-card-content>
        </va-card>
      </div>
      
      <div class="flex xs12 md6 xl4">
        <va-card class="mb-4">
          <va-card-title>快速导航</va-card-title>
          <va-card-content>
            <va-list>
              <va-list-item v-for="(item, index) in quickNavItems" :key="index">
                <va-list-item-section avatar>
                  <va-icon :name="item.icon" />
                </va-list-item-section>
                <va-list-item-section>
                  <router-link :to="item.path">{{ item.title }}</router-link>
                </va-list-item-section>
              </va-list-item>
            </va-list>
          </va-card-content>
        </va-card>
      </div>
      
      <div class="flex xs12 md6 xl4">
        <va-card class="mb-4">
          <va-card-title>最近访问</va-card-title>
          <va-card-content>
            <p>暂无最近访问记录</p>
          </va-card-content>
        </va-card>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

// 快速导航项
const quickNavItems = ref([
  { title: '仪表盘', path: '/admin/dashboard', icon: 'dashboard' },
  { title: '角色管理', path: '/admin/system/role', icon: 'people' },
  { title: '图表', path: '/admin/statistics/charts', icon: 'insert_chart' }
]);
</script>

<style lang="scss" scoped>
.home-page {
  padding: 1rem;
}
</style>