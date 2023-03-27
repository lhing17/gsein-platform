import request from "@/services/request";

const roleList = async () => {
  const response = await request.get("/api/v1/role/list");
  return response.data;
};

export { roleList };
