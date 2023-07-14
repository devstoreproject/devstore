import ResultTable from 'Components/CYW/OrderedList/ResultTable';
import SearchTable from '../../Components/CYW/OrderedList/SearchTable';
import { BsBoxSeam } from 'react-icons/bs';
import PaginationContainer from 'Components/CYW/OrderedList/PaginationContainer';

export default function OrderedList() {
  return (
    <div className="flex flex-col w-full bg-light-gray">
      <div className="flex items-center mb-6">
        <BsBoxSeam size={25} />
        <h1 className="pl-2 font-bold text-xl">주문 된 상품 내역</h1>
      </div>
      <div>
        <SearchTable />
        <ResultTable />
      </div>
      <div className="flex justify-center w-300">
        <PaginationContainer />
      </div>
    </div>
  );
}

// "flex flex-col w-full bg-light-gray"
