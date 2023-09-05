import api from 'api';
import type { Inquiry } from 'model/inquiry';
import { useEffect, useState } from 'react';

const useFetchInquiry = () => {
  const Authorization = localStorage.getItem('authorization');
  const [inpuiry, setInquiry] = useState<Inquiry[]>([]);

  useEffect(() => {
    api
      .get(`/api/qna/admin?sort=createdAt,desc`, {
        headers: {
          Authorization,
        },
      })
      .then((res) => {
        setInquiry(res.data.data.content);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [setInquiry]);

  return inpuiry;
};

export default useFetchInquiry;
