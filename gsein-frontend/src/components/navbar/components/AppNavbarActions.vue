<template>
  <div class="app-navbar-actions">
    <va-switch
      v-model="value"
      color="#5123a1"
      off-color="#ffd300"
      style="--va-switch-checker-background-color: #252723;"
    >
      <template #innerLabel>
        <div class="va-text-center">
          <va-icon
            size="24px"
            :name="value ? 'dark_mode' : 'light_mode'"
          />
        </div>
      </template>
    </va-switch>
<!--    <color-dropdown class="app-navbar-actions__item" />-->
    <message-dropdown class="app-navbar-actions__item" />
    <notification-dropdown class="app-navbar-actions__item" />
<!--     <settings-dropdown class="app-navbar-actions__item" />-->
    <language-dropdown class="app-navbar-actions__item" />
    <profile-dropdown class="app-navbar-actions__item app-navbar-actions__item--profile">
      <span>{{ userName }}</span>
    </profile-dropdown>
  </div>
</template>

<script setup lang="ts">
  import LanguageDropdown from './dropdowns/LanguageDropdown.vue'
  import ProfileDropdown from './dropdowns/ProfileDropdown.vue'
  import NotificationDropdown from './dropdowns/NotificationDropdown.vue'
  import MessageDropdown from './dropdowns/MessageDropdown.vue'
  import { ref } from "@vue/reactivity";
  import { useColors } from "vuestic-ui";
  import { watchEffect } from "vue";
  const { presets, applyPreset } = useColors()

  withDefaults(
    defineProps<{
      userName?: string
      isTopBar?: boolean
    }>(),
    {
      userName: '',
      isTopBar: false,
    },
  )

  defineEmits<{
    (e: 'update:isTopBar', isTopBar: boolean): void
  }>()

  const value = ref(false)

  watchEffect(() => {
    let theme = value.value ? 'dark' : 'light'
    applyPreset(theme)
  })

  // const isTopBarProxy = computed({
  //   get() {
  //     return props.isTopBar
  //   },
  //
  //   set(isTopBar: boolean) {
  //     emit('update:isTopBar', isTopBar)
  //   },
  // })
</script>

<style lang="scss">
  .app-navbar-actions {
    display: flex;
    align-items: center;

    .va-dropdown__anchor {
      color: var(--va-primary);
      fill: var(--va-primary);
    }

    &__item {
      padding: 0;
      margin-left: 1.25rem;
      margin-right: 1.25rem;

      svg {
        height: 24px;
      }

      &:last-of-type {
        margin-right: 0;
      }

      &--profile {
        display: flex;
        justify-content: center;
        margin: auto 0 auto 1.25rem;
      }

      .va-dropdown-content {
        background-color: var(--va-white);
      }

      @media screen and (max-width: 640px) {
        margin-right: 0;

        &:first-of-type {
          margin-left: 0;
        }

        &--profile {
          position: absolute;
          right: 0.75rem;
          top: 1.25rem;
          height: fit-content;
          margin: auto;
        }
      }
    }
  }
</style>
