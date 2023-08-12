import api from 'api';
import type { InquiryContentType } from 'Components/CYW/ProductDetail/Tab/Tab';

const fetchInquiry = (
  id: string,
  setInquiry: React.Dispatch<React.SetStateAction<InquiryContentType[] | null>>
): void => {
  api
    .get(`api/qna/items/${id}?page=0&size=10`)
    .then((res) => {
      setInquiry(res.data.data.content);
    })
    .catch((err) => {
      console.log(err);
    });
};

export default fetchInquiry;
