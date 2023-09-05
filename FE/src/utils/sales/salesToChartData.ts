import type { Sales } from 'model/sales';

const salseToChartData = (sales: Sales[], salesStatus: boolean[]) => {
  const salesData = sales.map((data) => {
    let name = '';
    if (salesStatus[0]) {
      name = `${data.localDate.slice(5, 10)}`;
    }

    if (salesStatus[1]) {
      name = `${data.localDate.slice(5, 7)}월`;
    }

    return {
      name,
      매출금액: String(data.totalOriginalPrice).slice(0, -3),
    };
  });

  return salesData;
};

export default salseToChartData;
