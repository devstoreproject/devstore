interface TabList {
  title: string;
  active: boolean;
  onClick: () => void;
  adminPath: string;
}

export default function TabListItem({
  title,
  active,
  onClick,
  adminPath,
}: TabList) {
  return (
    <button
      type="button"
      className={`items-center justify-center w-20 py-1 ${
        adminPath === 'admin' ? '' : 'my-16'
      } mr-5 text-sm font-medium text-center border hover:text-white rounded-3xl  ${
        active ? 'bg-black text-white' : 'bg-tab-gray hover:bg-black'
      }`}
      onClick={onClick}
    >
      {title}
    </button>
  );
}
