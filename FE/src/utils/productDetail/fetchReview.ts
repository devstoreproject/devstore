import api from 'api';
import type { ReviewContentType } from 'Components/CYW/ProductDetail/Tab/Tab';

const fetchReview = (
  id: string,
  setReview: React.Dispatch<React.SetStateAction<ReviewContentType[] | null>>,
  page: number,
  setReviewTotalPage: React.Dispatch<React.SetStateAction<number>>
): void => {
  api
    .get(`api/items/${id}/reviews?page=${page}&size=10`)
    .then((res) => {
      setReview(res.data.data.content);
      setReviewTotalPage(res.data.data.totalPages);
    })
    .catch((err) => {
      console.log(err);
    });
};

export default fetchReview;
