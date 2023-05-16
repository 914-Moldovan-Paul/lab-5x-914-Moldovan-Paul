export interface Rol {
  name: string;
  read_all: boolean;
  update_all: boolean;
  delete_all: boolean;
  read_own: boolean;
  update_own: boolean;
  delete_own: boolean;
  create: boolean;
}
