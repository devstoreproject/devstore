import api from 'api';
import type { Inquiry } from 'model/inquiry';
import { useEffect, useState } from 'react';

const useFetchInquiryPaging = (page: number) => {
  const [inpuiry, setInquiry] = useState<Inquiry[]>([]);

  useEffect(() => {
    api
      .get(`/api/qna/admin?page=${page}&size=10`)
      .then((res) => {
        setInquiry(res.data.data.content);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [setInquiry, page]);

  return inpuiry;
};

export default useFetchInquiryPaging;
