import CartItem from './CartItem';
import cart from 'Dummy/Cart';

export default function CartMain() {
  return (
    <section className="mx-5">
      <input type="checkbox" className="w-5 h-5 ml-8 mb-4"></input>
      <div className="rounded-xl bg-gray-100 border-gray-300 border p-8">
        <ul>
          {cart.map((item) => (
            <CartItem item={item} key={item.key} />
          ))}
        </ul>
      </div>
      <div className="flex justify-between items-center mt-6">
        <div className="flex">
          <p>주문금액 416,000</p>
          <p className="ml-6">-</p>
          <p className="ml-6">할인 18,000</p>
          <p className="ml-6">배송비 0</p>
        </div>
        <div className="flex items-center">
          <p className="flex items-center">
            총합
            <strong className="ml-2 text-2xl">398,000</strong>
          </p>
          <button className="ml-4 w-60 h-14 bg-light-black rounded-full text-xl text-white">
            구매하기
          </button>
        </div>
      </div>
    </section>
  );
}
