export default function RecentlyViewItem() {
  return (
    <li className="mr-4">
      <span className="text-xs text-gray-500">06.27</span>
      <div className="flex items-center p-4 bg-gray-100 border border-gray-400 rounded-lg h-28 w-80">
        <img className="w-24 h-16 bg-gray-300 rounded-lg" />
        <div className="flex flex-col ml-4">
          <span className="mb-2 text-sm">Razer Viper V2 PRO Black</span>
          <span className="text-sm text-gray-500">193,280원</span>
        </div>
      </div>
    </li>
  );
}
