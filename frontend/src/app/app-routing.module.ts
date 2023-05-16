import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { UsersOverviewComponent } from './components/users/users-overview/users-overview.component';
import { UserDetailsComponent } from './components/users/user-details/user-details.component';
import { UserEditComponent } from './components/users/user-edit/user-edit.component';
import { DoctorRatingStatisticComponent } from './components/doctors/doctor-rating-statistic/doctor-rating-statistic.component';
import { UserDeleteComponent } from './components/users/user-delete/user-delete.component';
import { HospitalOverviewComponent } from './components/hospitals/hospital-overview/hospital-overview.component';
import { HospitalCreateComponent } from './components/hospitals/hospital-create/hospital-create.component';
import { HospitalDetailsComponent } from './components/hospitals/hospital-details/hospital-details.component';
import { HospitalEditComponent } from './components/hospitals/hospital-edit/hospital-edit.component';
import { HospitalDeleteComponent } from './components/hospitals/hospital-delete/hospital-delete.component';
import { DoctorDetailsComponent } from './components/doctors/doctor-details/doctor-details.component';
import { DoctorCreateComponent } from './components/doctors/doctor-create/doctor-create.component';
import { DoctorEditComponent } from './components/doctors/doctor-edit/doctor-edit.component';
import { DoctorDeleteComponent } from './components/doctors/doctor-delete/doctor-delete.component';
import { ReviewDetailsComponent } from './components/reviews/review-details/review-details.component';
import { ReviewCreateComponent } from './components/reviews/review-create/review-create.component';
import { ReviewEditComponent } from './components/reviews/review-edit/review-edit.component';
import { ReviewDeleteComponent } from './components/reviews/review-delete/review-delete.component';
import { HospitalCountStatisticComponent } from './components/hospitals/hospital-count-statistic/hospital-count-statistic.component';
import { RegisterComponent } from './components/login/register/register.component';
import { RegisterConfirmComponent } from './components/login/register-confirm/register-confirm.component';
import { LoginComponent } from './components/login/login/login.component';
import { LogoutComponent } from './components/login/logout/logout.component';
import { UnauthorizedComponent } from './components/unauthorized/unauthorized.component';
import { AdminComponent } from './components/admin/admin.component';

const routes: Routes = [
  {
    path: "",
    component: HomeComponent
  },
  {
    path:"users",
    component: UsersOverviewComponent
  },
  {
    path:"users/:id",
    component: UserDetailsComponent
  },
  {
    path:"users/:id/edit",
    component: UserEditComponent
  },
  {
    path:"users/:id/delete",
    component: UserDeleteComponent
  },
  {
    path:"doctors/sorted-by-rating",
    component: DoctorRatingStatisticComponent
  },
  {
    path:"hospitals",
    component: HospitalOverviewComponent
  }, 
  {
    path:"hospitals/create",
    component: HospitalCreateComponent
  },
  {
    path:"hospitals/sorted-by-doctors",
    component: HospitalCountStatisticComponent
  },
  {
    path:"hospitals/:id",
    component: HospitalDetailsComponent
  },
  {
    path:"hospitals/:id/edit",
    component: HospitalEditComponent
  },  
  {
    path:"hospitals/:id/delete",
    component: HospitalDeleteComponent
  },
  {
    path:"doctors",
    component: DoctorOverviewComponent
  },
  {
    path:"doctors/create",
    component: DoctorCreateComponent
  },
  {
    path:"doctors/:id",
    component: DoctorDetailsComponent
  },
  {
    path:"doctors/:id/edit",
    component: DoctorEditComponent
  },
  {
    path:"doctors/:id/delete",
    component: DoctorDeleteComponent
  },
  {
    path: "reviews",
    component: ReviewDetailsComponent
  },
  {
    path: "reviews/create",
    component: ReviewCreateComponent
  },
  {
    path: "reviews/edit",
    component: ReviewEditComponent
  },
  {
    path: "reviews/delete",
    component: ReviewDeleteComponent
  },
  {
    path: "register",
    component: RegisterComponent
  },
  {
    path: 'register/:token',
    component: RegisterConfirmComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'logout',
    component: LogoutComponent
  },
  {
    path: 'unauthorized',
    component: UnauthorizedComponent
  },
  {
    path: 'admin',
    component: AdminComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
