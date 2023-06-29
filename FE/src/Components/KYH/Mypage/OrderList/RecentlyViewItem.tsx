export default function RecentlyViewItem() {
  return (
    <li className="mr-4">
      <span className="text-xs text-gray-500">06.27</span>
      <div className="flex items-center h-32 p-4 bg-gray-100 border border-gray-400 rounded-lg w-96">
        <img className="h-20 bg-gray-300 rounded-lg w-28" />
        <div className="flex flex-col ml-4">
          <span className="mb-2 text-sm">Razer Viper V2 PRO Black</span>
          <span className="text-sm text-gray-500">193,280원</span>
        </div>
      </div>
    </li>
  );
}
