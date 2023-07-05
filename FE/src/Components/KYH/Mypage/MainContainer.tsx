import { Outlet } from 'react-router-dom';
import MainNav from 'Components/KYH/Mypage/MainNav';

export default function MainContainer() {
  return (
    <div className="flex ml-28">
      <MainNav />
      <div className="w-3/5">
        <Outlet />
      </div>
    </div>
  );
}
