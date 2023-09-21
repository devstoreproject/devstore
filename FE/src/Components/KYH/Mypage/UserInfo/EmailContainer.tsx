import type { Profile } from 'model/auth';

type email = Pick<Profile, 'email'>;

export default function EmailContainer({ email }: email) {
  return (
    <div className="flex items-center mb-4">
      <span className="w-32 text-gray-500">이메일(변경불가)</span>
      <input
        type="email"
        className="h-10 pl-4 text-sm border border-gray-300 w-96 rounded-3xl"
        value={email}
        readOnly
      />
    </div>
  );
}
