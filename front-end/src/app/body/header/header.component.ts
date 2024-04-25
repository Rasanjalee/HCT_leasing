import {Component, ElementRef, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {InterCommunicatorService} from "../../service/support-services/inter-communicator.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  loggedEmployeeName: any;
  activeTab: any; // Set the default active tab
  // activeTab: string = 'Lease'; // Set the default active tab
  constructor(private router: Router,
              private communicationService: InterCommunicatorService,
              private activeRoute: ActivatedRoute
              ) {
    this.activeRoute.url.subscribe(urlSegments => {
      const currentUrl = this.router.url;
      console.log('Current URL:', currentUrl); // Debugging statement
      console.log('URL Segments:', urlSegments);
      if (currentUrl.includes('/lease')) {
        this.activeTab = 'Lease';
      } else if (currentUrl.includes('administration')) {
        console.log('Contains "administration":', true); // Debugging statement
        this.activeTab = 'Administration';
      } else if (currentUrl.includes('report')) {
        this.activeTab = 'Reports';
      }
    });
  }

  ngOnInit(): void {
    this.loggedEmployeeName = localStorage.getItem('le_name')
  }

  createNewLease() {
    this.setActiveTab('Lease');
    this.router.navigate(['./lease'])
  }
  createNewUser(){
    this.router.navigate(['./administration/user/new'])

  }
  setActiveTab(tab: string): void {
    this.activeTab = tab;
  }
  signOut() {
    localStorage.clear();
    this.communicationService.changeHeaderNavBarStatus(true);
    this.router.navigate(['./login']);
  }

  navigateToSettings() {
    this.setActiveTab('Administration');
    this.router.navigate(['./administration'])
  }
  navigateToReports() {
    this.setActiveTab('Reports');
    this.router.navigate(['./report'])
  }
}
