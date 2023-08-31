import { useEffect, useState } from 'react';
import MainBestProduct from 'Components/LSM/Main/MainBestProduct';
import MainBestReview from 'Components/LSM/Main/MainBestReview';
import MainImage from 'Components/LSM/Main/MainImage';
import MainIntroduce from 'Components/LSM/Main/MainIntroduce';
import MainNewProduct from 'Components/LSM/Main/MainNewProduct';
import MainThemeProduct from 'Components/LSM/Main/MainThemeProduct';
import api from 'api';

export default function Main() {
  const [bestItemsData, setBestItemsData] = useState([]);
  const [newItemsData, setNewItemsData] = useState([]);
  const [reviewData, setReviewData] = useState([]);
  const [themeData, setThemeData] = useState([]);

  const fetchData = async () => {
    try {
      const bestItemRes = await api.get(
        '/api/items?page=0&size=10&sort=createdAt'
      );
      setBestItemsData(bestItemRes?.data?.data?.content);

      const newItemRes = await api.get(
        '/api/items?page=0&size=10&sort=createdAt'
      );
      setNewItemsData(newItemRes?.data?.data?.content);

      const reviewRes = await api.get('/api/reviews?page=0&size=10');
      setReviewData(reviewRes?.data?.data?.content);

      const themeRes = await api.get(
        '/api/items/search/category?category=COMPUTER&page=0&size=2'
      );
      setThemeData(themeRes?.data?.data?.content);
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    void fetchData();
    window.history.scrollRestoration = 'manual';
  }, []);

  return (
    <div className="w-full bg-light-gray selection:bg-neon-green">
      <MainImage />
      <div className="m-auto lg:max-w-365">
        <MainBestProduct bestItemsData={bestItemsData} />
        <MainNewProduct newItemsData={newItemsData} />
        <div className="w-full m-auto overflow-hidden">
          <MainBestReview reviewData={reviewData} />
        </div>
        <MainThemeProduct themeData={themeData} />
      </div>
      <MainIntroduce />
    </div>
  );
}
