import { RxHamburgerMenu } from 'react-icons/rx';
import { type adminNavProps } from './AdminNav';

export default function AdminNavHeader({ isFold, setIsFold }: adminNavProps) {
  const btnHandler = () => {
    localStorage.setItem('fold', JSON.stringify(!isFold));
    setIsFold((prev) => !prev);
  };

  return (
    <div className="flex items-center h-20 pt-4 ml-4 text-2xl font-bold">
      <p className={`truncate ${isFold ? 'hidden' : 'mr-auto'}`}>DEV SHOP</p>
      <button onClick={btnHandler}>
        <RxHamburgerMenu />
      </button>
    </div>
  );
}
