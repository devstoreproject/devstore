export default function SalesChartHeader() {
  return (
    <div className="flex">
      <div className="ml-auto">
        <button className="w-20 h-8 border border-gray-500 rounded-l-lg">
          일별
        </button>
        <button className="w-20 h-8 border border-gray-500">월별</button>
        <button className="w-20 h-8 border border-gray-500 rounded-r-lg">
          년별
        </button>
      </div>
    </div>
  );
}
