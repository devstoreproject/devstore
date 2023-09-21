import { BsBoxSeam } from 'react-icons/bs';

export default function DeliverInfo() {
  return (
    <section className="mx-5 py-10 border-b border-gray-300 flex justify-between">
      <h2>배송정보</h2>
      <button className="flex items-center">
        <span className="mr-7 font-black">
          <BsBoxSeam />
        </span>
        운송장번호 123456781234 &gt;
      </button>
    </section>
  );
}
