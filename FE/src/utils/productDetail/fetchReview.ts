import api from 'api';
import type { ReviewContentType } from 'Components/CYW/ProductDetail/Tab/Tab';

const fetchReview = (
  id: string,
  setReview: React.Dispatch<React.SetStateAction<ReviewContentType[] | null>>
): void => {
  api
    .get(`api/items/${id}/reviews?page=0&size=20`)
    .then((res) => {
      setReview(res.data.data.content);
    })
    .catch((err) => {
      console.log(err);
    });
};

export default fetchReview;
