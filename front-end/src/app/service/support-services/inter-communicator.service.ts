import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {NewLeaseMonthlyInstallments} from "../../classes/NewLeaseMonthlyInstallments";

@Injectable()
export class InterCommunicatorService {

  private newLeasePayments = new BehaviorSubject<NewLeaseMonthlyInstallments>(new NewLeaseMonthlyInstallments('',  '', '', ''));
  currentActiveNewLeasePayments =this.newLeasePayments.asObservable();

  constructor() { }

  setCurrentActiveNewLeasePayments(lease: NewLeaseMonthlyInstallments) {
    this.newLeasePayments.next(lease);
  }
}
