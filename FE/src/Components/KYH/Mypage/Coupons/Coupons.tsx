import Coupon from './Coupon';

export default function Coupons() {
  return (
    <div className="flex flex-col">
      <span className="mb-4 font-bold">쿠폰 목록</span>
      <ul className="flex flex-wrap">
        <Coupon />
        <Coupon />
        <Coupon />
        <Coupon />
        <Coupon />
        <Coupon />
      </ul>
    </div>
  );
}
