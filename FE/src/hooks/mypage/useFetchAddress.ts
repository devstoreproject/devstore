import api from 'api';
import type { Address } from 'model/auth';
import { useEffect, useState } from 'react';

const useFetchAddress = () => {
  const [address, setAddress] = useState<Address[]>([]);
  const userId = localStorage.getItem('userId');

  useEffect(() => {
    if (userId !== null) {
      api
        .get(`/api/address/users/${userId}`)
        .then((res) => {
          setAddress(res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, [setAddress]);

  return address;
};

export default useFetchAddress;
