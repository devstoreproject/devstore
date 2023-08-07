import api from 'api';

interface UserData {
  email: string;
  password: string;
  userName: string;
  nickname: string;
  phone: string;
}

const fetchSignUp = (userData: UserData, navigate: (to: string) => void) => {
  const formData = new FormData();
  const blob = new Blob([JSON.stringify(userData)], {
    type: 'application/json',
  });

  formData.append('post', blob);

  api
    .post('/api/users', formData)
    .then((res) => {
      window.alert(res.data.message);
      navigate('/signin');
    })
    .catch((err) => {
      if (err.response.status === 409)
        window.alert('이미 가입한 메일이 존재합니다.');
    });
};

export default fetchSignUp;
