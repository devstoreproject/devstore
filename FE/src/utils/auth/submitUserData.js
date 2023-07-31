import api from 'api';
import { useNavigate } from 'react-router-dom';

const navigate = useNavigate();

export const submitUserData = (userData) => {
  const formData = new FormData();
  const blob = new Blob([JSON.stringify(userData)], {
    type: 'application/json',
  });

  formData.append('post', blob);

  api
    .post('/api/users', formData)
    .then(() => {
      navigate('/signin');
    })
    .catch((err) => {
      console.log(err);
    });
};
