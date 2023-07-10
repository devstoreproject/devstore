import CheckListItem from './CheckListItem';

export default function CheckListMain() {
  return (
    <section className="rounded-xl bg-gray-100 border-gray-300 border p-8 mx-5">
      <ul className="flex flex-wrap justify-between">
        <CheckListItem />
      </ul>
    </section>
  );
}
