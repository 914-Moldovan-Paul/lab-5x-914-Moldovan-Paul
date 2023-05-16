import { Component } from '@angular/core';
import { LoginService } from './services/login.service';
import { UserService } from './services/user-service';
import { Rol } from './model/Rol';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'frontend';
  rol: Rol | null = null;

  constructor(
    private loginService: LoginService,
    private userService: UserService
  ) {
    this.loginService.getUserHandleObservable().subscribe({
      next: (handle) => {
        handle = handle || 'visitor';
        this.userService.getUserRol(handle).subscribe({
          next: (rol) => {
            this.rol = rol;
          },
        });
      },
    });
  }
}
