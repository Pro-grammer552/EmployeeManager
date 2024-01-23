  import { ActivatedRoute, Router } from '@angular/router';
  import { FormBuilder, FormGroup, Validators } from '@angular/forms';
  import { ApiService } from '../api.service';
  import { Component, OnInit } from '@angular/core';
  import { ToastrService } from 'ngx-toastr';

  @Component({
    selector: 'app-edit-details',
    templateUrl: './edit-details.component.html',
    styleUrls: ['./edit-details.component.css']
  })
  export class EditDetailsComponent implements OnInit {
    myForm: FormGroup;

    userId: number = 1;

    constructor(
      private fb: FormBuilder,
      private empser: ApiService,
      private router: Router,
      private route: ActivatedRoute,
      private toaster:ToastrService
    ) {


      this.myForm = this.fb.group({
        empName: ['', Validators.required],
        pass: ['', Validators.required],
        roleId: [''],
        deptId: [''],
        passportPic: ['']

      });

    }

    ngOnInit(): void {
      this.route.params.subscribe(params => {
        this.userId = +params['id'];
      });

      this.empser.getEmployeeById(this.userId).subscribe(employee => {
        if (employee) {
          this.myForm.patchValue({
            empName: employee.empName || '',
            pass: employee.pass || '',
            passportPic:employee.passportPic || ''
          });
        }
      });
    }
    goBack() {
      this.router.navigate(['/EmployeeList']);
    }

    onSubmit() {
      const updatedData = this.myForm.value;
      const res = {
        empName: this.myForm.value.empName,
        pass: this.myForm.value.pass,
        role: {
          roleId: this.myForm.value.roleId,
        },
        department: {
          deptId: this.myForm.value.deptId,
        },
        passportPic: this.myForm.value.passportPic
      };
    
      console.log('data', res);
      
      this.empser.updateEmployee(this.userId, res).subscribe(
        data => {
          console.log('Update successful', data);
    
          this.router.navigate(['EmployeeList']).then(() => {
            setTimeout(() => {
              this.toaster.success('Credentials Updated SucessFully..', 'Success', { closeButton: true });
            }, 100);
          });
        },
        error => {
          console.error('Update failed', error);
          this.toaster.error('Please fill The Required Fields.', 'Success', { closeButton: true });
        }
      );
    }
  }  