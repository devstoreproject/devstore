import type { CartItemList } from '../Type/CartTypes';

interface PropsType {
  item: CartItemList;
}

export default function PurchaseItem({ item }: PropsType) {
  const regexComma = /\B(?=(\d{3})+(?!\d))/g;
  // const priceComma = item.defaultPrice.toString().replace(regexComma, ',');
  const discount = item.defaultPrice * item.discountRate;
  const discountPrice = item.defaultPrice - discount;
  const discountPriceComma = discountPrice.toString().replace(regexComma, ',');
  return (
    <tr>
      <td className="flex items-center pt-5 last-of-type:pb-5">
        <div className="rounded-xl w-36 h-36 bg-white flex justify-center align-middle overflow-hidden">
          {item.imageInfo !== null && (
            <img
              src={item.imageInfo.thumbnailPath}
              alt={item.imageInfo.title}
              className="max-w-max"
            />
          )}
        </div>
        <div className="text-subtitle-gray ml-5 text-left">
          <p className="text-black">{item.itemName}</p>
          {item.optionName !== null && item.optionDetail !== null && (
            <span className="mr-4">
              {item.optionName} : {item.optionDetail}
            </span>
          )}
        </div>
      </td>
      <td>{item.count}</td>
      {/* <td>{discount}</td> */}
      <td>{discountPriceComma}</td>
    </tr>
  );
}
