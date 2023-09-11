import type { ItemSales } from 'model/sales';

const calculateTotalSales = (sales: ItemSales[]) => {
  const totalSales = sales.reduce((total, sale) => total + sale.itemPrice, 0);

  return totalSales;
};

export default calculateTotalSales;
