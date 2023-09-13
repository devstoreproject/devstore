interface OwnProps {
  salesStatus: boolean[];
  setSalesStatus: React.Dispatch<React.SetStateAction<boolean[]>>;
}
export default function SalesChartHeader({
  salesStatus,
  setSalesStatus,
}: OwnProps) {
  return (
    <div className="flex">
      <span className="text-lg font-bold">매출내역</span>
      <div className="flex flex-col ml-auto">
        <div>
          <button
            className={`w-20 h-8 border rounded-l-lg ${
              salesStatus[0]
                ? 'bg-gray-700 text-white border-black'
                : 'border-gray-700'
            }`}
            onClick={() => {
              setSalesStatus([true, false]);
            }}
          >
            일별
          </button>
          <button
            className={`w-20 h-8 border rounded-r-lg  ${
              salesStatus[1]
                ? 'bg-gray-700 text-white border-gray-700'
                : 'border-gray-500'
            }`}
            onClick={() => {
              setSalesStatus([false, true]);
            }}
          >
            월별
          </button>
        </div>
        <span className="flex justify-end mt-2 mr-1 text-sm">
          (단위 : 천원)
        </span>
      </div>
    </div>
  );
}
