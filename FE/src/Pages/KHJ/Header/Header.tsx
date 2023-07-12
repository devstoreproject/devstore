import NavTop from 'Components/KHJ/Nav/NavTop';
import NavBottom from 'Components/KHJ/Nav/NavBottom';
import GlobalNav from 'Components/KHJ/Nav/GlobalNav';
import SearchNav from 'Components/KHJ/Nav/SearchNav';

export default function Nav() {
  return (
    <>
      <header className="fixed top-0 left-0 w-full">
        <NavTop />
        <NavBottom />
      </header>
      <GlobalNav />
      <SearchNav />
    </>
  );
}
