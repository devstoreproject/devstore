import PageHistory from 'Components/KHJ/Common/PageHistory';
import OrderItem from 'Components/KHJ/Order/OrderItem';
import DeliveryReadonly from 'Components/KHJ/Purchase/DeliverReadonly';
import type { orderListType } from 'Components/KHJ/Type/OrderItemTypes';
import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import api from 'api';
// import { AiFillCreditCard } from 'react-icons/ai';

function OrderCom() {
  const { id } = useParams();
  const orderId = Number(id);
  const [isOrderPage, setIsOrderPage] = useState<orderListType>();
  const fetchOrderGet = (orderId: number) => {
    api
      .get(`api/orders/${orderId}`)
      .then((res) => {
        const data = res.data;
        setIsOrderPage(data);
      })
      .catch((err) => {
        console.log(err);
      });
  };
  useEffect(() => {
    fetchOrderGet(orderId);
  }, [orderId]);
  const cost = [
    {
      info: '배송비',
      cost: isOrderPage?.data.deliveryPrice,
    },
    {
      info: '최종결제금액',
      cost: isOrderPage?.data.discountedPrice,
    },
  ];
  if (isOrderPage === undefined) {
    return (
      <main>
        <div className="mx-5 mb-8 rounded-xl border border-gray-300 bg-gray-100 text-center py-16">
          <h1 className="text-3xl font-bold">구매가 완료되었습니다.</h1>
          <p>주문번호 </p>
        </div>
        <section className="mx-5 ㅡㅅ-5 border-b border-gray-300">
          <table className="w-full text-center">
            <thead>
              <tr className="rounded-t-xl shadow-border h-14">
                <th>상품상세</th>
                <th>수량</th>
                <th>결제금액</th>
              </tr>
            </thead>
            <tbody>
              <tr></tr>
            </tbody>
          </table>
        </section>
        <div className="flex justify-between gap-5 mx-5 border-b border-gray-300 pb-8">
          <h2 className="w-14 mt-7">결제</h2>
          <div className="mt-4 w-80"></div>
        </div>
      </main>
    );
  } else {
    return (
      <main>
        <PageHistory pageHistory="결제완료" />
        <div className="mx-5 mb-8 rounded-xl border border-gray-300 bg-gray-100 text-center py-16">
          <h1 className="text-3xl font-bold">구매가 완료되었습니다.</h1>
          <p>주문번호 {isOrderPage.data.orderNumber}</p>
        </div>
        <section className="mx-5 ㅡㅅ-5 border-b border-gray-300">
          <table className="w-full text-center">
            <thead>
              <tr className="rounded-t-xl shadow-border h-14">
                <th>상품상세</th>
                <th>수량</th>
                <th>결제금액</th>
              </tr>
            </thead>
            <tbody>
              {isOrderPage !== undefined
                ? isOrderPage.data.orderItemList?.map((item: any) => (
                    <OrderItem key={item.optionId} item={item} />
                  ))
                : null}
            </tbody>
          </table>
        </section>
        <DeliveryReadonly isShippingFormWrite={isOrderPage.data.addressInfo} />
        <div className="flex justify-between gap-5 mx-5 border-b border-gray-300 pb-8">
          <h2 className="w-14 mt-7">결제</h2>
          {/* <p className="flex items-center h-6 whitespace-nowrap mt-7">
          <AiFillCreditCard /> 카드 결제 일시불
        </p> */}
          <div className="mt-4 w-80">
            {cost.map((info) => (
              <p
                key={info.info}
                className="mt-3 flex justify-between last:mt-5"
              >
                <span>{info.info}</span>
                <em>{info.cost}</em>
              </p>
            ))}
          </div>
        </div>
      </main>
    );
  }
}

export default OrderCom;
