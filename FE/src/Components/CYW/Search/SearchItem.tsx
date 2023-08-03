export default function SearchItem() {
  return (
    <div className="flex flex-col py-6 px-6 bg-white w-72 rounded-xl shadow-md">
      <img
        src="https://item.sellpro.co.kr/data/item/125/5565685125_550.jpg"
        className="w-68 h-40 object-cover border rounded-xl mb-4"
      />
      <p>알파스캔 AOC Q32V3S QHD IPS 75 시력보호 무결점</p>
      <div>
        <span className="text-lg">398,000</span>
        <span className="pl-2 line-through text-slate-500">443,000</span>
        <span className="pl-2 text-slate-500">10%</span>
      </div>
      <p className="text-slate-500">무료배송</p>
    </div>
  );
}
