import api from 'api';
import type { Inquiry } from 'model/inquiry';
import { useEffect, useState } from 'react';

const useFetchInquiryPaging = (page: number, checked: boolean[]) => {
  const [inquirys, setInquirys] = useState<Inquiry[]>([]);
  const [totalPages, setTotalPages] = useState<number>(0);

  useEffect(() => {
    let answerStatus = '';

    if (checked[0] && checked[1]) answerStatus = '';
    if (checked[0] && !checked[1]) answerStatus = 'REGISTER';
    if (!checked[0] && checked[1]) answerStatus = 'ANSWER_COMPLETE';
    if (!checked[0] && !checked[1]) {
      setInquirys([]);
      return;
    }

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
