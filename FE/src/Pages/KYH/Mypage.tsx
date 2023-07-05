import MainContainer from 'Components/KYH/Mypage/MainContainer';
import ProfileContainer from 'Components/KYH/Mypage/ProfileContainer';
import Title from 'Components/KYH/Mypage/Title';
import { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

export default function Mypage() {
  const location = useLocation();
  const pathname = location.pathname;
  const navigate = useNavigate();

  useEffect(() => {
    if (pathname === '/mypage') {
      navigate('/mypage/orderlist');
    }
  }, [pathname]);

  return (
    <div className="flex flex-col min-h-screen pt-24 bg-light-gray">
      <Title />
      <ProfileContainer />
      <MainContainer />
    </div>
  );
}
