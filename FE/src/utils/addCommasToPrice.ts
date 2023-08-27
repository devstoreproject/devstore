const addCommasToPrice = (price: number | string) => {
  const commaNumber = String(price).replace(/\B(?=(\d{3})+(?!\d))/g, ',');

  return commaNumber;
};

export default addCommasToPrice;
