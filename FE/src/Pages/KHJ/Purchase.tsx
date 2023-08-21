import PageHistory from 'Components/KHJ/Common/PageHistory';
import Delivery from 'Components/KHJ/Purchase/Delivery';
import Discount from 'Components/KHJ/Purchase/Discount';
import PayProgress from 'Components/KHJ/Purchase/PayProgress';
import Payment from 'Components/KHJ/Purchase/Payment';
import PurchaseList from 'Components/KHJ/Purchase/PurchaseList';

export default function Purchase() {
  return (
    <main>
      <PageHistory pageName="결제진행" pageHistory="결제진행" />
      <PurchaseList />
      <Delivery />
      <Discount />
      <Payment />
      <PayProgress />
    </main>
  );
}
