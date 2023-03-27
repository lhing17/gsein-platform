import axios from "@/services/request";

const login = async (username: string, password: string) => {
  const response = await axios.post("/api/v1/user/login", { username, password });
  return response.data;
};


export { login };


