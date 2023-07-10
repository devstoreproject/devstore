import { BsBoxSeam } from 'react-icons/bs';
import Button from '../Purchase/Button';

export default function OrderList() {
  return (
    <section className="bg-gray-100 border border-gray-300">
      <ul className="-mb-10 mx-5">
        <li className="flex items-center justify-between mb-10">
          <input type="checkbox" className="w-5 h-5 border-gray-300"></input>
          <div className="rounded-xl w-36 h-36 bg-white"></div>
          <div>
            <h3>알파스캔 모니터</h3>
            <div className="text-subtitle-gray">
              <span className="mr-4">색상 : 화이트</span>
              <span>너비 : 24인치</span>
            </div>
          </div>
          <div className="flex">
            <button>
              <BsBoxSeam size={20} />
            </button>
          </div>
          <div className="relative">
            <p className="text-xl font-normal">398,000</p>
          </div>
          <div className="flex w-80 flex-wrap gap-2 grow-0 shrink">
            <Button />
          </div>
        </li>
      </ul>
    </section>
  );
}
