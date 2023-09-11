export default function OptionTitle() {
  return (
    <div className="flex w-full h-10 font-bold bg-gray-200 border-b-2 border-gray-300">
      <div className="flex items-center justify-center border-r-2 border-gray-300 w-36">
        옵션명
      </div>
      <div className="flex items-center justify-center w-40 border-gray-300">
        옵션내용
      </div>
      <div className="flex items-center justify-center w-40 border-gray-300 border-x-2">
        옵션가격
      </div>
      <div className="flex items-center justify-center w-28">옵션수량</div>
    </div>
  );
}
