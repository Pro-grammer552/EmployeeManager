import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../api.service';
import { EmployeeData } from './EmployeeData';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { Observable, map } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { error } from 'console';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {
  employees: EmployeeData[] = [];
  passportUploadForm: FormData = new FormData();

  constructor(
    private employeeService: ApiService,
    private router: Router,
    private sanitizer: DomSanitizer,
    private toastr: ToastrService
  ) {}
  deletedData: any;

  ngOnInit(): void {
    this.getEmployeeList();
  }

  private getEmployeeList() {
    this.employeeService.getEmployeeList().subscribe((data: any[]) => {
      console.log(data);

      this.employees = data.map((employeeData) => {

        return new EmployeeData(
          employeeData.empid,
          employeeData.empName,
          employeeData.pass,
          employeeData.role,
          employeeData.department,
          employeeData.passportPic
        );
      });
    });
  }

  private getPassportPhoto(empId: number): Observable<SafeUrl> {
    return this.employeeService.getPassportpic(empId);
  }

  private mapToEmployee(data: any): Observable<EmployeeData> {
    const passportPic$ = this.getPassportPhoto(data.empid);

    return passportPic$.pipe(
      map((response) => {
        const employee: EmployeeData = {
          empid: data.empid || 0,
          empName: data.empName || "",
          pass: data.pass || "",
          role: {
            roleId: data.role.roleId || 0,
            roleName: data.role.roleName || "",
            feature: {
              featureId: data.role.feature.featureId || 0,
              featureName: data.role.feature.featureName || ""
            }
          },
          department: {
            deptId: data.department.deptId || 0,
            departmentNo: data.department.departmentNo || ""
          },
          passportPic: data.passportPic
        };
        return employee;
      }),
    );
  }



  private getSafeImageURL(base64Image: string, sanitizer: DomSanitizer): any {
    // Ensure base64Image is properly formed
    if (base64Image) {
      // Construct the safe URL
      return sanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + base64Image);
    } else {
      console.log(base64Image)
      // Return a placeholder or default image if base64Image is not available
      return 'path-to-placeholder-image';
    }
  }

  logins() {
    console.log('Navigating to login page');
    this.router.navigate(['/Registration']);
  }

  views(id: number) {
    console.log('Navigating to View page');
    this.router.navigate(['/view', id]);
  }

  updateEmloyee(id: number) {
    console.log('Navigating to Edit page');
    this.router.navigate(['edit', id]);
  }

  deleteEmployee(id: number) {
    this.employeeService.deleteEmployee(id).subscribe(data => {
      console.log(data);
      this.toastr.success('Record deleted successfully.', 'Success', { closeButton: true });
      this.getEmployeeList();

      
    });
  }

  undo() {
    if (this.deletedData) {
      this.employeeService.createEmployee(this.deletedData).subscribe(
        data=> {
          // Handle restore response...
          console.log(data);
          this.toastr.success('Record restored successfully.', 'Success', { closeButton: true });
          this.getEmployeeList();
          // After successful restoration, clear the deletedData property
          this.deletedData = null;
        },
        error => {
          // Handle restore error...
          console.error('Restore failed', error);
          this.toastr.error('Failed to restore record.', 'Error', { closeButton: true });
        }
      );
    
   
  }
}





  // Method to handle passport image upload
  uploadPassport(empId: number) {
    const passportPicFile:any = this.passportUploadForm.get('passportPic');
    if (passportPicFile) {
      this.employeeService.uploadPassportPic(empId, passportPicFile).subscribe(
        (response) => {
          console.log('Passport image uploaded successfully:', response);
          // Refresh employee list after successful upload
          this.getEmployeeList();
        },
        (error) => {
          console.error('Error uploading passport image:', error);
          // Handle error as needed
        }
      );
    }
  }

  // Method to handle file input change
  onFileChange(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.passportUploadForm.set('passportPic', file);
    }
  }
}
