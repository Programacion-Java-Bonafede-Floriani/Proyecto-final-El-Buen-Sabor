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

public class ArticuloManufacturado extends Articulo {
    private String descripcion;
    private Integer tiempoEstimadoMinutos;
    private String preparacion;
    @Builder.Default
    private List<ArticuloManufacturadoDetalle> detalles = new ArrayList<>();

}