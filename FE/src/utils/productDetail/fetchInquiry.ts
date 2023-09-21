import api from 'api';
import type { InquiryContentType } from 'Components/CYW/ProductDetail/Tab/Tab';

const fetchInquiry = (
  id: string,
  setInquiry: React.Dispatch<React.SetStateAction<InquiryContentType[] | null>>,
  inquiryPage: number,
  setInquiryTotalPage: React.Dispatch<React.SetStateAction<number>>
): void => {
  api
    .get(`api/qna/items/${id}?page=${inquiryPage}&size=10`)
    .then((res) => {
      setInquiry(res.data.data.content);
      setInquiryTotalPage(res.data.data.totalPages);
    })
    .catch((err) => {
      console.log(err);
    });
};

export default fetchInquiry;
