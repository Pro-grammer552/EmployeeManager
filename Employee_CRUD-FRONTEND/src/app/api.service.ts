import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Employee } from 'src/Employee';
import { SafeUrl } from '@angular/platform-browser';

@Injectable({
  providedIn: 'root'
})
export class ApiService {




  constructor(private http: HttpClient) {}

  url = "http://localhost:8909/employees/login";

  LoginUser(username:String,password:String) {
    return this.http.get(`${this.url}?name=${username}&pass=${password}`);
  }



  private apiUrl = 'http://localhost:8909/employees';

  registerUser(userData: any): Observable<any> {
    console.log("the backedn data is",userData);
    return this.http.post(`${this.apiUrl}`, userData);
  }



  private apiUrl1 = "http://localhost:8909/employees";
  getEmployeeList(): Observable<any> {
    return this.http.get(`${this.apiUrl1}`);

  }

  private apiUrl2 = "http://localhost:8909/employees";
  deleteEmployee(id: number): Observable<Object>{
    return this.http.delete(`${this.apiUrl2}/${id}`);
  }

  private apiUrl3 = "http://localhost:8909/employees";
  updateEmployee(id: number, employee: any): Observable<Object>{
    return this.http.put(`${this.apiUrl3}/${id}`, employee);
  }



  private apiUrl4 = "http://localhost:8909/employees";
  getEmployeeById(id: number): Observable<any>{
    return this.http.get<any>(`${this.apiUrl4}/${id}`);
  }

  private baseUrl1 = 'http://localhost:8909/employees/imagesave';
  uploadPassportPic(empId: number, passportPic: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('image', passportPic, passportPic.name);
    return this.http.post<any>(`${this.baseUrl1}?id=${empId}&image=${passportPic}`,formData);
  }

  getPassportpic(empId:number) : Observable<SafeUrl>{
    return this.http.get<any>(`http://localhost:8909/employees/getImage/${empId}`);
  }


  createEmployee(data: any): Observable<any> {
    // Implement the logic to create a new employee
    // You may need to adjust the API endpoint and HTTP method accordingly
    return this.http.post<any>('your_api_endpoint', data);
  }
  

}
