import TableContents from './TableContents';
import TableTitle from './TableTitle';

export default function Table() {
  return (
    <div className="bg-gray-100 border border-gray-400 rounded-t-lg w-250 h-128">
      <TableTitle />
      <ul>
        <TableContents />
        <TableContents />
        <TableContents />
      </ul>
    </div>
  );
}
