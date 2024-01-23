import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { AppComponent } from './app.component';
import { RegistrationComponent } from './registration/registration.component';
import { HomeComponent } from './home/home.component';
import { EmployeeListComponent } from './employee-list/employee-list.component';
import { ViewDetailsComponent } from './view-details/view-details.component';
import { EditDetailsComponent } from './edit-details/edit-details.component';


const routes: Routes = [
  { path: "", component: HomeComponent },
  { path: "Login", component: LoginComponent },
  { path: "Registration", component: RegistrationComponent } ,
  { path: 'EmployeeList', component: EmployeeListComponent },
  {path:'view/:id',component:ViewDetailsComponent},
  {path:'edit/:id',component:EditDetailsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
