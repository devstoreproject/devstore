import { RxHamburgerMenu } from 'react-icons/rx';
import type { adminNavProps } from './AdminNav';
import { useNavigate } from 'react-router-dom';

export default function AdminNavHeader({ isFold, setIsFold }: adminNavProps) {
  const navigate = useNavigate();
  const btnHandler = () => {
    localStorage.setItem('fold', JSON.stringify(!isFold));
    setIsFold((prev) => !prev);
  };

  return (
    <div className="flex items-center h-20 pt-4 ml-4 text-2xl font-bold">
      <button
        className={`truncate ${isFold ? 'hidden' : 'mr-auto'}`}
        onClick={() => {
          navigate('/');
        }}
      >
        DEV SHOP
      </button>
      <button onClick={btnHandler}>
        <RxHamburgerMenu />
      </button>
    </div>
  );
}
