import api from 'api';

interface Userinfo {
  email: string;
  name: string;
  phone: string;
}

const fetchSearchPassword = (
  userInfo: Userinfo,
  setIsModalOpen: React.Dispatch<React.SetStateAction<boolean>>,
  setTemporaryPassword: React.Dispatch<React.SetStateAction<string>>
) => {
  api
    .post('/api/users/password', userInfo)
    .then((res) => {
      setTemporaryPassword(res.data.data.password);
      setIsModalOpen(true);
    })
    .catch((error) => {
      console.log(error);
    });
};

export default fetchSearchPassword;
