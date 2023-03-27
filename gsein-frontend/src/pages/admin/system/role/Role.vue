<template>
  <div class="markup-tables flex">

    <va-card class="flex mb-4">
      <va-card-title>{{ t("tables.basic") }}</va-card-title>

      <va-card-content>
        <div class="table-wrapper">
          <table class="va-table">
            <thead>
            <tr>
              <th>{{ t("tables.headings.id") }}</th>
              <th>{{ t("tables.headings.name") }}</th>
              <th>{{ t("tables.headings.roleKey") }}</th>
              <th>{{ t("tables.headings.sort") }}</th>
            </tr>
            </thead>

            <tbody>

              <tr v-for="role in roles" :key="role.id">
                <td>{{ role.id }}</td>
                <td>{{ role.name }}</td>
                <td>{{ role.roleKey }}</td>
                <td>{{ role.sort }}</td>
              </tr>


            </tbody>
          </table>
        </div>
      </va-card-content>


    </va-card>

  </div>
</template>
<script setup lang="ts">
import { ref } from "vue";
import { useI18n } from "vue-i18n";
import { roleList } from "@/services/role";

const { t } = useI18n();

const response = await roleList();

const roles = ref(response.data.content);



</script>
<style lang="scss">
.markup-tables {
  .table-wrapper {
    overflow: auto;
  }

  .va-table {
    width: 100%;
  }
}
</style>
