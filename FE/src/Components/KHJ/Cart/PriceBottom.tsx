import cart from 'Dummy/Cart';

export default function PriceBottom() {
  return (
    <div className="fixed bottom-0 left-0 w-full h-14 bg-white flex justify-end items-center shadow-contentsBoxYminus px-5 box-border">
      <p className="mr-6">{cart.length}개</p>
      <span className="w-px text-tab-gray">|</span>
      <h2 className="ml-6 flex items-center">
        총합<strong className="text-2xl ml-3">398,000</strong>
      </h2>
    </div>
  );
}
