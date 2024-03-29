import api from 'api';
import type { InquiryDetail } from 'model/inquiry';
import { useEffect, useState } from 'react';

const useFetchInquiryDetail = (inquiryId: number) => {
  const [inpuiryDetail, setInquiryDetail] = useState<InquiryDetail>({
    comment: '',
    answer: {
      comment: '',
    },
  });

  useEffect(() => {
    api
      .get(`/api/qna/${inquiryId}`)
      .then((res) => {
        setInquiryDetail(res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [setInquiryDetail, inquiryId]);

  return inpuiryDetail;
};

export default useFetchInquiryDetail;
