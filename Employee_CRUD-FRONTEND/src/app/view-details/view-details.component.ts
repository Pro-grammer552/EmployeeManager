  import { Component, OnInit } from '@angular/core';
  import { ActivatedRoute, Router } from '@angular/router';

  import { Employee } from 'src/Employee';
import { ApiService } from '../api.service';
import { EmployeeData } from '../employee-list/EmployeeData';

  @Component({
    selector: 'app-view-details',
    templateUrl: './view-details.component.html',
    styleUrls: ['./view-details.component.css']
  })
  export class ViewDetailsComponent implements OnInit {
    employee1 = {
      empid: 0,
      empName: "string",
      pass: "string",
      role: {
        roleId: 0,
        roleName: "string",
        feature: {
          featureId: 0,
          featureName: ""
        }
      },
      department: {
        deptId: 0,
        departmentNo: ""
      },
      passportPic: ['']

    };

    id: any;
    employee: EmployeeData[] = [];

    constructor(private route: ActivatedRoute, private employeService: ApiService,private router:Router) { }

    ngOnInit(): void {
      this.id = this.route.snapshot.params['id'];
      this.employeService.getEmployeeById(this.id).subscribe(data => {
        this.employee = [data];
        console.log(data)
      });
    }

    goBack() {
      this.router.navigate(['/EmployeeList']);
    }
  }
