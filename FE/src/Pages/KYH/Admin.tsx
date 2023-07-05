import { Route, Routes } from 'react-router-dom';
import ProductList from 'Components/KYH/admin/ProductList/ProductList';
import ReturnOrExchange from 'Components/KYH/admin/ReturnOrExchange/ReturnOrExchange';
import ProductInquiry from 'Components/KYH/admin/ProductInquiry/ProductInquiry';
import AdminNav from 'Components/KYH/admin/AdminNav';

export default function Admin() {
  return (
    <div className="flex w-screen min-h-screen bg-light-gray">
      <AdminNav />
      <div className="w-5/6 pt-16 pl-112">
        <Routes>
          <Route path="/productlist" element={<ProductList />} />
          <Route path="/returnorexchange" element={<ReturnOrExchange />} />
          <Route path="/productinquiry" element={<ProductInquiry />} />
        </Routes>
      </div>
    </div>
  );
}
