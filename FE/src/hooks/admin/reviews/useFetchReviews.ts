import api from 'api';
import type { Reviews } from 'model/review';
import { useEffect, useState } from 'react';

const useFetchReviews = () => {
  const userId = localStorage.getItem('userId');
  const [reviews, setReviews] = useState<Reviews[]>([]);

  useEffect(() => {
    if (userId !== null) {
      api
        .get(`/api/user/${userId}/reviews`)
        .then((res) => {
          setReviews(res.data.data.content);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, [setReviews, userId]);

  return reviews;
};

export default useFetchReviews;
