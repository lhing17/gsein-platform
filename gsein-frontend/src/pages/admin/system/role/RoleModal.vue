<template>
  <va-modal
    class="modal-crud-example"
    :model-value="shown"
    title="Edit item"
    size="small"
    @ok="saveOrUpdate"
    @cancel="resetEditedItem"
  >
    <va-input
      v-for="key in Object.keys(realEditedItem)"
      :key="key"
      v-model="realEditedItem[key]"
      class="my-3"
      :label="key"
    />
  </va-modal>
</template>

<script setup lang="ts">
import { addRole, getRoleById, updateRole } from "@/services/api/role";
import { ref, watchEffect } from "vue";

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

</style>
