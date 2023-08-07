import { Outlet } from 'react-router-dom';
import AdminNav from 'Components/KYH/admin/AdminNav';
import { useState } from 'react';

export default function Admin() {
  const fold = localStorage.getItem('fold');
  const parseFold: boolean = fold === null ? false : JSON.parse(fold);
  const [isFold, setIsFold] = useState(parseFold);

  return (
    <div className="flex min-h-screen bg-light-gray">
      <AdminNav isFold={isFold} setIsFold={setIsFold} />
      <div
        className={`w-full pt-16 transition-all duration-200 ease-linear ${
          isFold ? 'pl-40' : 'pl-96'
        }`}
      >
        <Outlet />
      </div>
    </div>
  );
}
