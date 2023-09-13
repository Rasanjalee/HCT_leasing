import { Component, OnInit } from '@angular/core';
import {FormBuilder, Validators, FormsModule, ReactiveFormsModule} from '@angular/forms';

@Component({
  selector: 'app-new-lease',
  templateUrl: './new-lease.component.html',
  styleUrls: ['./new-lease.component.css']
})
export class NewLeaseComponent implements OnInit {

  selectedSection = 1;
  constructor(private _formBuilder: FormBuilder) { }

  ngOnInit(): void {
  }

  changeSteps(number: number) {
   this.selectedSection = number;
  }
}
