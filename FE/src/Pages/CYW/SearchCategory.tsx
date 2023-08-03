import CategoryResult from 'Components/CYW/SearchCategory/CategoryResult';

export default function SearchCategory() {
  return (
    <div className="flex flex-col pt-10">
      <p className="text-slate-600 pl-136">{`홈 > 카테고리 > 모니터`}</p>
      <div className="flex flex-col items-center">
        <h1 className="flex justify-center text-3xl font-bold pt-10">모니터</h1>
        <CategoryResult />
      </div>
    </div>
  );
}
