package Entities;

import Enums.Estado;
import Enums.FormaPago;
import Enums.TipoEnvio;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList; // Importar ArrayList

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

public class Pedido extends Base{
    private LocalTime horaEstimadaFinalizacion;
    private Double total;
    private Double totalCosto;
    private Estado estado;
    private TipoEnvio tipoEnvio;
    private FormaPago formaPago;
    private LocalDate fechaPedido;
    @Builder.Default
    private List<DetallePedido> detallesPedido = new ArrayList<>();
    private Factura factura;
}
