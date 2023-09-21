import api from 'api';

interface UserInfo {
  username: string;
  password: string;
}

interface FetchSignIn {
  userInfo: UserInfo;
  navigate: (to: string) => void;
  setIsSignInSuccess: React.Dispatch<React.SetStateAction<boolean>>;
}

const fetchSignIn = ({
  userInfo,
  navigate,
  setIsSignInSuccess,
}: FetchSignIn) => {
  api
    .post('/api/login', userInfo)
    .then((res) => {
      localStorage.setItem('authorization', res.headers.authorization);
      localStorage.setItem('refresh', res.headers.refresh);
      localStorage.setItem('userId', res.data.data.userId);
      navigate('/');
    })
    .catch((err) => {
      console.log(err);
      setIsSignInSuccess(false);
    });
};

export default fetchSignIn;
