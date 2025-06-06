package Entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

public class DetallePedido extends Base {
    private Integer cantidad;
    private Double subTotal;
    private Articulo articulo;
}
