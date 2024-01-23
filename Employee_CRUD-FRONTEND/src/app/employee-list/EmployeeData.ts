import { SafeUrl } from "@angular/platform-browser";
import { Observable } from "rxjs";

export class EmployeeData {
  empid: any;
  empName: string;
  pass: string;
  passportImg?: string;
  role: {
    roleId: any;
    roleName: string;
    feature: {
      featureId: any;
      featureName: string;
    };
    employees?: string[]; // Make employees property optional
  };
  department: {
    deptId: any;
    departmentNo: string;
  };
  passportPic?: Observable<SafeUrl>; // Make passportPic property optional

  constructor(
    empid: any,
    empName: string,
    pass: string,
    role: { roleId: any; roleName: string; feature: { featureId: any; featureName: string } },
    department: { deptId: any; departmentNo: string },
    passportImg?: string,
    employees?: string[], // Add employees as a parameter in the constructor
    passportPic?: Observable<SafeUrl> // Add passportPic as a parameter in the constructor
  ) {
    this.empid = empid;
    this.empName = empName;
    this.pass = pass;
    this.passportImg = passportImg;
    this.role = { ...role, employees }; // Spread the existing role properties and include employees
    this.department = department;
    this.passportPic = passportPic;
  }
}
