import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {NewLeaseMonthlyInstallments} from "../../../../../classes/NewLeaseMonthlyInstallments";
import {InterCommunicatorService} from "../../../../../service/support-services/inter-communicator.service";

@Component({
  selector: 'app-lease-details',
  templateUrl: './lease-details.component.html',
  styleUrls: ['./lease-details.component.css']
})
export class LeaseDetailsComponent implements OnInit {
  public stepFiveForm: FormGroup;
  isSubmitted = false;
  leaseAmountsCalculated = false;
  monthlyInstallment = new NewLeaseMonthlyInstallments('', '', '', '');
  constructor(private fb: FormBuilder,
              private communicationService: InterCommunicatorService) {
    this.stepFiveForm = this.fb.group( {
      valuation: this.fb.control('', Validators.required),
      leaseType: this.fb.control('1'),
      duration: this.fb.control('', Validators.required),
      interest: this.fb.control('', Validators.required),
      documentCharge: this.fb.control('', Validators.required),
      visitCharge: this.fb.control('', Validators.required),
      startDate: this.fb.control(new Date().toISOString().slice(0, 10))
    })
  }

  ngOnInit(): void {
  }

  submitNewLeaseValuationDetails() {
    this.communicationService.setCurrentActiveNewLeasePayments(this.monthlyInstallment);
  }

  formValidation() {
    return this.stepFiveForm.get('valuation')?.value !== '' && this.stepFiveForm.get('duration')?.value !== '' &&
      this.stepFiveForm.get('interest')?.value !== '' && this.stepFiveForm.get('documentCharge')?.value !== '' &&
      this.stepFiveForm.get('documentCharge')?.value !== '';
  }

  calculateLeaseAmounts() {
    this.isSubmitted = true;
    this.leaseAmountsCalculated = false;
    if (this.formValidation()) {
      this.monthlyInstallment.cashOnHand =
        this.stepFiveForm.get('valuation')?.value - (this.stepFiveForm.get('documentCharge')?.value + this.stepFiveForm.get('visitCharge')?.value);

      if (this.stepFiveForm.get('leaseType')?.value === '1') {
        const totalInterest = this.stepFiveForm.get('valuation')?.value * this.stepFiveForm.get('duration')?.value * this.stepFiveForm.get('interest')?.value / 100.0;
        this.monthlyInstallment.totalLeaseCost = this.stepFiveForm.get('valuation')?.value + totalInterest;
        this.monthlyInstallment.installment = (this.monthlyInstallment.totalLeaseCost / this.stepFiveForm.get('duration')?.value).toFixed(2);
        this.monthlyInstallment.totalInterest = totalInterest;
      }

      this.leaseAmountsCalculated = true;
      console.log(this.monthlyInstallment)
    }
  }
}
