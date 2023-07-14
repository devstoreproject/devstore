export default function OptionChoice() {
  return (
    <div className="pt-5">
      <p>옵션</p>
      <div className="flex flex-col pt-4">
        <div className="flex flex-col border-box border-2 rounded-full items-center py-3">
          <select className="text-slate-600">
            <option value="color">색상 선택</option>
          </select>
        </div>
        <div className="flex flex-col border-box border-2 rounded-full items-center py-3 mt-2">
          <select className="text-slate-600">
            <option value="inch">크기 선택</option>
          </select>
        </div>
      </div>
    </div>
  );
}
