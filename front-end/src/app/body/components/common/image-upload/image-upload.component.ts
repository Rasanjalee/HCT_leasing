import {Component, ElementRef, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {HttpErrorResponse} from "@angular/common/http";
import {LeaseService} from "../../../../service/lease.service";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";

@Component({
  selector: 'app-image-upload',
  templateUrl: './image-upload.component.html',
  styleUrls: ['./image-upload.component.css']
})
export class ImageUploadComponent implements OnInit {


  @Output() uploadedImageUrl = new EventEmitter<any>();

  @ViewChild('fileInput') fileInput: ElementRef | any;
  uploadedImage: any;
  imageUploaded = false;
  constructor(private leaseService: LeaseService,
              private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
  }

  get safeImageUrl(): SafeResourceUrl {
    // Use DomSanitizer to sanitize the URL
    return this.sanitizer.bypassSecurityTrustResourceUrl( (this.uploadedImage));
  }





  openFileInput() {
    // Trigger the file input click event when the SVG or <a> is clicked
    this.fileInput.nativeElement.click();
  }

  onFileChange(event: any) {
    const file = event.target.files[0];
    this.leaseService.leaseDocumentUpload(file)
      .subscribe((data: any) => {
      }, (error: HttpErrorResponse) => {
        this.uploadedImage = error.error.text;
        this.uploadedImageUrl.emit(this.uploadedImage);
        this.imageUploaded = true;
      })

    // Handle the response from the endpoint
  }

}
