import api from 'api';
import type { Inquiry } from 'model/inquiry';
import { useEffect, useState } from 'react';

const useFetchInquiry = () => {
  const [inpuiry, setInquiry] = useState<Inquiry[]>([]);

  useEffect(() => {
    api
      .get('/api/qna/admin')
      .then((res) => {
        setInquiry(res.data.data.content.reverse());
      })
      .catch((err) => {
        console.log(err);
      });
  }, [setInquiry]);

  return inpuiry;
};

export default useFetchInquiry;
