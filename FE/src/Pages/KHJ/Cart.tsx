import PageHistory from 'Components/KHJ/Common/PageHistory';
import CartMain from 'Components/KHJ/Cart/CartMain';

export default function Cart(): React.ReactElement {
  return (
    <>
      <PageHistory pageName="장바구니" pageHistory="장바구니" />
      <main className="mb-20">
        <CartMain />
      </main>
    </>
  );
}
