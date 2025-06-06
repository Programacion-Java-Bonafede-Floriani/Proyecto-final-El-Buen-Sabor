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

public class Articulo extends Base{
    protected String denominacion;
    protected Double precioVenta;
    @Builder.Default
    private List<Imagen> imagenes = new ArrayList<>();
    private UnidadMedida unidadMedida;

}