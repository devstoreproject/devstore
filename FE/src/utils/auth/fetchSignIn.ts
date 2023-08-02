import api from 'api';

interface userInfo {
  username: string;
  password: string;
}

const fetchSignIn = (
  userInfo: userInfo,
  navigate: (to: string) => void,
  setFn: React.Dispatch<React.SetStateAction<boolean>>
) => {
  api
    .post('/api/login', {
      userInfo,
    })
    .then((res) => {
      localStorage.setItem('authorization', res.headers.authorization);
      localStorage.setItem('refresh', res.headers.refresh);
      navigate('/');
    })
    .catch((err) => {
      console.log(err);
      setFn(false);
    });
};

export default fetchSignIn;
