<template>
  <div class="markup-tables flex">
    <va-card class="flex mb-4">
      <va-card-title>{{ title }}</va-card-title>
      <va-card-content>
        <div class="table--query row">
          <slot name="query"  />
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
        <va-divider />
        <div>
          <va-button @click="addRow">
            {{ t("buttons.add") }}
          </va-button>
        </div>
        <va-data-table
          :items="items"
          :columns="columns"
          v-model:sort-by="sortBy"
          v-model:sorting-order="sortingOrder"
        >
          <template #cell(actions)="{rowIndex}">
            <va-button-group>
              <va-button
                @click="editRow(rowIndex)"
                color="primary"
                size="small"
                class="mr-2"
              >
                {{ t("buttons.edit") }}
              </va-button>
              <va-button
                @click="deleteRow(rowIndex)"
                color="danger"
                size="small"
              >
                {{ t("buttons.delete") }}
              </va-button>
            </va-button-group>
          </template>
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
    title: t("messages.confirm"),
    message: t("messages.confirmDelete"),
    okText: t("buttons.confirm"),
    cancelText: t("buttons.cancel")
  });
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
  height: calc(100% - 133px);
}
</style>
