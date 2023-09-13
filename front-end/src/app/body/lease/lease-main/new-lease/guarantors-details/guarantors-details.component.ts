import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-guarantors-details',
  templateUrl: './guarantors-details.component.html',
  styleUrls: ['./guarantors-details.component.css']
})
export class GuarantorsDetailsComponent implements OnInit {

  isSubmitted = false;
  public stepFourForm: FormGroup;
  constructor(private fb: FormBuilder) {
    this.stepFourForm = this.fb.group({
      guarantorOneForm: this.fb.group( {
        name: this.fb.control('', Validators.required),
        address: this.fb.control(''),
        nic: this.fb.control(''),
        phone: this.fb.control('')
      }),
      guarantorTwoForm: this.fb.group( {
        name: this.fb.control('', Validators.required),
        address: this.fb.control(''),
        nic: this.fb.control(''),
        phone: this.fb.control('')
      }),
    })
  }

  ngOnInit(): void {
  }

  submitNewLeaseGuarantorsDetails() {
    this.isSubmitted = true;
  }
}
