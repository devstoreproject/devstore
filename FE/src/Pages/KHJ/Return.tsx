import ButtonBasic from 'Components/KHJ/Common/ButtonBasic';
import PageHistory from 'Components/KHJ/Common/PageHistory';
import PayReturn from 'Components/KHJ/Order/PayReturn';
import ReturnReason from 'Components/KHJ/Order/ReturnReason';
import ReturnWay from 'Components/KHJ/Order/ReturnWay';
import DeliverInfo from 'Components/KHJ/Purchase/DeliverInfo';
import PurchaseList from 'Components/KHJ/Purchase/PurchaseList';
import { Link } from 'react-router-dom';

export default function Return() {
  return (
    <main>
      <PageHistory
        pageName="반품요청내역"
        pageHistory="주문내역 ∙ 반품요청내역"
      />
      <PurchaseList />
      <ReturnReason />
      <ReturnWay />
      <PayReturn />
      <DeliverInfo />
      <div className="flex justify-center gap-5 mt-16">
        <ButtonBasic buttonName="반품취소" buttonBlack={true} />
        <Link to="/order">
          <ButtonBasic buttonName="목록으로" buttonBlack={false} />
        </Link>
      </div>
    </main>
  );
}
