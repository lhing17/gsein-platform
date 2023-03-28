<template>
  <div class="markup-tables flex">

    <va-card class="flex mb-4">
      <va-card-title>{{ t("tables.basic") }}</va-card-title>

      <va-card-content>
        <va-data-table
          :items="roles"
          :columns="columns">
        </va-data-table>
        <div class="table--pagination justify-center">
          <div class="justify-center">
            <va-select
              v-model="size"
              :options="options">
              <template #prepend>
                <span>显示第 {{ pageStart }} 到第 {{ pageEnd }} 条记录，总共 {{ total }} 条记录</span>
                <span>每页显示</span>
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
import { ref, watchEffect } from "vue";
import { useI18n } from "vue-i18n";
import { roleList } from "@/services/role";
import { DataTableItem } from "vuestic-ui";
import { DataTableColumnSource } from "vuestic-ui/dist/types/components/va-data-table/types";
import { Ref } from "@vue/reactivity";

const { t } = useI18n();

const options = [1, 10, 20, 50, 100];
const page = ref(1);
const size = ref(options[0]);

const roles: Ref<DataTableItem[]> = ref([]);
const pages: Ref<number> = ref(0);
const pageStart: Ref<number> = ref(0);
const pageEnd: Ref<number> = ref(0);
const total: Ref<number> = ref(0);

// 只显示id、name、roleKey、sort
const columns: DataTableColumnSource[] = [
  { key: "id", label: t("tables.headings.id") },
  { key: "name", label: t("tables.headings.name") },
  { key: "roleKey", label: t("tables.headings.roleKey") },
  { key: "sort", label: t("tables.headings.sort") }
];

// 监听页码变化
watchEffect(async () => {
  const response = await roleList(page.value, size.value, "", {});
  roles.value = response.data.content;
  pages.value = response.data.totalPages;
  pageStart.value = (page.value - 1) * size.value + 1;
  pageEnd.value = page.value * size.value;
  total.value = response.data.totalElements;

});


</script>
<style lang="scss" scoped>
.markup-tables {
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

 :deep(.va-input-wrapper__field)  {
  width: 80px;
}
</style>
