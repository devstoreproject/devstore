import OrderListTableContents from './OrderListTableContents';
import OrderListTableTitle from './OrderListTableTitle';

export default function OrderListTable() {
  return (
    <div>
      <OrderListTableTitle />
      <OrderListTableContents />
      <OrderListTableContents />
      <OrderListTableContents />
    </div>
  );
}
