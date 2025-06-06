package Entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder

public class ArticuloManufacturadoDetalle extends Base {
    private Integer cantidad;
    private ArticuloInsumo articuloInsumo;
}
