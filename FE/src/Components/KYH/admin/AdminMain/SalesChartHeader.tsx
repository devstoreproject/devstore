export default function SalesChartHeader() {
  return (
    <div className="flex">
      <span className="text-lg font-bold">매출내역</span>
      <div className="ml-auto">
        <button className="w-20 h-8 border border-gray-500 rounded-l-lg">
          일별
        </button>
        <button className="w-20 h-8 border border-gray-500 rounded-r-lg">
          월별
        </button>
      </div>
    </div>
  );
}
