import NavTop from 'Components/KHJ/Nav/NavTop';
import NavBottom from 'Components/KHJ/Nav/NavBottom';
import GlobalNav from 'Components/KHJ/Nav/GlobalNav';
import SearchNav from 'Components/KHJ/Nav/SearchNav';
import { useState } from 'react';

export default function Nav() {
  const [isNavOpen, setNavOpen] = useState(false);
  const [isSearchOpen, setSearchOpen] = useState(false);
  return (
    <>
      <header className="fixed top-0 left-0 z-10 w-full">
        <NavTop />
        <NavBottom
          navOpen={isNavOpen}
          setNavOpen={setNavOpen}
          searchOpen={isSearchOpen}
          setSearchOpen={setSearchOpen}
        />
      </header>
      <GlobalNav navOpen={isNavOpen} setNavOpen={setNavOpen} />
      <SearchNav searchOpen={isSearchOpen} setSearchOpen={setSearchOpen} />
      <div className="h-20 mt-3"></div>
    </>
  );
}
