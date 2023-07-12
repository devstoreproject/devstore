import { RxHamburgerMenu } from 'react-icons/rx';
import { type adminNavProps } from './AdminNav';

export default function AdminNavHeader({ setFold, fold }: adminNavProps) {
  const btnHandler = () => {
    setFold((prev) => !prev);
  };

  return (
    <div className="flex items-center h-20 pt-4 ml-4 text-2xl font-bold">
      <p className={`truncate ${fold ? 'hidden' : 'mr-auto'}`}>DEV SHOP</p>
      <button onClick={btnHandler}>
        <RxHamburgerMenu />
      </button>
    </div>
  );
}
