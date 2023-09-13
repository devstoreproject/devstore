export interface ItemSales {
  itemId?: number;
  itemName: string;
  itemPrice: number;
}

const calculateTotalSales = (sales: ItemSales[]) => {
  const totalSales = sales.reduce((total, sale) => total + sale.itemPrice, 0);

  return totalSales;
};

export default calculateTotalSales;
