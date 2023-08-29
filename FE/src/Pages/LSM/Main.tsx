// import { useEffect } from 'react';
import MainBestProduct from 'Components/LSM/Main/MainBestProduct';
import MainBestReview from 'Components/LSM/Main/MainBestReview';
import MainEvent from 'Components/LSM/Main/MainEvent';
import MainImage from 'Components/LSM/Main/MainImage';
import MainIntroduce from 'Components/LSM/Main/MainIntroduce';
import MainNewProduct from 'Components/LSM/Main/MainNewProduct';
import MainThemeProduct from 'Components/LSM/Main/MainThemeProduct';
// import api from 'api';

export default function Main() {
  return (
    <div className="w-full bg-light-gray selection:bg-neon-green">
      <MainImage />
      <div className="m-auto lg:max-w-365">
        <MainBestProduct />
        <MainNewProduct />
        <div className="w-full m-auto overflow-hidden">
          <MainBestReview />
          <MainEvent />
        </div>
        <MainThemeProduct />
      </div>
      <MainIntroduce />
      <div>배포자동화test입니다. 수정해보겠습니다.</div>
    </div>
  );
}
