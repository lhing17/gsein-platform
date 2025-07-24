<template>
  <div 
    v-if="visible" 
    class="tab-context-menu" 
    :style="{ top: `${top}px`, left: `${left}px` }"
  >
    <div class="tab-context-menu__item" @click="onClose">
      <va-icon name="close" class="mr-2" />
      {{ t('tabs.close') }}
    </div>
    <div class="tab-context-menu__item" @click="onCloseOthers">
      <va-icon name="format_line_spacing" class="mr-2" />
      {{ t('tabs.closeOthers') }}
    </div>
    <div class="tab-context-menu__item" @click="onCloseAll">
      <va-icon name="clear_all" class="mr-2" />
      {{ t('tabs.closeAll') }}
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onBeforeUnmount } from 'vue';
import { useI18n } from 'vue-i18n';

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  },
  top: {
    type: Number,
    default: 0
  },
  left: {
    type: Number,
    default: 0
  },
  tabPath: {
    type: String,
    default: ''
  }
});

const emit = defineEmits(['close', 'closeOthers', 'closeAll', 'update:visible']);

const { t } = useI18n();

// 关闭当前标签页
const onClose = () => {
  emit('close', props.tabPath);
  emit('update:visible', false);
};

// 关闭其他标签页
const onCloseOthers = () => {
  emit('closeOthers', props.tabPath);
  emit('update:visible', false);
};

// 关闭所有标签页
const onCloseAll = () => {
  emit('closeAll');
  emit('update:visible', false);
};

// 点击其他区域关闭菜单
const handleClickOutside = (event) => {
  if (props.visible) {
    emit('update:visible', false);
  }
};

// 监听全局点击事件
watch(() => props.visible, (newValue) => {
  if (newValue) {
    setTimeout(() => {
      window.addEventListener('click', handleClickOutside);
    }, 0);
  } else {
    window.removeEventListener('click', handleClickOutside);
  }
});

// 组件卸载时移除事件监听
onBeforeUnmount(() => {
  window.removeEventListener('click', handleClickOutside);
});
</script>

<style lang="scss" scoped>
.tab-context-menu {
  position: fixed;
  background: white;
  border-radius: 4px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.15);
  z-index: 1000;
  min-width: 160px;
  
  &__item {
    padding: 8px 16px;
    display: flex;
    align-items: center;
    cursor: pointer;
    transition: background-color 0.2s;
    
    &:hover {
      background-color: rgba(0, 0, 0, 0.05);
    }
  }
}
</style>