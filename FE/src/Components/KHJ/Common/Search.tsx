import { BiSearch } from 'react-icons/bi';

function Search() {
  return (
    <div className="flex items-center h-8 w-full">
      <input
        className="w-64 border-b-2 border-light-black"
        type="textarea"
      ></input>
      <button>
        <BiSearch size={26} />
      </button>
    </div>
  );
}

export default Search;
