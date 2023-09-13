import api from 'api';
import type { Inquiry } from 'model/inquiry';
import { useEffect, useState } from 'react';

const useFetchInquiryPaging = (page: number, checked: boolean[]) => {
  const [inpuiry, setInquiry] = useState<Inquiry[]>([]);

  useEffect(() => {
    const answerStatus = checked[0] ? 'REGISTER' : 'ANSWER_COMPLETE';
    api
      .get(
        `/api/qna/admin?status=${answerStatus}&page=${page}&size=10&sort=createdAt,desc`
      )
      .then((res) => {
        setInquiry(res.data.data.content);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [setInquiry, page, checked]);

  return inpuiry;
};

export default useFetchInquiryPaging;
