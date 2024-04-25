import { Component, OnInit } from '@angular/core';
import {InterCommunicatorService} from "../../../../service/support-services/inter-communicator.service";
import {
  LeaseDetails,
  LeaseDocuments,
  LeaseeMasterDetails, LeaseGuarantors, LeaseInstallments,
  LeaseMasterDetails, LeasePaymentHistory,
  VehicleDetails
} from "../../../../classes/LeaseDetails";
import {Router} from "@angular/router";
import {LeaseService} from "../../../../service/lease.service";
import {ModalService} from "../../../../service/support-services/modal.service";
import {data} from "jquery";

@Component({
  selector: 'app-lease-view',
  templateUrl: './lease-view.component.html',
  styleUrls: ['./lease-view.component.css']
})
export class LeaseViewComponent implements OnInit {

  selectedLeaseDetails: LeaseDetails;
  selectedLeaseDetailsDataFetched: boolean;
  selectedSectionToEdit: string = '';
  constructor(private communicatorService: InterCommunicatorService,
              private leaseService: LeaseService,
              private modalService: ModalService,
              private router: Router) {

    // Create instances of the required classes here
    const leaseeMaster = new LeaseeMasterDetails('', '', '', '', '', '', '', '');
    const leaseMaster = new LeaseMasterDetails(0, '', '', '', '', '', '', '', '',
      '', '', '', '', '', '', '', '',
      '', '', '', '', '', '', '',
      '', 0, '', '', '', '',  '', '', '','');;
    const vehicle = new VehicleDetails('', '', '', '', '', '', '', '', '');
    const documents = new LeaseDocuments('', '');
    const guarantors =  new LeaseGuarantors('', '', '', '');
    const installments = new LeaseInstallments(0, '', '', '', '', '', '',
      '', '');
    const paymentHistory = new LeasePaymentHistory('', '');
    this.selectedLeaseDetails = new LeaseDetails(leaseeMaster, leaseMaster, vehicle, [guarantors], [documents], [installments], [paymentHistory]);
    this.selectedLeaseDetailsDataFetched = false;
  }

  ngOnInit(): void {
    this.communicatorService.selectedLeaseDetailsUpdate
      .subscribe((data: {status: boolean, lease: LeaseDetails}) => {
        if (data.status) {
          this.selectedLeaseDetails =  data.lease;
          this.selectedLeaseDetailsDataFetched = true;
        } else {
          this.router.navigate(['/lease/main']);
        }
      })

  }

  backToLeaseMainPage() {
    this.router.navigate(['/lease/main']);
  }

  editSectionEnabled(section: string) {
    this.selectedSectionToEdit = section;
  }

  editLeaseGuarantorsDetails() {
    this.leaseService.editLeaseGuarantorsDetails(this.selectedLeaseDetails.leaseGurantors)
      .subscribe((data: any) => {
        this.editSectionEnabled('');
        const json = {type: 'APPROVE', status: 'Success', message: 'Guarantors Details edit successfully!'}
        this.communicatorService.displayErrorModal(json);
        this.showModal('confirmationModal');
      })
  }

  showModal(modalId: string) {
    this.modalService.showModal(modalId);
  }

}
