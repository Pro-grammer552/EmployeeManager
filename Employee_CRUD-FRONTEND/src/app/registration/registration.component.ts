import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ApiService } from '../api.service';
import { Router } from '@angular/router';
import { ToastrModule, ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  RegisterForm: any = FormGroup;
  constructor(private fb: FormBuilder,private apiService: ApiService,private route: Router,private toastr: ToastrService) { }

  ngOnInit(): void {

    this.RegisterForm = this.fb.group({
      empName: [],
      pass: [],
      roleId: [],
      deptId: [],
      image:['']
    });
  }
 myImg:any;
  onFileChange(event: any) {
    const fileInput = event.target.files[0];

    if (fileInput) {
      const reader = new FileReader();
      reader.onload = this._handleReaderLoaded.bind(this);
      reader.readAsDataURL(fileInput);
    }
  }

  _handleReaderLoaded(e:any) {
    let reader = e.target;
    const arrayBuffer = reader.result;
        this.RegisterForm.patchValue({
          image: arrayBuffer
        });
    console.log(arrayBuffer)
    this.myImg=arrayBuffer;
  }
 
  onSubmit() {
    const userData = {
      empName: this.RegisterForm.value.empName,
      pass: this.RegisterForm.value.pass,
      roleId: this.RegisterForm.value.roleId,
      deptId: this.RegisterForm.value.deptId,
      image: this.myImg

    };
    const fileInput = this.RegisterForm.value.image;
    console.log(fileInput);
    this.apiService.registerUser(userData).subscribe(
      (response) => {
        console.log(userData);
        this.data = response;
        console.log('Registration successful:', this.data);

        this.route.navigate(['Login']).then(() => {
          this.toastr.success('Registration Successfully Completed ', 'Success!!', { closeButton: true });
      }
      );
      },
      
    error => {
      console.error('Update failed', error);
      this.toastr.error('Fill REQUIRED FIELDS ', 'ERROR!!', { closeButton: true });
    }
  );
}
 


  get empName() {
    return this.RegisterForm.get('empName');
  }

  get pass() {
    return this.RegisterForm.get('pass');
  }

  get role() {
    return this.RegisterForm.get('role');
  }

  get department() {
    return this.RegisterForm.get('department');
  }
  get image() {
    return this.RegisterForm.get('image');
  }




  data: any;



}
