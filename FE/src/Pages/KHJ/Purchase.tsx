import PageHistory from 'Components/KHJ/Common/PageHistory';
import Delivery from 'Components/KHJ/Purchase/Delivery';
import Discount from 'Components/KHJ/Purchase/Discount';
import Payment from 'Components/KHJ/Purchase/Payment';
import PurchaseList from 'Components/KHJ/Purchase/PurchaseList';
import TotalPrice from 'Components/KHJ/Purchase/TotalPrice';

export default function Purchase() {
  return (
    <main>
      <PageHistory />
      <PurchaseList />
      <Delivery />
      <Discount />
      <Payment />
      <TotalPrice />
    </main>
  );
}
