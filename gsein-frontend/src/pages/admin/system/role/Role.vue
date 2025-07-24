<template>
  <div class="role-page">
    <paged-table :data-producer="roleList"
                 :columns="columns"
                 :title="t('menu.role')"
                 :query="query"
                 @add="addRow"
                 @edit="editRow"
                 @delete="deleteRow"
                 @reset="reset"
                 ref="roleTable">
      <template #query>
        <div class="query-form">
          <va-input v-model="query.name"
                    :label="t('tables.headings.name')"
                    :placeholder="t('tables.headings.name')"
                    class="query-item mb-2 mr-4" />
          <va-input v-model="query.roleKey"
                    :label="t('tables.headings.roleKey')"
                    :placeholder="t('tables.headings.roleKey')"
                    class="query-item mb-2" />
        </div>
      </template>
    </paged-table>
    <role-modal
      :id="id"
      :edited-item="editedItem"
      :shown="modalShown"
      @close="hideModal" />
  </div>
</template>
<script setup lang="ts">
import { nextTick, ref } from "vue";
import { useI18n } from "vue-i18n";
import { deleteRole, roleList } from "@/services/api/role";
import RoleModal from "@/pages/admin/system/role/RoleModal.vue";
import PagedTable from "@/components/tables/PagedTable.vue";

const { t } = useI18n();

// 只显示id、name、roleKey、sort
const columns = [
  { key: "id", label: t("tables.headings.id"), sortable: true },
  { key: "name", label: t("tables.headings.name") },
  { key: "roleKey", label: t("tables.headings.roleKey") },
  { key: "sort", label: t("tables.headings.sort") },
  { key: "actions", label: t("tables.headings.actions") }
];

const id = ref(0);
const editedItem = ref({});
const modalShown = ref(false);
const roleTable = ref();
const query = ref({
  name: "",
  roleKey: ""
});

function editRow(_id: number, _editedItem: object) {
  id.value = _id;
  editedItem.value = _editedItem;
  modalShown.value = true;
}

function addRow(_id: number, _editedItem: object) {
  id.value = _id;
  editedItem.value = _editedItem;
  modalShown.value = true;
}

function hideModal() {
  modalShown.value = false;
  roleTable.value.loadData();
}

async function deleteRow(_id: number) {
  await deleteRole(_id);
  await roleTable.value.loadData();
}

async function reset() {
  query.value = {
    name: "",
    roleKey: ""
  };
  await nextTick(roleTable.value.loadData);
}


</script>
<style lang="scss" scoped>
.role-page {
  height: calc(100% - 41px);
  padding: 0 12px;
}

.query-form {
  width: 100%;
  margin-left: 0;
  margin-right: 0;
}

.query-item {
  min-width: 200px;
}

.query-item {
  width: 200px;
}
</style>
