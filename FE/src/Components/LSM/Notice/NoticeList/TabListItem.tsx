interface TabList {
  title: string;
}

export default function TabListItem({ title }: TabList) {
  return (
    <li className="items-center justify-center w-20 py-1 my-16 mr-5 text-sm font-medium text-center transition-all border cursor-pointer hover:text-white rounded-3xl bg-tab-gray hover:bg-black">
      {title}
    </li>
  );
}
