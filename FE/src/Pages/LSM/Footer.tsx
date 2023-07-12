import FixedBottomBox from 'Components/LSM/Footer/FixedBottomBox';
import FooterNav from 'Components/LSM/Footer/FooterNav';
import { Outlet } from 'react-router-dom';

export default function Footer() {
  return (
    <>
      <Outlet />
      <div className="relative flex flex-col items-center w-full py-10 bg-light-gray">
        <FooterNav />
        <p className="my-4 text-sm">Copyrightⓒ2023</p>
        <FixedBottomBox />
      </div>
    </>
  );
}
