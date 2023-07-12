import PageHistory from 'Components/KHJ/Common/PageHistory';
import OrderButton from 'Components/KHJ/Order/OrderButton';
import DeliverInfo from 'Components/KHJ/Purchase/DeliverInfo';
import Delivery from 'Components/KHJ/Purchase/Delivery';
import PurchaseList from 'Components/KHJ/Purchase/PurchaseList';
import TotalPrice from 'Components/KHJ/Purchase/TotalPrice';

export default function PurComDetail() {
  return (
    <main>
      <PageHistory />
      <PurchaseList />
      <Delivery />
      <DeliverInfo />
      <TotalPrice />
      <OrderButton />
    </main>
  );
}
