<template>
  <div class="markup-tables flex">

    <va-card class="flex mb-4">
      <va-card-title>{{ t("menu.role") }}</va-card-title>

      <va-card-content>

        <div class="table--query row">
          <va-input v-model="query.name"
                    label="名称"
                    class="flex flex-col md3" />
          <va-input v-model="query.roleKey"
                    label="角色Key"
                    class="flex flex-col md3" />
          <va-spacer />
          <div class="flex justify-center">
            <va-button @click="loadData" class="mr-2">
              {{ t("buttons.query") }}
            </va-button>
            <va-button @click="reset" preset="secondary" border-color="primary">
              {{ t("buttons.reset") }}
            </va-button>
          </div>
        </div>
        <va-divider/>
        <va-data-table
          :items="roles"
          :columns="columns"
          v-model:sort-by="sortBy"
          v-model:sorting-order="sortingOrder"
        >
        </va-data-table>
        <div class="table--pagination justify-center">
          <div class="justify-center">
            <va-select
              v-model="size"
              :options="options">
              <template #prepend>
                <span>显示第 {{ pageStart }} 到第 {{ pageEnd }} 条记录，总共 {{ total }} 条记录</span>
                <span> &nbsp;&nbsp;&nbsp;&nbsp每页显示</span>
              </template>
              <template #append>
                <span>条记录</span>
              </template>
            </va-select>
          </div>
          <va-spacer></va-spacer>
          <div class="justify-center">
            <va-pagination
              v-model="page"
              :pages="pages"
              :visible-pages="3"
              buttons-preset="primary"
              rounded
              gapped
              border-color="primary"
              class="mb-6"
            />
          </div>
        </div>
      </va-card-content>


    </va-card>

  </div>
</template>
<script setup lang="ts">
import { computed, ref, watch } from "vue";
import { useI18n } from "vue-i18n";
import { roleList } from "@/services/role";
import { DataTableItem, DataTableSortingOrder } from "vuestic-ui";
import { DataTableColumnSource } from "vuestic-ui/dist/types/components/va-data-table/types";
import { Ref } from "@vue/reactivity";

const { t } = useI18n();

const options = [10, 20, 50, 100];
const page = ref(1);
const size = ref(options[0]);

const roles: Ref<DataTableItem[]> = ref([]);
const pages: Ref<number> = ref(0);
const pageStart: Ref<number> = ref(0);
const pageEnd: Ref<number> = ref(0);
const total: Ref<number> = ref(0);
const sortBy: Ref<string> = ref("id");
const sortingOrder: Ref<DataTableSortingOrder> = ref("asc");

const sorting = computed(() => {
  if (sortingOrder.value === null) {
    return "";
  } else {
    return sortBy.value + " " + sortingOrder.value;
  }
});

const query = ref({
  name: "",
  roleKey: ""
});

// 只显示id、name、roleKey、sort
const columns: DataTableColumnSource[] = [
  { key: "id", label: t("tables.headings.id"), sortable: true },
  { key: "name", label: t("tables.headings.name") },
  { key: "roleKey", label: t("tables.headings.roleKey") },
  { key: "sort", label: t("tables.headings.sort") }
];

async function loadData() {
  const response = await roleList(page.value, size.value, sorting.value, query.value);
  roles.value = response.data.content;
  pages.value = response.data.totalPages;
  pageStart.value = (page.value - 1) * size.value + 1;
  pageEnd.value = page.value * size.value;
  total.value = response.data.totalElements;
}

function reset() {
  query.value = {
    name: "",
    roleKey: ""
  };
  page.value = 1;
  size.value = options[0];
  sortBy.value = "id";
  sortingOrder.value = "asc";
  loadData();
}

watch([page, size, sorting], loadData);
loadData()


</script>
<style lang="scss" scoped>
.markup-tables {
  height: calc(100vh - 112px);
  .table-wrapper {
    overflow: auto;
  }

  .va-table {
    width: 100%;
  }
}

.table--pagination {
  display: flex;
  justify-content: center;
}

:deep(.va-input-wrapper__field) {
  width: 80px;
}

.table--query:deep(.va-input-wrapper__label) {
  height: 14px;
}
.va-card {
  height: 100%;
}
.va-card__content {
  height: calc(100% - 55px);
}
.va-data-table {
  height: calc(100% - 97px);
}
</style>
