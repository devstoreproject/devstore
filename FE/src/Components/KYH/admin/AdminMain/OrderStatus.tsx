import orderStatusCount from 'utils/admin/order/orderStatusCount';

interface OwnProps {
  status: string;
  statusName: string;
  style: string;
}

export default function OrderStatus({ status, statusName, style }: OwnProps) {
  return (
    <div
      className={`flex flex-col items-center w-1/5 h-full bg-white rounded-lg shadow-signBox ${style}`}
    >
      <span className="mt-3 text-gray-500">{statusName}</span>
      <span className="mt-2 text-3xl">{orderStatusCount(status)}</span>
    </div>
  );
}
