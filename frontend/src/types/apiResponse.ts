export interface ApiResponse<T> {
  data: T;
  timestamp: Date;
  status: number;
}
