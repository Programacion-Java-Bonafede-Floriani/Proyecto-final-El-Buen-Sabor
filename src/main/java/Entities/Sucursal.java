package Entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList; // Importar ArrayList

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Sucursal extends Base {
    private String nombre;
    private LocalTime horarioApertura;
    private LocalTime horarioCierre;
    private Domicilio domicilio;
    @Builder.Default
    private List<Pedido> pedidos = new ArrayList<>();
    @Builder.Default
    private List<Categoria> categorias = new ArrayList<>();
    private Empresa empresa;
}
