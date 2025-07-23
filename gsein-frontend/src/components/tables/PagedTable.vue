<template>
  <div class="markup-tables flex">
    <va-card class="flex mb-4">
      <va-card-title class="card-title">{{ title }}</va-card-title>
      <va-card-content>
        <div class="table--query row">
          <slot name="query" />
          <va-spacer />
          <div class="flex justify-center query-buttons">
            <va-button @click="loadData" class="mr-2" preset="primary" size="small">
              {{ t("buttons.query") }}
            </va-button>
            <va-button @click="reset" preset="secondary" border-color="primary" size="small">
              {{ t("buttons.reset") }}
            </va-button>
          </div>
        </div>
        <va-divider class="my-3" />
        <div class="mb-3">
          <va-button @click="addRow" preset="primary" size="small">
            {{ t("buttons.add") }}
          </va-button>
        </div>
        <va-data-table
          :items="items"
          :columns="columns"
          v-model:sort-by="sortBy"
          v-model:sorting-order="sortingOrder"
          class="mb-3"
          striped
          hoverable
        >
          <template #cell(actions)="{rowIndex}">
            <va-button-group>
              <va-button
                @click="editRow(rowIndex)"
                color="primary"
                size="small"
                icon="edit"
                class="mr-1"
                preset="primary"
              >
                {{ t("buttons.edit") }}
              </va-button>
              <va-button
                @click="deleteRow(rowIndex)"
                color="danger"
                size="small"
                icon="delete"
                preset="danger"
              >
                {{ t("buttons.delete") }}
              </va-button>
            </va-button-group>
          </template>
        </va-data-table>
        <div class="table--pagination">
          <div class="pagination-info">
            <va-select
              v-model="size"
              :options="options"
              size="small"
              class="size-select"
            >
              <template #prepend>
                <span class="pagination-text">{{ t('tables.showing') }} {{ pageStart }} {{ t('tables.to') }} {{ pageEnd }} {{ t('tables.of') }} {{ total }} {{ t('tables.entries') }}</span>
                <span class="ml-2">{{ t('tables.pageSize') }}</span>
              </template>
              <template #append>
                <span>{{ t('tables.entries') }}</span>
              </template>
            </va-select>
          </div>
          <div class="pagination-controls">
            <va-pagination
              v-model="page"
              :pages="pages"
              :visible-pages="5"
              buttons-preset="primary"
              rounded
              gapped
              border-color="primary"
            />
          </div>
        </div>
      </va-card-content>
    </va-card>
  </div>
</template>
<script setup lang="ts">

import { Ref } from "@vue/reactivity";
import { computed, ref, watch } from "vue";
import { DataTableColumnSource, DataTableItem, DataTableSortingOrder, useModal } from "vuestic-ui";
import { useI18n } from "vue-i18n";

const props = withDefaults(
  defineProps<{
    title?: string
    dataProducer: (page: number, size: number, sorting: string, condition: object) => Promise<any>,
    columns: DataTableColumnSource[],
    query?: object
  }>(),
  {
    title: "分页表格",
    columns: () => [],
    query: () => ({})
  }
);

const { t } = useI18n();
const { confirm } = useModal();

const options = [10, 20, 50, 100];
const page = ref(1);
const size = ref(options[0]);

const items: Ref<DataTableItem[]> = ref([]);
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

async function loadData() {
  const response = await props.dataProducer(page.value, size.value, sorting.value, props.query);
  items.value = response.data.content;
  pages.value = response.data.totalPages;
  pageStart.value = (page.value - 1) * size.value + 1;
  pageEnd.value = page.value * size.value;
  total.value = response.data.totalElements;
}

const id = ref(0);
const editedItem = ref({});

const emit = defineEmits(["edit", "add", "delete", "reset"]);


function editRow(rowIndex: number) {
  id.value = items.value[rowIndex].id;
  const item = items.value[rowIndex];
  editedItem.value = {
    name: item.name,
    roleKey: item.roleKey,
    sort: item.sort
  };

  emit("edit", id.value, editedItem.value);

}

function addRow() {
  id.value = 0;
  editedItem.value = {
    name: "",
    roleKey: "",
    sort: 0
  };
  emit("add", id.value, editedItem.value);
}

async function deleteRow(rowIndex: number) {
  const ok = await confirm({
    title: t("messages.systemMessage"),
    message: t("messages.confirmDelete"),
    okText: t("modal.confirm"),
    cancelText: t("modal.cancel"),
  })
  if (!ok) {
    return;
  }
  const id = items.value[rowIndex].id;
  emit("delete", id);
}

function reset() {
  page.value = 1;
  size.value = options[0];
  sortBy.value = "id";
  sortingOrder.value = "asc";
  emit("reset");
}

watch([page, size, sorting], loadData);
loadData();

defineExpose({
  loadData
});

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

.card-title {
  font-size: 1.25rem;
  font-weight: 600;
  padding: 1rem 1.5rem;
}

.table--pagination {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  margin-top: 1rem;
}

.pagination-info {
  display: flex;
  align-items: center;
}

.pagination-text {
  font-size: 0.875rem;
  color: var(--va-text-secondary);
}

.pagination-controls {
  margin-top: 0.5rem;
}

.size-select {
  width: 80px;
  margin-left: 0.5rem;
}

:deep(.va-input-wrapper__field) {
  width: 80px;
}

:deep(.va-input-wrapper__size-keeper) {
  width: auto;
}

.table--query {
  margin-bottom: 1rem;
}

.table--query:deep(.va-input-wrapper__label) {
  height: 14px;
  font-size: 0.875rem;
}

.query-buttons {
  margin-top: 1.5rem;
}

.va-card {
  height: 100%;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.va-card__content {
  height: calc(100% - 55px);
  padding: 1.5rem;
}

.va-data-table {
  height: calc(100% - 133px);
  border-radius: 4px;
  overflow: hidden;
}
</style>
