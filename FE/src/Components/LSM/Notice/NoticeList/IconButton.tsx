import { FaPlus } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';

export default function IconButton() {
  const navigate = useNavigate();

  const postHandler = () => {
    navigate('/admin/notice/post', { replace: true });
  };
  return (
    <button className="ml-4 text-sm text-subtitle-gray" onClick={postHandler}>
      <FaPlus />
    </button>
  );
}
