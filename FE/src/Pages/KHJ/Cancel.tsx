import ButtonBasic from 'Components/KHJ/Common/ButtonBasic';
import PageHistory from 'Components/KHJ/Common/PageHistory';
import PayReturn from 'Components/KHJ/Order/PayReturn';
import ReturnReason from 'Components/KHJ/Order/ReturnReason';
import PurchaseList from 'Components/KHJ/Purchase/PurchaseList';
import { Link } from 'react-router-dom';

export default function Cancel() {
  return (
    <main>
      <PageHistory
        pageName="구매취소내역"
        pageHistory="주문내역 ∙ 구매취소내역"
      />
      <PurchaseList />
      <ReturnReason />
      <PayReturn />
      <div className="text-center mt-16">
        <Link to="/order">
          <ButtonBasic buttonName="목록으로" buttonBlack={false} />
        </Link>
      </div>
    </main>
  );
}
