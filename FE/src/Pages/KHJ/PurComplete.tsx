import PageHistory from 'Components/KHJ/Common/PageHistory';
import Complete from 'Components/KHJ/Purchase/Complete';
import DeliveryReadonly from 'Components/KHJ/Purchase/DeliverReadonly';
import PurchaseList from 'Components/KHJ/Purchase/PurchaseList';
import TotalPrice from 'Components/KHJ/Purchase/TotalPrice';

export default function PurComplete() {
  return (
    <main>
      <PageHistory pageHistory="결제완료" />
      <Complete />
      <PurchaseList />
      <DeliveryReadonly />
      <TotalPrice />
    </main>
  );
}
