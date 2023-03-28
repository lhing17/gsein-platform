import request from "@/services/request";

const roleList = async (page: number, size: number, sorting: String, condition: Object) => {
  const response = await request.get("/api/v1/role/list", {
    params: {
      page,
      size,
      sorting,
      ...condition
    }
  });
  return response.data;
};

const deleteRole = async (id: number) => {
  const response = await request.delete(`/api/v1/role/${id}`);
  return response.data;
};

export { roleList, deleteRole };
