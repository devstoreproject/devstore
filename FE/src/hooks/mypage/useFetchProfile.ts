import { useEffect, useState } from 'react';
import api from 'api';
import type { Profile } from 'model/auth';

const useFetchProfile = () => {
  const [profile, setProfile] = useState<Profile>({
    userId: 0,
    email: '',
    phone: '',
    nickname: '',
    profileImage: null,
    username: '',
  });

  const userId = localStorage.getItem('userId');

  useEffect(() => {
    if (userId !== null) {
      api
        .get(`/api/users/${userId}`)
        .then((res) => {
          setProfile(res.data.data);
        })
        .catch((err) => {
          console.error(err);
        });
    }
  }, [setProfile]);

  return profile;
};

export default useFetchProfile;
