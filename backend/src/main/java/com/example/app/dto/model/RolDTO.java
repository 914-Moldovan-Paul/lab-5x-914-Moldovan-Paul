package com.example.app.dto.model;

import com.example.app.model.Rol;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RolDTO {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private boolean read_all;
    @Getter
    @Setter
    private boolean update_all;
    @Getter
    @Setter
    private boolean delete_all;
    @Getter
    @Setter
    private boolean read_own;
    @Getter
    @Setter
    private boolean update_own;
    @Getter
    @Setter
    private boolean delete_own;
    @Getter
    @Setter
    private boolean create;

    public static Rol toRol(RolDTO rolDTO) {
        return new Rol(
          rolDTO.getName(),
          rolDTO.read_all,
          rolDTO.update_all,
          rolDTO.delete_all,
          rolDTO.read_own,
          rolDTO.update_own,
          rolDTO.delete_own,
          rolDTO.create
        );
    }

    public static RolDTO fromRol(Rol rol) {
        return new RolDTO(
                rol.getName(),
                rol.isRead_all(),
                rol.isUpdate_all(),
                rol.isDelete_all(),
                rol.isRead_own(),
                rol.isUpdate_own(),
                rol.isDelete_own(),
                rol.isCreat()
        );
    }
}
