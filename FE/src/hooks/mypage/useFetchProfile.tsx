import { useEffect, useState } from 'react';
import api from 'api';

interface profile {
  userId: number;
  email: string;
  password: string;
  nickname: string;
  profileImage: string | null;
}
const useFetchProfile = () => {
  const [profile, setProfile] = useState<profile>({
    userId: 0,
    email: '',
    password: '',
    nickname: '',
    profileImage: null,
  });

  const userId = localStorage.getItem('userId');
  const authorization = localStorage.getItem('authorization');

  useEffect(() => {
    if (userId !== null) {
      api
        .get(`/api/users/${userId}`, {
          headers: {
            Authorization: authorization,
          },
        })
        .then((res) => {
          setProfile(res.data.data);
        })
        .catch((err) => {
          console.error(err);
        });
    }
  }, [setProfile, authorization]);

  return profile;
};

export default useFetchProfile;
