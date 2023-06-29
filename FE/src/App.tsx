import { BrowserRouter, Routes, Route } from 'react-router-dom';

import SignIn from 'Pages/KYH/SignIn';
import SignUp from 'Pages/KYH/SignUp';
import SleeperAccount from 'Pages/KYH/SleeperAccount';
import Mypage from 'Pages/KYH/Mypage';
import ProductList from 'Components/KYH/ProductList/ProductList';
import ReturnOrExchange from 'Components/KYH/ReturnOrExchange/ReturnOrExchange';
import ProductInquiry from 'Components/KYH/ProductInquiry/ProductInquiry';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/signup" element={<SignUp />} />
        <Route path="/signin" element={<SignIn />} />
        <Route path="/sleeperaccount" element={<SleeperAccount />} />
        <Route path="/mypage/*" element={<Mypage />} />
        <Route path="/admin/productlist" element={<ProductList />} />
        <Route path="/admin/returnorexchange" element={<ReturnOrExchange />} />
        <Route path="/admin/productinquiry" element={<ProductInquiry />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
