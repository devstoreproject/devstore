export default function MainImage() {
  const url =
    'https://images.unsplash.com/photo-1581472723648-909f4851d4ae?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=3540&q=80';
  return (
    <div className="w-full h-136">
      <img src={url} className="object-cover w-full h-full" />
    </div>
  );
}
