const calculateSalesRate = (itemPrice: number, totalPrice: number) => {
  const salesRate = Math.round((itemPrice / totalPrice) * 100);

  return salesRate;
};

export default calculateSalesRate;
