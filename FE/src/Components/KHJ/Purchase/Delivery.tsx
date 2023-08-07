import DeliverForm from './DeliverForm';

const Delivery: React.FC = (props) => {
  return (
    <section className="mx-5 py-7 flex">
      <div className="w-36">
        <h2>배송지 정보 입력</h2>
        <p>*은 필수 입력입니다.</p>
      </div>
      <form className="flex">
        <div className="w-32">
          <p className="h-14 flex items-center">받는 이*</p>
          <p className="h-14 flex items-center mt-3">이메일*</p>
          <p className="h-14 flex items-center mt-3">휴대전화*</p>
          <p className="h-14 flex items-center mt-3">일반전화</p>
          <p className="h-14 flex items-center mt-3">주소*</p>
          <p className="h-14 flex items-center mt-3"></p>
          <p className="h-14 flex items-center mt-3">배송요청사항</p>
        </div>
        <DeliverForm />
      </form>
    </section>
  );
};

export default Delivery;
