import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApiService } from '../api.service';
import { Router } from '@angular/router';
// import { ToastrModule } from 'ngx-toastr';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  credentials:any= FormGroup; 
  validCredentials = {
    username: '',
    password: ''
  };

  constructor(private loginserv: ApiService, private fb: FormBuilder, private route: Router,private toastr: ToastrService) { }

  ngOnInit(): void {
    this.credentials = this.fb.group({
      username: ['shubham', [Validators.required, Validators.email]],
      password: ['12345', [Validators.required, Validators.minLength(8), Validators.pattern(/^(?=.*[a-zA-Z])(?=.*[0-9])/)]]
    });
  }

  onSubmit() {
    this.loginserv.LoginUser(this.credentials.value.username, this.credentials.value.password).subscribe(
      (response: any) => {
        if (response.empName === this.credentials.value.username && response.pass === this.credentials.value.password) {
         
          this.route.navigate(['EmployeeList']).then(() => {

            setTimeout(() => {
              this.toastr.success('Credentials are valid. Proceeding with login.', 'Success', { closeButton: true });
            }, 100);
          }
          
          );
          


          
        } else {
          this.toastr.error('Invalid credentials. Please check your username and password.', 'Error');
        }
      },
      (error) => {
        this.toastr.error('Username and/or password incorrect.', 'Error');
        console.log('result not ok', error);
      }
    );
  }
  
}