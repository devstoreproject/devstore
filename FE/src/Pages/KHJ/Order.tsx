import PageHistory from 'Components/KHJ/Common/PageHistory';
import OrderList from 'Components/KHJ/Order/OrderList';

export default function Order() {
  return (
    <main>
      <PageHistory pageName="주문내역" pageHistory="주문내역" />
      <OrderList />
    </main>
  );
}
