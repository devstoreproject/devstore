import MainContainer from 'Components/KYH/Mypage/MainContainer';
import ProfileContainer from 'Components/KYH/Mypage/ProfileContainer';
import Title from 'Components/KYH/Mypage/Title';
import { useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

export default function Mypage() {
  const params = useParams()['*'];
  const navigate = useNavigate();

  useEffect(() => {
    if (params === null || params === '') navigate('/mypage/orderlist');
  }, [params]);

  return (
    <div className="flex flex-col min-h-screen pt-24 bg-light-gray">
      <Title />
      <ProfileContainer />
      <MainContainer />
    </div>
  );
}
