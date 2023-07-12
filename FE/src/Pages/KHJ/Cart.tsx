import PriceBottom from 'Components/KHJ/Cart/PriceBottom';
import Header from './Header/Header';
import PageHistory from 'Components/KHJ/Common/PageHistory';
import CartMain from 'Components/KHJ/Cart/CartMain';

export default function Cart(): React.ReactElement {
  return (
    <>
      <Header />
      <PageHistory />
      <main className="mb-20">
        <CartMain />
      </main>
      <PriceBottom />
    </>
  );
}
