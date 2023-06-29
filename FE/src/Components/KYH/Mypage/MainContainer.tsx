import { Routes, Route } from 'react-router-dom';

import MainNav from 'Components/KYH/Mypage/MainNav';
import OrderList from './OrderList/OrderList';
import UserInfo from './UserInfo/UserInfo';
import Bookmarks from './Bookmarks/Bookmarks';
import Reviews from './Reviews/Reviews';
import Coupons from './Coupons/Coupons';
import Subscription from './Subscription/Subscription';

export default function MainContainer() {
  return (
    <div className="flex ml-28">
      <MainNav />
      <div className="w-3/5">
        <Routes>
          <Route path="/orderlist" element={<OrderList />} />
          <Route path="/userinfo" element={<UserInfo />} />
          <Route path="/bookmarks" element={<Bookmarks />} />
          <Route path="/reviews" element={<Reviews />} />
          <Route path="/coupons" element={<Coupons />} />
          <Route path="/subscription" element={<Subscription />} />
        </Routes>
      </div>
    </div>
  );
}
