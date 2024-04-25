import { Component, OnInit } from '@angular/core';
import {InterCommunicatorService} from "../../../../../service/support-services/inter-communicator.service";
import {NewLeaseMonthlyInstallments, NewLeasePaymentsDetails} from "../../../../../classes/NewLeaseMonthlyInstallments";

@Component({
  selector: 'app-payments-details',
  templateUrl: './payments-details.component.html',
  styleUrls: ['./payments-details.component.css']
})
export class PaymentsDetailsComponent implements OnInit {

  newLeasePaymentAmounts: NewLeaseMonthlyInstallments = new NewLeaseMonthlyInstallments('', '', '', '', '', '', '', '', '',0,0.0,0.0);
  newLeasePaymentAmountsLoaded = false;
  monthlyPaymentDetails: NewLeasePaymentsDetails[] = [];

  constructor(private communicatorService: InterCommunicatorService) { }

  ngOnInit(): void {
    this.communicatorService.currentActiveNewLeasePayments
      .subscribe((data: {status: boolean, paymentHistory: NewLeasePaymentsDetails[], lease: NewLeaseMonthlyInstallments}) => {
        if (data.status) {
          this.monthlyPaymentDetails = data.paymentHistory;
          this.newLeasePaymentAmounts =  data.lease;
          this.newLeasePaymentAmountsLoaded = true;
        }
      })
  }
}
