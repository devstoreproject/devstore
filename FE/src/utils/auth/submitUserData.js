import api from 'api';
import { useNavigate } from 'react-router-dom';

export const submitUserData = (userData) => {
  const navigate = useNavigate();
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
