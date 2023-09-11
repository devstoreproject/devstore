import PurchaseItem from './PurchaseItem';
import type { cartInfoType } from '../Type/CartTypes';

interface purchaseProps {
  isCartInfo: cartInfoType;
}

const PurchaseListProps: React.FC<purchaseProps> = (isCartInfo) => {
  if (isCartInfo !== undefined) {
    return (
      <section className="mx-5 border-b border-gray-300 pb-7">
        <table className="w-full text-center">
          <thead>
            <tr className="rounded-t-xl shadow-border h-14">
              <th>상품상세</th>
              <th>수량</th>
              <th>결제금액</th>
            </tr>
          </thead>
          <tbody>
            {isCartInfo !== undefined
              ? isCartInfo?.isCartInfo.itemList?.map((item) => (
                  <PurchaseItem key={item.optionId} item={item} />
                ))
              : null}
          </tbody>
        </table>
      </section>
    );
  } else {
    return (
      <section className="mx-5 border-b border-gray-300 pb-7">
        <table className="w-full text-center">
          <thead>
            <tr className="rounded-t-xl shadow-border h-14">
              <th>상품상세</th>
              <th>수량</th>
              <th>할인</th>
              <th>결제금액</th>
            </tr>
          </thead>
          <tbody></tbody>
        </table>
      </section>
    );
  }
};

export default PurchaseListProps;
