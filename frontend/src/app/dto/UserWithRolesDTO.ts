import { User } from '../model/User';
import { Rol } from '../model/Rol';

export interface UserWithRolsDTO {
  user: User;
  rol: Rol;
}
