import Footer from './Footer';
import Header from '../KHJ/Header/Header';
import { Outlet } from 'react-router-dom';

export default function BasicLayout() {
  return (
    <>
      <Header />
      <Outlet />
      <Footer />
    </>
  );
}
