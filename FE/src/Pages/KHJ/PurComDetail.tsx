import PageHistory from 'Components/KHJ/Common/PageHistory';
import OrderButton from 'Components/KHJ/Order/OrderButton';
import DeliverInfo from 'Components/KHJ/Purchase/DeliverInfo';
import DeliveryReadonly from 'Components/KHJ/Purchase/DeliverReadonly';
import PurchaseList from 'Components/KHJ/Purchase/PurchaseList';
import TotalPrice from 'Components/KHJ/Purchase/TotalPrice';

export default function PurComDetail() {
  return (
    <main>
      <PageHistory pageName="주문내역" pageHistory="주문내역" />
      <PurchaseList />
      <DeliveryReadonly />
      <DeliverInfo />
      <TotalPrice />
      <OrderButton />
    </main>
  );
}
