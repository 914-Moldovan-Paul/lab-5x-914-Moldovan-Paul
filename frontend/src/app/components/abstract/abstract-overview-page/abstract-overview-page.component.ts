import { Component, Input } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user-service';
import { Rol } from 'src/app/model/Rol';

@Component({
  selector: 'app-abstract-overview-page',
  templateUrl: './abstract-overview-page.component.html',
  styleUrls: ['./abstract-overview-page.component.css'],
})
export class AbstractOverviewPageComponent<TData> {
  @Input() data: TData[] = [];
  @Input() currentPage: number = 0;
  @Input() currentSize: number = 10;
  @Input() actionable: boolean = false;
  protected rol: Rol | null = null;

  constructor(
    private loginService: LoginService,
    private userService: UserService
  ) {
    loginService.getUserHandleObservable().subscribe({
      next: (handle) => {
        userService.getUserRol(handle || 'visitor').subscribe({
          next: (rol) => {
            this.rol = rol;
          },
        });
      },
    });
  }
}
