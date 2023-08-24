import axios from 'axios';

const api = axios.create({
  baseURL: process.env.REACT_APP_BASE_URL,
});

const refreshApi = axios.create({
  baseURL: process.env.REACT_APP_BASE_URL,
  headers: {
    Refresh: localStorage.getItem('refresh'),
  },
});

api.interceptors.response.use(
  (res) => {
    return res;
  },
  (err) => {
    if (err.response.status === 401) {
      refreshApi
        .post('/api/auth/refresh')
        .then((res) => {
          localStorage.setItem('authorization', res.headers.authorization);
          localStorage.setItem('refresh', res.headers.refresh);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }
);

export default api;
