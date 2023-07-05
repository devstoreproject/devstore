// import { BsChevronDown } from 'react-icons/bs';

export default function Select() {
  //   const classname = '';
  return (
    <div className="relative">
      <select
        name="notice"
        className="px-2 py-1.5 border rounded-lg border-gray"
        defaultValue={'none'}
      >
        <option value="none" disabled>
          카테고리 선택
        </option>
        <option value="policy">운영정책</option>
        <option value="update">업데이트</option>
        <option value="event">이벤트</option>
      </select>
      {/* <span className="absolute z-10 flex items-center justify-center text-gray-400 t-0 r-0">
        <BsChevronDown />
      </span> */}
    </div>
  );
}
