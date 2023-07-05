import HomeNavigate from './HomeNavigate';
import MenuNav from './MenuNav';

export default function AdminNav() {
  return (
    <div className="fixed flex flex-col h-screen px-6 bg-white w-80 shadow-signBox">
      <span className="h-20 pt-4 ml-4 text-2xl font-bold">DEV SHOP</span>
      <div className="w-full h-full border-t border-t-black">
        <HomeNavigate />
        <MenuNav />
      </div>
    </div>
  );
}
