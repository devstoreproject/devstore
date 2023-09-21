import SignUpForm from 'Components/KYH/SignUp/SignUpForm';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

export default function SignUp() {
  const navigate = useNavigate();
  const isLogin = localStorage.getItem('authorization');

  useEffect(() => {
    if (isLogin !== null) {
      navigate('/');
    }
  }, [isLogin]);
  return (
    <div className="flex justify-center h-screen bg-light-gray">
      <SignUpForm />
    </div>
  );
}
