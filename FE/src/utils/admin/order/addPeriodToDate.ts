const addPeriodToDate = (createdAt: number[]) => {
  return createdAt.slice(0, 3).join('. ');
};

export default addPeriodToDate;
