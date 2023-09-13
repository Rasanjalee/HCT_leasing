import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LeaseComponent } from './lease.component';
import { NewLeaseComponent } from './lease-main/new-lease/new-lease.component';
import {LeaseMainComponent} from "./lease-main/lease-main.component";

const routes: Routes = [
  {
    path: 'lease',
    component: LeaseComponent,
    children: [
      { path: '', redirectTo: 'main', pathMatch: 'full' },
      { path: 'main', component: LeaseMainComponent },
      { path: 'create', component: NewLeaseComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LeaseRoutingModule { }
