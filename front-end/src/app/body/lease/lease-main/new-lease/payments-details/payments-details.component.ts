import { Component, OnInit } from '@angular/core';
import {InterCommunicatorService} from "../../../../../service/support-services/inter-communicator.service";
import {NewLeaseMonthlyInstallments} from "../../../../../classes/NewLeaseMonthlyInstallments";

@Component({
  selector: 'app-payments-details',
  templateUrl: './payments-details.component.html',
  styleUrls: ['./payments-details.component.css']
})
export class PaymentsDetailsComponent implements OnInit {

  newLeasePaymentAmounts: NewLeaseMonthlyInstallments = new NewLeaseMonthlyInstallments('', '', '', '');
  newLeasePaymentAmountsLoaded = false;
  constructor(private communicatorService: InterCommunicatorService) { }

  ngOnInit(): void {
    this.communicatorService.currentActiveNewLeasePayments
      .subscribe((data: NewLeaseMonthlyInstallments) => {
        if (data.totalLeaseCost !== '') {
          this.newLeasePaymentAmounts = data;
          this.newLeasePaymentAmountsLoaded = true;
        }
      })
  }

}
