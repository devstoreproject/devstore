import api from 'api';
import type { Inquiry } from 'model/inquiry';
import { useEffect, useState } from 'react';

const useFetchInquiryPaging = (page: number, checked: boolean[]) => {
  const [inquirys, setInquirys] = useState<Inquiry[]>([]);
  const [totalPages, setTotalPages] = useState<number>(0);

  useEffect(() => {
    const answerStatus = checked[0] ? 'REGISTER' : 'ANSWER_COMPLETE';
    api
      .get(
        `/api/qna/admin?status=${answerStatus}&page=${page}&size=10&sort=createdAt,desc`
      )
      .then((res) => {
        setTotalPages(res.data.data.totalPages);
        setInquirys(res.data.data.content);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [setInquirys, page, checked]);

  return { inquirys, totalPages };
};

export default useFetchInquiryPaging;
