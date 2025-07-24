<template>
  <va-modal
    class="modal-crud-example"
    :model-value="shown"
    :title="id === 0 ? t('buttons.add') + t('menu.role') : t('buttons.edit') + t('menu.role')"
    size="small"
    close-button
    @ok="saveOrUpdate"
    @cancel="resetEditedItem"
  >
    <div class="role-form pa-4">
      <va-input
        v-model="realEditedItem.name"
        class="mb-4"
        :label="t('tables.headings.name')"
        :placeholder="t('tables.headings.name')"
      />
      <va-input
        v-model="realEditedItem.roleKey"
        class="mb-4"
        :label="t('tables.headings.roleKey')"
        :placeholder="t('tables.headings.roleKey')"
      />
      <va-input
        v-model="realEditedItem.sort"
        class="mb-4"
        :label="t('tables.headings.sort')"
        type="number"
        :placeholder="t('tables.headings.sort')"
      />
    </div>
  </va-modal>
</template>

<script setup lang="ts">
import { addRole, getRoleById, updateRole } from "@/services/api/role";
import { ref, watchEffect } from "vue";
import { useI18n } from "vue-i18n";

const { t } = useI18n();

const props = withDefaults(
  defineProps<{
    shown: boolean,
    id: number,
    editedItem: { name?: string, roleKey?: string, sort?: number }
  }>(),
  {
    shown: false,
    id: 0,
    editedItem: () => {
      return { name: "", roleKey: "", sort: 0 };
    }
  }
);

const realEditedItem = ref(props.editedItem);
watchEffect(() => {
  realEditedItem.value = props.editedItem;
});

async function saveOrUpdate() {
  // 获取props中的id
  const { id } = props;
  if (id === 0) {
    // 新增
    await addRole(realEditedItem.value);
  } else {
    // 修改
    let role = await getRoleById(id);
    role = { ...role.data, ...realEditedItem.value };
    await updateRole(role);
  }
  emit("close");
}

const emit = defineEmits(["close"]);

function resetEditedItem() {
  emit("close");
}

</script>

<style lang="scss" scoped>
.role-form {
  min-width: 300px;
}
</style>
