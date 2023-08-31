import api from 'api';

interface Userinfo {
  username: string;
  password: string;
}

const fetchSleeperAccount = (
  userInfo: Userinfo,
  navigate: (to: string) => void
) => {
  api
    .post('/api/auth/active', userInfo)
    .then(() => {
      window.alert('해제되었습니다.');
      navigate('/signin');
    })
    .catch((err) => {
      console.log(err);
    });
};

export default fetchSleeperAccount;
