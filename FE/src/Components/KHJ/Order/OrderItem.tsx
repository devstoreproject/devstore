import type { orderItemType } from '../Type/OrderItemTypes';

interface PropsType {
  item: orderItemType;
}

export default function OrderItem({ item }: PropsType) {
  const regexComma = /\B(?=(\d{3})+(?!\d))/g;
  // const priceComma = item.defaultPrice.toString().replace(regexComma, ',');
  const discountPriceComma = item.discountPrice
    .toString()
    .replace(regexComma, ',');
  return (
    <tr>
      <td className="py-4 text-center">{item.itemName}</td>
      <td>{item.itemCount}</td>
      <td>{discountPriceComma}</td>
    </tr>
  );
}
