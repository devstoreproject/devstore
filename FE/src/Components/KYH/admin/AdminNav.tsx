import AdminNavHeader from './AdminNavHeader';
import HomeNavigate from './HomeNavigate';
import MenuNav from './MenuNav';

export interface adminNavProps {
  isFold: boolean;
  setIsFold: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function AdminNav({ isFold, setIsFold }: adminNavProps) {
  return (
    <div
      className={`fixed flex flex-col h-screen px-6 bg-white shadow-signBox transition-all duration-200 ease-linear ${
        isFold ? 'w-25' : 'w-80'
      }`}
    >
      <AdminNavHeader isFold={isFold} setIsFold={setIsFold} />
      <div className="w-full h-full border-t border-t-black">
        <HomeNavigate isFold={isFold} />
        <MenuNav isFold={isFold} />
      </div>
    </div>
  );
}
