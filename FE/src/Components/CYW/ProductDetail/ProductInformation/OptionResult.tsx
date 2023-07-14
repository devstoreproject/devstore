import { CiCirclePlus, CiCircleMinus } from 'react-icons/ci';
import { RxCross2 } from 'react-icons/rx';

export default function OptionResult() {
  return (
    <div className="pt-5">
      <div className="border-box border-2 rounded-[20px] pt-3 pl-5 py-2 bg-slate-50">
        <div className="flex items-center">
          <p>알파스캔 AOC Q32V3S QHD IPS 75 시력보호 무결점</p>
          <div className="ml-auto pr-4">
            <RxCross2 size={18} />
          </div>
        </div>
        <div className="flex pt-1">
          <span className="font-light">색상: 화이트</span>
          <span className="font-light pl-2">너비: 24인치</span>
        </div>
        <div className="flex items-center pt-2">
          <CiCircleMinus size={24} />
          <p className="mx-4">1</p>
          <CiCirclePlus size={24} />
          <span className="ml-auto mr-4">398,000</span>
        </div>
      </div>
    </div>
  );
}
