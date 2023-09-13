import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './body/header/header.component';
import {LeaseModule} from "./body/lease/lease.module";
import {CommonModule} from "@angular/common";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {InterCommunicatorService} from "./service/support-services/inter-communicator.service";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    LeaseModule,
    BrowserAnimationsModule
  ],
  providers: [InterCommunicatorService],
  bootstrap: [AppComponent]
})
export class AppModule { }
