export default function OptionTitle() {
  return (
    <div className="flex w-full h-8 bg-gray-200 border-b-2 border-gray-300">
      <div className="flex items-center justify-center border-gray-300 w-36">
        옵션명
      </div>
      <div className="flex items-center justify-center w-32 border-gray-300 border-x-2">
        옵션가격
      </div>
      <div className="flex items-center justify-center w-28">옵션수량</div>
    </div>
  );
}