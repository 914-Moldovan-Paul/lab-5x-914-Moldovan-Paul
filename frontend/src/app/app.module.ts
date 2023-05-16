import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { UsersOverviewComponent } from './components/users/users-overview/users-overview.component';
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import { UserDetailsComponent } from './components/users/user-details/user-details.component';
import { UserEditComponent } from './components/users/user-edit/user-edit.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DoctorRatingStatisticComponent } from './components/doctors/doctor-rating-statistic/doctor-rating-statistic.component';
import { UserDeleteComponent } from './components/users/user-delete/user-delete.component';
import { PaginationComponent } from './components/reusable/pagination/pagination.component';
import { HospitalOverviewComponent } from './components/hospitals/hospital-overview/hospital-overview.component';
import { HospitalCreateComponent } from './components/hospitals/hospital-create/hospital-create.component';
import { HospitalDetailsComponent } from './components/hospitals/hospital-details/hospital-details.component';
import { HospitalEditComponent } from './components/hospitals/hospital-edit/hospital-edit.component';
import { HospitalDeleteComponent } from './components/hospitals/hospital-delete/hospital-delete.component';
import { DoctorOverviewComponent } from './components/doctors/doctor-overview/doctor-overview.component';
import { DoctorDetailsComponent } from './components/doctors/doctor-details/doctor-details.component';
import { DoctorCreateComponent } from './components/doctors/doctor-create/doctor-create.component';
import { DoctorEditComponent } from './components/doctors/doctor-edit/doctor-edit.component';
import { DoctorDeleteComponent } from './components/doctors/doctor-delete/doctor-delete.component';
import { UsersOverviewPageComponent } from './components/users/users-overview-page/users-overview-page.component';
import { HospitalOverviewPageComponent } from './components/hospitals/hospital-overview-page/hospital-overview-page.component';
import { DoctorOverviewPageComponent } from './components/doctors/doctor-overview-page/doctor-overview-page.component';
import { ReviewsOverviewPageComponent } from './components/reviews/reviews-overview-page/reviews-overview-page.component';
import { AbstractOverviewPageComponent } from './components/abstract/abstract-overview-page/abstract-overview-page.component';
import { ReviewDetailsComponent } from './components/reviews/review-details/review-details.component';
import { ReviewCreateComponent } from './components/reviews/review-create/review-create.component';
import { ReviewEditComponent } from './components/reviews/review-edit/review-edit.component';
import { ReviewDeleteComponent } from './components/reviews/review-delete/review-delete.component';
import { HospitalCountStatisticComponent } from './components/hospitals/hospital-count-statistic/hospital-count-statistic.component';
import { UserReviewCountPageComponent } from './components/users/user-review-count-page/user-review-count-page.component';
import { HospitalDoctorCountPageComponent } from './components/hospitals/hospital-doctor-count-page/hospital-doctor-count-page.component';
import { DoctorRatingPageComponent } from './components/doctors/doctor-rating-page/doctor-rating-page.component';
import { DoctorRatingWithUsersPageComponent } from './components/doctors/doctor-rating-with-users-page/doctor-rating-with-users-page.component';
import { LoginStatusComponent } from './components/reusable/login-status/login-status.component';
import { RegisterComponent } from './components/login/register/register.component';
import { RegisterConfirmComponent } from './components/login/register-confirm/register-confirm.component';
import { LoginComponent } from './components/login/login/login.component';
import { LogoutComponent } from './components/login/logout/logout.component';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { AbstractPageContainerComponent } from './components/abstract/abstract-page-container/abstract-page-container.component';
import { UnauthorizedComponent } from './components/unauthorized/unauthorized.component';
import { UnauthorizedRedirectionInterceptor } from './interceptors/unauthorized-redirection.interceptor';
import { AdminComponent } from './components/admin/admin.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    UsersOverviewComponent,
    UserDetailsComponent,
    UserEditComponent,
    DoctorRatingStatisticComponent,
    UserDeleteComponent,
    PaginationComponent,
    HospitalOverviewComponent,
    HospitalCreateComponent,
    HospitalDetailsComponent,
    HospitalEditComponent,
    HospitalDeleteComponent,
    DoctorOverviewComponent,
    DoctorDetailsComponent,
    DoctorCreateComponent,
    DoctorEditComponent,
    DoctorDeleteComponent,
    UsersOverviewPageComponent,
    HospitalOverviewPageComponent,
    DoctorOverviewPageComponent,
    ReviewsOverviewPageComponent,
    AbstractOverviewPageComponent,
    ReviewDetailsComponent,
    ReviewCreateComponent,
    ReviewEditComponent,
    ReviewDeleteComponent,
    HospitalCountStatisticComponent,
    UserReviewCountPageComponent,
    HospitalDoctorCountPageComponent,
    DoctorRatingPageComponent,
    DoctorRatingWithUsersPageComponent,
    LoginStatusComponent,
    RegisterComponent,
    RegisterConfirmComponent,
    LoginComponent,
    LogoutComponent,
    UnauthorizedComponent,
    AdminComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: UnauthorizedRedirectionInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
