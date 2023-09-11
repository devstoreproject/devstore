import PageHistory from 'Components/KHJ/Common/PageHistory';
import DeliveryReadonly from 'Components/KHJ/Purchase/DeliverReadonly';
import PurchaseItem from 'Components/KHJ/Purchase/PurchaseItem';
import api from 'api';
import { AiFillCreditCard } from 'react-icons/ai';
import { useParams } from 'react-router-dom';

function OrderComDetail() {
  const { orderIdGet } = useParams();
  const fetchOrderGet = () => {
    const orderId = Number(orderIdGet);
    api
      .get(`api/orders/${orderId}`)
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });
  };
  console.log(fetchOrderGet);
  const isCartInfo: any = {
    data: {
      cartId: 46,
      userId: 1,
      deliveryPrice: 3000,
      totalPrice: 20000100,
      discountedPrice: 20000100,
      itemList: [
        {
          itemId: 2,
          optionId: 3,
          count: 1,
          defaultPrice: 10000000,
          additionalPrice: 0,
          discountRate: 0,
          itemName: '매윽북2',
          optionName: '옵션이름 3',
          optionDetail: null,
          imageInfo: {
            imageId: 6,
            originalPath:
              'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSz7rUlbKGMcXKcNkWGw6CnN_CRBz1hYjrsKXFVio9s26u7nQEILnX8EGV8e5UEIdGdsI0&usqp=CAU',
            thumbnailPath:
              'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSz7rUlbKGMcXKcNkWGw6CnN_CRBz1hYjrsKXFVio9s26u7nQEILnX8EGV8e5UEIdGdsI0&usqp=CAU',
            title: '스크린샷 2023-03-30 오후 6.16.18.png',
            imageOrder: 1,
            representative: true,
          },
        },
        {
          itemId: 4,
          optionId: 8,
          count: 1,
          defaultPrice: 10000000,
          additionalPrice: 100,
          discountRate: 0,
          itemName: '매매매북4',
          optionName: '옵션이름 8',
          optionDetail: '맥북 pro',
          imageInfo: null,
        },
      ],
    },
    code: 'C200',
    message: '작업 완료',
  };
  const cost = [
    {
      info: '최종결제금액',
      cost: isCartInfo?.totalPrice,
    },
    {
      info: '배송비',
      cost: isCartInfo?.deliveryPrice,
    },
  ];
  return (
    <main>
      <PageHistory pageHistory="결제완료" />
      <div className="mx-5 mb-8 rounded-xl border border-gray-300 bg-gray-100 text-center py-16">
        <h1 className="text-3xl font-bold">구매가 완료되었습니다.</h1>
        <p>주문번호 </p>
      </div>
      <section className="mx-5 ㅡㅅ-5 border-b border-gray-300 pb-7">
        <table className="w-full text-center">
          <thead>
            <tr className="rounded-t-xl shadow-border h-14">
              <th>상품상세</th>
              <th>수량</th>
              <th>결제금액</th>
            </tr>
          </thead>
          <tbody>
            {isCartInfo !== undefined
              ? isCartInfo.itemList?.map((item: any) => (
                  <PurchaseItem key={item.optionId} item={item} />
                ))
              : null}
          </tbody>
        </table>
      </section>
      <DeliveryReadonly />
      <div className="flex justify-between gap-5">
        <h2 className="w-14 mt-7">결제</h2>
        <p className="flex items-center h-6 whitespace-nowrap mt-7">
          <AiFillCreditCard /> 카드 결제 일시불
        </p>
        <div className="mt-4 w-80">
          {cost.map((info) => (
            <p key={info.info} className="mt-3 flex justify-between last:mt-5">
              <span>{info.info}</span>
              <em>{info.cost}</em>
            </p>
          ))}
        </div>
      </div>
    </main>
  );
}

export default OrderComDetail;
