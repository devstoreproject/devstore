import { Link } from 'react-router-dom';

export default function FixedBottomBox() {
  return (
    <div className="fixed px-4 py-2 font-bold bottom-2 right-3 animate-bounce bg-neon-green rounded-2xl">
      <Link to="https://github.com/devstoreproject/devstore " target="_blank">
        @ devstore
      </Link>
    </div>
  );
}
