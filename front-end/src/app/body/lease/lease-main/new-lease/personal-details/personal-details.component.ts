import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-personal-details',
  templateUrl: './personal-details.component.html',
  styleUrls: ['./personal-details.component.css']
})
export class PersonalDetailsComponent implements OnInit {
  public stepOneForm: FormGroup;
  isSubmitted = false;

  afuConfig = {
    multiple: false,
    formatsAllowed: '.png',
    uploadAPI: {
      url: "https://example-file-upload-api",
      headers: {
        'Accept': '*/*',
        'validate-key': '',
        'submission-date': ''
      }
    },
    hideProgressBar: false,
    hideResetBtn: true,
    hideSelectBtn: false,
    theme:'attachPin'
  };

  constructor(private fb: FormBuilder) {
    this.stepOneForm = this.fb.group({
      firstName: this.fb.control('', Validators.required),
      lastName: this.fb.control('', Validators.required),
      mobileNumber: this.fb.control('', Validators.required),
      landNumber: this.fb.control(''),
      nicNumber: this.fb.control('', Validators.required),
      address: this.fb.control('', Validators.required),
      remarks: this.fb.control(''),
      email: this.fb.control(''),
      coordinates: this.fb.control(''),
    });

  }

  ngOnInit(): void {
  }

  submitNewLeasePersonalDetails() {
    this.isSubmitted = true;
  }

  docUpload($event: any) {

  }
}
