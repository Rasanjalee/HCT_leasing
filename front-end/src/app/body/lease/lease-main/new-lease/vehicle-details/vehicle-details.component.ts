import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-vehicle-details',
  templateUrl: './vehicle-details.component.html',
  styleUrls: ['./vehicle-details.component.css']
})
export class VehicleDetailsComponent implements OnInit {

  isSubmitted = false;
  public stepTwoForm: FormGroup;
  constructor(private fb: FormBuilder) {
    this.stepTwoForm = this.fb.group({
      vehicleNo: this.fb.control('', Validators.required),
      chassisNo: this.fb.control('', Validators.required),
      engineNo: this.fb.control('', Validators.required),
      make: this.fb.control('', Validators.required),
      model: this.fb.control('', Validators.required),
      manufactureYear: this.fb.control('', Validators.required),
      registerYear: this.fb.control('', Validators.required),
      noOfOwners: this.fb.control('', Validators.required),
      remarks: this.fb.control(''),
    });
  }

  ngOnInit(): void {
  }

  submitNewLeaseVehicleDetails() {
    this.isSubmitted = true;
  }
}
