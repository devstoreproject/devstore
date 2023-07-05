import { Outlet } from 'react-router-dom';
import AdminNav from 'Components/KYH/admin/AdminNav';

export default function Admin() {
  return (
    <div className="flex w-screen min-h-screen bg-light-gray">
      <AdminNav />
      <div className="w-5/6 pt-16 pl-104">
        <Outlet />
      </div>
    </div>
  );
}
