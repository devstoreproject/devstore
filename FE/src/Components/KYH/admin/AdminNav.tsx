import AdminNavHeader from './AdminNavHeader';
import HomeNavigate from './HomeNavigate';
import MenuNav from './MenuNav';

export interface adminNavProps {
  fold: boolean;
  setFold: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function AdminNav({ fold, setFold }: adminNavProps) {
  return (
    <div
      className={`fixed flex flex-col h-screen px-6 bg-white shadow-signBox transition-all duration-200 ease-linear ${
        fold ? 'w-25' : 'w-80'
      }`}
    >
      <AdminNavHeader setFold={setFold} fold={fold} />
      <div className="w-full h-full border-t border-t-black">
        <HomeNavigate fold={fold} />
        <MenuNav fold={fold} />
      </div>
    </div>
  );
}
