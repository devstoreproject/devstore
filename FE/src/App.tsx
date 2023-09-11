import { BrowserRouter, Routes, Route } from 'react-router-dom';

import Main from 'Pages/LSM/Main';
import SignIn from 'Pages/KYH/SignIn';
import SignUp from 'Pages/KYH/SignUp';
import SleeperAccount from 'Pages/KYH/SleeperAccount';
import Mypage from 'Pages/KYH/Mypage';
import OrderList from 'Components/KYH/Mypage/OrderList/OrderList';
import UserInfo from 'Components/KYH/Mypage/UserInfo/UserInfo';
import Bookmarks from 'Components/KYH/Mypage/Bookmarks/Bookmarks';
import ProductList from 'Components/KYH/admin/ProductList/ProductList';
import ProductInquiry from 'Components/KYH/admin/ProductInquiry/ProductInquiry';
import Admin from 'Pages/KYH/Admin';
import NoticeList from 'Pages/LSM/Notice/NoticeList';
import NoticePost from 'Pages/LSM/Notice/NoticePost';
import NoticeEdit from 'Pages/LSM/Notice/NoticeEdit';
import NoticeDetail from 'Pages/LSM/Notice/NoticeDetail';
import ProductPost from 'Pages/LSM/Product/ProductPost';
import ProductEdit from 'Pages/LSM/Product/ProductEdit';
import Cart from 'Pages/KHJ/Cart';
import PurchaseMain from 'Pages/KHJ/PurchaseMain';
import Purchase from 'Pages/KHJ/Purchase';
import PurComplete from 'Pages/KHJ/PurComplete';
import PurComDetail from 'Pages/KHJ/PurComDetail';
import AdminMain from 'Components/KYH/admin/AdminMain/AdminMain';
import BasicLayout from 'Pages/LSM/BasicLayout';
import ProductDetail from 'Pages/CYW/ProductDetail';
import OrderedList from 'Pages/CYW/OrderedList';
import ReviewManagement from 'Pages/CYW/ReviewManagement';
import Search from 'Pages/CYW/Search';
import Sales from 'Components/KYH/admin/Sales/Sales';
import Products from 'Pages/CYW/Products';
import SearchPassword from 'Pages/KYH/SearchPassword';
import OrderCom from 'Pages/KHJ/OrderCom';
import OrderComDetail from 'Pages/KHJ/OrderComDetail';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<BasicLayout />}>
          <Route path="/" element={<Main />} />
          <Route path="products" element={<Products />} />
          <Route path="search" element={<Search />} />
          <Route path="products/:id" element={<ProductDetail />} />
          <Route path="/notice" element={<NoticeList />} />
          <Route path="/notice/:id" element={<NoticeDetail />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/signin" element={<SignIn />} />
          <Route path="/searchpassword" element={<SearchPassword />} />
          <Route path="/sleeperaccount" element={<SleeperAccount />} />

          <Route path="/cart" element={<Cart />} />
          <Route path="/purchase" element={<PurchaseMain />}>
            <Route path="order" element={<Purchase />} />
            <Route path="complete" element={<PurComplete />} />
            <Route path="detail" element={<PurComDetail />} />
          </Route>
          <Route path="/complete/:id" element={<OrderCom />} />
          <Route path="/complete/detail/:id" element={<OrderComDetail />} />
        </Route>

        <Route path="/mypage" element={<Mypage />}>
          <Route path="orderlist" element={<OrderList />} />
          <Route path="userinfo" element={<UserInfo />} />
          <Route path="bookmarks" element={<Bookmarks />} />
        </Route>

        <Route path="/admin" element={<Admin />}>
          <Route path="" element={<AdminMain />} />
          <Route path="productlist" element={<ProductList />} />
          <Route path="productinquiry" element={<ProductInquiry />} />
          <Route path="productlist/post" element={<ProductPost />} />
          <Route path="productlist/edit" element={<ProductEdit />} />
          <Route path="reviewmanagement" element={<ReviewManagement />} />
          <Route path="orderedlist" element={<OrderedList />} />
          <Route path="sales" element={<Sales />} />
          <Route path="notice" element={<NoticeList />} />
          <Route path="notice/post" element={<NoticePost />} />
          <Route path="notice/edit/:id" element={<NoticeEdit />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
