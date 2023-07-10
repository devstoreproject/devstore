import cart from 'Dummy/Cart';

export default function CartItem() {
  return (
    <ul className="-mb-10 ">
      {cart.map((item) => (
        <li key={item.key} className="flex items-center justify-between mb-10">
          <input type="checkbox" className="w-5 h-5 border-gray-300"></input>
          <div className="rounded-xl w-36 h-36 bg-white"></div>
          <div>
            <h3>{item.text}</h3>
            <div className="text-subtitle-gray">
              <span className="mr-4">색상 : {item.color}</span>
              <span>너비 : {item.option}</span>
            </div>
          </div>
          <div className="flex">
            <button className="w-7 h-7 rounded-full border border-gray-300">
              -
            </button>
            <p className="mx-6">1</p>
            <button className="w-7 h-7 rounded-full border border-gray-300">
              +
            </button>
            <button className="px-4 h-7 rounded-full border ml-6 border-gray-300">
              옵션변경
            </button>
          </div>
          <div className="relative">
            <span className="absolute bottom-3/4 text-subtitle-gray right-0">
              쿠폰적용가
            </span>
            <del className="text-subtitle-gray">416,000</del>
            <strong className="text-xl font-normal">398,000</strong>
          </div>
        </li>
      ))}
    </ul>
  );
}
