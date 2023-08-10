import api from 'api';

const fetchLogOut = (navigate: (to: string) => void) => {
  const Refresh = localStorage.getItem('refresh');
  api
    .post('/api/log-out', null, {
      headers: {
        Refresh,
      },
    })
    .then(() => {
      localStorage.removeItem('userId');
      localStorage.removeItem('authorization');
      localStorage.removeItem('refresh');
      navigate('/');
    })
    .catch((err) => {
      console.log(err);
    });
};

export default fetchLogOut;
