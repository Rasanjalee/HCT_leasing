import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-lease-main',
  templateUrl: './lease-main.component.html',
  styleUrls: ['./lease-main.component.css']
})
export class LeaseMainComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  navigateToLeaseCreatePage() {
    this.router.navigate(['/lease/create'])
  }

}
