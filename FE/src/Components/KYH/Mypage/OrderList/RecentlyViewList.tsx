import RecentlyViewItem from './RecentlyViewItem';

export default function RecentlyViewList() {
  return (
    <>
      <span className="mb-4 font-bold">최근 본 상품</span>
      <ul className="flex p-2 overflow-y-scroll bg-white border border-gray-300 rounded-lg scrollbar-hide">
        <RecentlyViewItem />
        <RecentlyViewItem />
        <RecentlyViewItem />
      </ul>
    </>
  );
}
