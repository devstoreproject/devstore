export interface Inquiry {
  itemId?: number;
  questionId?: number;
  comment: string;
  answer: string | null;
}

export interface InquiryDetail {
  comment: string;
  answer: Answer | null;
}

export interface Answer {
  comment: string;
}
