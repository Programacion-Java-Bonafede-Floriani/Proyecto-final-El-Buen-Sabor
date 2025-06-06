package Entities;

import lombok.*;
import lombok.experimental.SuperBuilder;


import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

public class Categoria extends Base{
    private String denominacion;
    @Builder.Default
    private ArrayList<Articulo> articulos = new ArrayList<>();
    private Categoria categoria;

}