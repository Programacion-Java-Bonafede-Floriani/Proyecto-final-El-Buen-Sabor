package Entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)

public class Empresa extends Base {
    private String nombre;
    private String razonSocial;
    private int cuil;
    @Builder.Default
    private List<Sucursal> sucursales = new ArrayList<>();
}
