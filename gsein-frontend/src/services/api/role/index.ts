import request from "@/services/request";

const roleList = async (page: number, size: number, sorting: string, condition: object) => {
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

const updateRole = async (data: Object) => {
  const response = await request.put("/api/v1/role/update", data);
  return response.data;
};

const addRole = async (data: Object) => {
  const response = await request.post("/api/v1/role/save", data);
  return response.data;
};

const getRoleById = async (id: number) => {
  const response = await request.get(`/api/v1/role/${id}`);
  return response.data;
};

export { roleList, deleteRole, updateRole, addRole, getRoleById };
