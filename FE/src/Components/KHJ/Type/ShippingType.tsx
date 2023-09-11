export interface ShippingType {
  data: ShippingList[];
  code: string;
  message: string;
}

export interface ShippingList {
  infoId?: number;
  recipient: string;
  mobileNumber: string;
  zipCode: string;
  addressSimple: string;
  addressDetail: string;
}
