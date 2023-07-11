import { useState } from 'react';

import { Outlet } from 'react-router-dom';
import AdminNav from 'Components/KYH/admin/AdminNav';

export default function Admin() {
  const [fold, setFold] = useState(false);

  return (
    <div className="flex min-h-screen bg-light-gray">
      <AdminNav fold={fold} setFold={setFold} />
      <div
        className={`w-full pt-16 transition-all duration-200 ease-linear ${
          fold ? 'pl-40' : 'pl-96'
        }`}
      >
        <Outlet />
      </div>
    </div>
  );
}
