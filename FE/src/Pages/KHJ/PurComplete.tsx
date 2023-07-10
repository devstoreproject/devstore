import PageHistory from 'Components/KHJ/Common/PageHistory';
import Complete from 'Components/KHJ/Purchase/Complete';
import Delivery from 'Components/KHJ/Purchase/Delivery';
import PurchaseList from 'Components/KHJ/Purchase/PurchaseList';
import TotalPrice from 'Components/KHJ/Purchase/TotalPrice';

export default function PurComplete() {
  return (
    <main>
      <PageHistory />
      <Complete />
      <PurchaseList />
      <Delivery />
      <TotalPrice />
    </main>
  );
}
