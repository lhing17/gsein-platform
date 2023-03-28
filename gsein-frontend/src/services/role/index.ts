import request from "@/services/request";

const roleList = async (page: number, size: number, sort: String, condition: Object) => {
  const response = await request.get("/api/v1/role/list", {
    params: {
      page,
      size,
      sort,
      ...condition
    }
  });
  return response.data;
};

export { roleList };
