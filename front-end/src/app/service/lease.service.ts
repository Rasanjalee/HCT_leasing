import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {LeaseDetails, LeaseGuarantors} from "../classes/LeaseDetails";

const BASE_URL = 'http://localhost:8080';

@Injectable({
  providedIn: 'root'
})
export class LeaseService {

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + localStorage.getItem('le_token')
    })
  };

  constructor(private httpClient: HttpClient) { }

  leaseDocumentUpload(file: File) {
    const formData = new FormData();
    formData.append('image', file);

    const headers = new HttpHeaders();
    headers.append('Content-Type', 'multipart/form-data');

    const leToken = localStorage.getItem('le_token');
    if (leToken) {
      headers.append('Authorization', leToken);
    }
    return this.httpClient.post('http://localhost:8080/leasing/document-upload', formData, {headers});
  }

  fetchAllData(openStatus: number) {
    return this.httpClient.get(`${BASE_URL}/leasing/all?open-status=${openStatus}`, this.httpOptions);
  }

  createNewLease(payload: LeaseDetails) {
    return this.httpClient.post(`${BASE_URL}/leasing/save`, payload, this.httpOptions);
  }

  calculateLeaseInstallment(leaseId: number, selectedDate: any) {
    return this.httpClient.get(`${BASE_URL}/leasing/calculate-installment?lease-id=${leaseId}&payment-received-date=${selectedDate}`, this.httpOptions)
  }

  payMonthlyInstallment(leaseId: number, interest: number, paymentDate: any, userId: number, installment: number,
                        penaltyDuration: number, penaltyAmount: any) {
    return this.httpClient.put(`${BASE_URL}/leasing/make-payment?lease-id=${leaseId}&interest=${interest}&payment-date=${paymentDate}&user-id=${userId}&installment=${installment}&penalty-duration=${penaltyDuration}&penalty-amount=${penaltyAmount}`, '', this.httpOptions);
  }

  getIncomeReportData(startDate: string, endDate: string) {
    return this.httpClient.get(`${BASE_URL}/leasing/report/income?start-date=${startDate}&end-date=${endDate}`, this.httpOptions);
  }

  getIncomeSummaryReportData( startDate: string, endDate: string ) {
    return this.httpClient.get(`${BASE_URL}/leasing/report/income/summery?start-date=${startDate}&end-date=${endDate}`, this.httpOptions);
  }

  getOutDatedPaymentsDetails() {
    return this.httpClient.get(`${BASE_URL}/leasing/report/outdated-payments`, this.httpOptions);
  }

  editLeaseGuarantorsDetails(payload: LeaseGuarantors[]) {
    return this.httpClient.put(`${BASE_URL}/leasing/guarantors/edit`, payload, this.httpOptions);
  }
  getLeaseeById(id:string){
    return this.httpClient.get(`${BASE_URL}/leasing/leasee?nic=${id}`,this.httpOptions);
  }
  removeLeaseById(id:number,status:number){
    console.log(this.httpOptions)
    return this.httpClient.put(`${BASE_URL}/leasing/delete?id=${id}&status=${status}`,'',this.httpOptions);
  }

  payInstallment(id: number, amount: number, userId: string | null){
    return this.httpClient.put(`${BASE_URL}/leasing/make-payment?lease-id=${id}&amount=${amount}&user-id=${userId}`,'',this.httpOptions);
  }
}
