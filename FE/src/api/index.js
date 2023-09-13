import axios from 'axios';

const Authorization = localStorage.getItem('authorization');
const Refresh = localStorage.getItem('refresh');

const api = axios.create({
  baseURL: process.env.REACT_APP_BASE_URL,
  headers: {
    Authorization,
  },
});

const refreshApi = axios.create({
  baseURL: process.env.REACT_APP_BASE_URL,
  headers: {
    Refresh,
  },
});

let isRefreshing = false;
let refreshSubscribers = [];

api.interceptors.response.use(
  (res) => {
    return res;
  },
  (err) => {
    if (err.config.url === '/api/login') throw err;
    if (err.config.url === '/api/auth/refresh') throw err;
    const originalRequest = err.config;

    if (err.response.status === 401) {
      if (!isRefreshing) {
        isRefreshing = true;

        return refreshApi
          .post('/api/auth/refresh')
          .then((res) => {
            localStorage.setItem('authorization', res.headers.authorization);
            localStorage.setItem('refresh', res.headers.refresh);
            isRefreshing = false;

            refreshSubscribers.forEach((callback) => callback());
            refreshSubscribers = [];
            return api(originalRequest);
          })
          .catch((refreshErr) => {
            console.log(refreshErr);
            isRefreshing = false;
          });
      } else {
        return new Promise((resolve) => {
          refreshSubscribers.push(() => {
            resolve(api(originalRequest));
          });
        });
      }
    }

    throw err;
  }
);

export default api;
