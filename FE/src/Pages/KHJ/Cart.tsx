import PriceBottom from 'Components/KHJ/Cart/PriceBottom';
import Header from './Header/Header';
import PageHistory from 'Components/KHJ/Common/PageHistory';
import CartMain from 'Components/KHJ/Cart/CartMain';

export default function Cart(): React.ReactElement {
  // 총 금액 및 갯수, 성공 후 리덕스로 리팩토링
  // const [isTotalPrice, setIsTotalPrice] = useState<Number>(0);
  // const [isTotalCount, setIsTotalCount] = useState<Number>(0);
  return (
    <>
      <Header />
      <PageHistory pageName="장바구니" pageHistory="장바구니" />
      <main className="mb-20">
        <CartMain />
      </main>
      <PriceBottom />
    </>
  );
}
