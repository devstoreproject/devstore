import { useNavigate } from 'react-router-dom';

export default function SearchPasswordNavBtn() {
  const navigate = useNavigate();
  return (
    <input
      type="button"
      value="비밀번호 찾기"
      className="ml-20 cursor-pointer hover:text-blue-500"
      onClick={() => {
        navigate('/searchpassword');
      }}
    />
  );
}
