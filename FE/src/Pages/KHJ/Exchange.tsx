import ButtonBasic from 'Components/KHJ/Common/ButtonBasic';
import PageHistory from 'Components/KHJ/Common/PageHistory';
import ExchangeDetail from 'Components/KHJ/Order/ExchangeDetail';
// import DeliveryReadonly from 'Components/KHJ/Purchase/DeliverReadonly';
import PurchaseList from 'Components/KHJ/Purchase/PurchaseList';
import { Link } from 'react-router-dom';

export default function Exchange() {
  return (
    <main>
      <PageHistory
        pageName="교환요청내역"
        pageHistory="주문내역 ∙ 교환요청내역"
      />
      <PurchaseList />
      {/* <DeliveryReadonly /> */}
      <ExchangeDetail />
      <div className="flex justify-center gap-5 mt-16">
        <ButtonBasic buttonName="교환취소" buttonBlack={true} />
        <Link to="/order">
          <ButtonBasic buttonName="목록으로" buttonBlack={false} />
        </Link>
      </div>
    </main>
  );
}
