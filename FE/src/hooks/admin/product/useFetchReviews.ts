import api from 'api';
import { useEffect, useState } from 'react';

const useFetchReviews = () => {
  const userId = localStorage.getItem('userId');
  const [reviews, setReviews] = useState([]);

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
