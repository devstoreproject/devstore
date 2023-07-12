function Hamburger() {
  return (
    <button className="absolute left-5 top-2/4 -translate-y-2/4">
      <HamburgerSpan />
      <HamburgerSpan />
      <HamburgerSpan />
    </button>
  );
}

function HamburgerSpan() {
  return <span className="w-5 h-px bg-light-black block"></span>;
}

export default Hamburger;
