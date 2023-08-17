import api from 'api';
import type { Profile } from 'model/auth';

const fetchProfileImage = (image: File, profile: Profile) => {
  const userId = localStorage.getItem('userId');
  const authorization = localStorage.getItem('authorization');

  const formData = new FormData();
  const blob = new Blob([JSON.stringify({})], {
    type: 'application/json',
  });

  formData.append('image', image);
  formData.append('patch', blob);

  if (userId !== null) {
    api
      .patch(`/api/users/${userId}`, formData, {
        headers: {
          Authorization: authorization,
        },
      })
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });
  }
};

export default fetchProfileImage;
