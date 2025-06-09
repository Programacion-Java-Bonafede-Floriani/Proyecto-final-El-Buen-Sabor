package Entities;

import Enums.Rol;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

public class Usuario extends Base {
    private String authId;
    private String username;
    private Rol rol;
}
