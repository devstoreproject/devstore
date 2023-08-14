import { BrowserRouter, Routes, Route } from 'react-router-dom';

import Main from 'Pages/LSM/Main';
import SignIn from 'Pages/KYH/SignIn';
import SignUp from 'Pages/KYH/SignUp';
import SleeperAccount from 'Pages/KYH/SleeperAccount';
import Mypage from 'Pages/KYH/Mypage';
import OrderList from 'Components/KYH/Mypage/OrderList/OrderList';
import UserInfo from 'Components/KYH/Mypage/UserInfo/UserInfo';
import Bookmarks from 'Components/KYH/Mypage/Bookmarks/Bookmarks';
import Reviews from 'Components/KYH/Mypage/Reviews/Reviews';
import Coupons from 'Components/KYH/Mypage/Coupons/Coupons';
import Subscription from 'Components/KYH/Mypage/Subscription/Subscription';
import ProductList from 'Components/KYH/admin/ProductList/ProductList';
import ReturnOrExchange from 'Components/KYH/admin/ReturnOrExchange/ReturnOrExchange';
import ProductInquiry from 'Components/KYH/admin/ProductInquiry/ProductInquiry';
import Admin from 'Pages/KYH/Admin';
import NoticeList from 'Pages/LSM/Notice/NoticeList';
import NoticePost from 'Pages/LSM/Notice/NoticePost';
import NoticeEdit from 'Pages/LSM/Notice/NoticeEdit';
import NoticeDetail from 'Pages/LSM/Notice/NoticeDetail';
import ProductPost from 'Pages/LSM/Product/ProductPost';
import ProductEdit from 'Pages/LSM/Product/ProductEdit';
import Cart from 'Pages/KHJ/Cart';
import CheckList from 'Pages/KHJ/CheckList';
import PurchaseMain from 'Pages/KHJ/PurchaseMain';
import Purchase from 'Pages/KHJ/Purchase';
import PurComplete from 'Pages/KHJ/PurComplete';
import PurComDetail from 'Pages/KHJ/PurComDetail';
import OrderMain from 'Pages/KHJ/OrderMain';
import Order from 'Pages/KHJ/Order';
import AdminMain from 'Components/KYH/admin/AdminMain/AdminMain';
import BasicLayout from 'Pages/LSM/BasicLayout';
import Return from 'Pages/KHJ/Return';
import Exchange from 'Pages/KHJ/Exchange';
import Cancel from 'Pages/KHJ/Cancel';
import ProductDetail from 'Pages/CYW/ProductDetail';
import OrderedList from 'Pages/CYW/OrderedList';
import ReviewManagement from 'Pages/CYW/ReviewManagement';
import Search from 'Pages/CYW/Search';
import SearchCategory from 'Pages/CYW/SearchCategory';
import Sales from 'Components/KYH/admin/Sales/Sales';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<BasicLayout />}>
          <Route path="/" element={<Main />} />
          <Route path="searchcategory" element={<SearchCategory />} />
          <Route path="search" element={<Search />} />
          <Route path="searchcategory/:id" element={<ProductDetail />} />
        </Route>

        <Route path="/signup" element={<SignUp />} />
        <Route path="/signin" element={<SignIn />} />
        <Route path="/sleeperaccount" element={<SleeperAccount />} />
        <Route path="/notice" element={<NoticeList />} />
        <Route path="/notice/post" element={<NoticePost />} />
        <Route path="/notice/edit" element={<NoticeEdit />} />
        <Route path="/notice/:id" element={<NoticeDetail />} />

        <Route path="/mypage" element={<Mypage />}>
          <Route path="orderlist" element={<OrderList />} />
          <Route path="userinfo" element={<UserInfo />} />
          <Route path="bookmarks" element={<Bookmarks />} />
          <Route path="reviews" element={<Reviews />} />
          <Route path="coupons" element={<Coupons />} />
          <Route path="subscription" element={<Subscription />} />
        </Route>

        <Route path="/admin" element={<Admin />}>
          <Route path="" element={<AdminMain />} />
          <Route path="productlist" element={<ProductList />} />
          <Route path="returnorexchange" element={<ReturnOrExchange />} />
          <Route path="productinquiry" element={<ProductInquiry />} />
          <Route path="productlist/post" element={<ProductPost />} />
          <Route path="productlist/edit" element={<ProductEdit />} />
          <Route path="reviewmanagement" element={<ReviewManagement />} />
          <Route path="orderedlist" element={<OrderedList />} />
          <Route path="sales" element={<Sales />} />
        </Route>
        <Route path="/cart" element={<Cart />} />
        <Route path="/checklist" element={<CheckList />} />
        <Route path="/purchase" element={<PurchaseMain />}>
          <Route path="" element={<Purchase />} />
          <Route path="complete" element={<PurComplete />} />
          <Route path="detail" element={<PurComDetail />} />
        </Route>
        <Route path="/order" element={<OrderMain />}>
          <Route path="" element={<Order />} />
          <Route path="return" element={<Return />} />
          <Route path="exchange" element={<Exchange />} />
          <Route path="cancel" element={<Cancel />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
