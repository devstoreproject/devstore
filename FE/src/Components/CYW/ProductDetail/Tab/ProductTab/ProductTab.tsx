export default function ProductTab({ tab }: { tab: number }) {
  if (tab === 0) {
    return (
      <div className="flex justify-center">
        <img src="https://img.danawa.com/prod_img/500000/501/355/desc/prod_13355501/add_2/20230208151607899_QLN35NYH.jpg" />
      </div>
    );
  }
}
