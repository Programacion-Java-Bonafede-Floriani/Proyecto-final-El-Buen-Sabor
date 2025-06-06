import DAO.UsuarioDAO;
import DAO.UsuarioDAOImpl;
import Entities.*; // Importa todas las entidades
import Enums.*;   // Importa todos los enums

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.*;
import java.util.List;

public class Main {
    String url = "jdbc:mysql://localhost:3306/buen_sabor?useSSL=false&serverTimezone=UTC";
    String usuario = "root"; // o el usuario que tengan
    String clave = "";       // clave de MySQL (por defecto en XAMPP puede estar vacía)

    public static void main(String[] args) {
        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/buen_sabor?useSSL=false&serverTimezone=UTC", "root", "")) {
            System.out.println("✅ Conexion exitosa a la base de datos 'buen_sabor'!");

            UsuarioDAO usuarioDAO = new UsuarioDAOImpl(conexion);

            System.out.println("----------------------------------------------");
            System.out.println("Iniciando Sistema 'El Buen Sabor'");
            System.out.println("----------------------------------------------");

            //  Instanciar Países, Provincias, Localidades y Domicilios
            System.out.println("\n--- Creacion de Ubicaciones y Domicilios ---");

            // Países
            Pais argentina = Pais.builder().id(1L).nombre("Argentina").build();

            // Provincias
            Provincia mendoza = Provincia.builder().id(3L).nombre("Mendoza").pais(argentina).build();

            // Localidades
            Localidad maipu = Localidad.builder().id(6L).nombre("Maipu").provincia(mendoza).build();
            Localidad capitalMza = Localidad.builder().id(7L).nombre("Mendoza Capital").provincia(mendoza).build();

            // Domicilios
            Domicilio domSucursal = Domicilio.builder().id(10L).calle("Av. San Martin").numero(1234).cp(5500).localidad(capitalMza).build();
            Domicilio domSucursal1 = Domicilio.builder().id(11L).calle("Sarmiento").numero(567).cp(5515).localidad(maipu).build();
            Domicilio domCliente1 = Domicilio.builder().id(12L).calle("Las Heras").numero(789).cp(5500).localidad(capitalMza).build();
            Domicilio domCliente2 = Domicilio.builder().id(13L).calle("Belgrano").numero(100).cp(5500).localidad(capitalMza).build();

            System.out.println("Pais: " + argentina.getNombre());
            System.out.println("Provincia: " + mendoza.getNombre());
            System.out.println("Localidad: " + maipu.getNombre());


            //  Instanciar Empresa y Sucursales
            System.out.println("\n--- Empresa y sus Sucursales ---");
            Empresa elBuenSabor = Empresa.builder().id(14L)
                    .nombre("El Buen Sabor S.A.")
                    .razonSocial("Comidas Rápidas y Gourmet")
                    .cuil(2030123456)
                    .build();

            Sucursal sucursalMaipu = Sucursal.builder().id(15L)
                    .nombre("Sucursal Maipu")
                    .horarioApertura(LocalTime.of(11, 30))
                    .horarioCierre(LocalTime.of(23, 30))
                    .domicilio(domSucursal1)
                    .build();

            Sucursal sucursalCapital = Sucursal.builder().id(16L)
                    .nombre("Sucursal Capital")
                    .horarioApertura(LocalTime.of(11, 0))
                    .horarioCierre(LocalTime.of(23, 30))
                    .domicilio(domSucursal) // O un domicilio específico para esta sucursal
                    .build();

            elBuenSabor.getSucursales().add(sucursalMaipu);
            elBuenSabor.getSucursales().add(sucursalCapital);

            System.out.println("Empresa: " + elBuenSabor.getNombre());

            System.out.println("Sucursal de Ciudad: " + sucursalCapital.getNombre());
            System.out.println("Domicilio Sucursal Ciudad: " + domSucursal.getCalle() + domSucursal.getNumero());
            System.out.println("Horario de atencion: " + sucursalCapital.getHorarioApertura() + " a " + sucursalCapital.getHorarioCierre());

            System.out.println("Sucursal de Maipu: " + sucursalMaipu.getNombre());
            System.out.println("Domicilio Sucursal Maipu: " + domSucursal1.getCalle() + domSucursal1.getNumero());
            System.out.println("Horario de atencion: " + sucursalMaipu.getHorarioApertura() + " a " + sucursalMaipu.getHorarioCierre());

            System.out.println("Sucursales de " + elBuenSabor.getNombre() + ": " + elBuenSabor.getSucursales().size());


            // Instanciar Unidades de Medida y Categorías

            UnidadMedida unidadGramo = UnidadMedida.builder().id(17L).denominacion("Gramo").build();
            UnidadMedida unidadUnidad = UnidadMedida.builder().id(18L).denominacion("Unidad").build();
            UnidadMedida unidadMililitro = UnidadMedida.builder().id(19L).denominacion("Mililitro").build();

            Categoria categoriaComida = Categoria.builder().id(20L).denominacion("Platos principales").build();
            Categoria categoriaBebida = Categoria.builder().id(21L).denominacion("Bebidas").build();
            Categoria categoriaIngrediente = Categoria.builder().id(22L).denominacion("Ingredientes").build();
            Categoria categoriaPostre = Categoria.builder().id(23L).denominacion("Postres").categoria(categoriaComida).build(); // Subcategoría

            sucursalMaipu.getCategorias().add(categoriaComida);
            sucursalMaipu.getCategorias().add(categoriaBebida);
            sucursalCapital.getCategorias().add(categoriaComida);
            sucursalCapital.getCategorias().add(categoriaIngrediente);


            // Instanciar Artículos (Insumos y Manufacturados)
            // Artículos Insumo
            ArticuloInsumo harina = ArticuloInsumo.builder().id(24L).denominacion("Harina 000").precioVenta(50.0).unidadMedida(unidadGramo)
                    .precioCompra(30.0).stockActual(10000).stockMaximo(20000).esParaElaborar(true).build();
            ArticuloInsumo carne = ArticuloInsumo.builder().id(25L).denominacion("Carne Picada").precioVenta(300.0).unidadMedida(unidadGramo)
                    .precioCompra(250.0).stockActual(50000).stockMaximo(100000).esParaElaborar(true).build();
            ArticuloInsumo CocaCola = ArticuloInsumo.builder().id(26L).denominacion("Coca Cola 500ml").precioVenta(800.0).unidadMedida(unidadMililitro)
                    .precioCompra(400.0).stockActual(100).stockMaximo(200).esParaElaborar(false).build();
            ArticuloInsumo queso = ArticuloInsumo.builder().id(27L).denominacion("Queso Muzzarella").precioVenta(400.0).unidadMedida(unidadGramo)
                    .precioCompra(350.0).stockActual(20000).stockMaximo(30000).esParaElaborar(true).build();
            ArticuloInsumo pan = ArticuloInsumo.builder().id(28L).denominacion("Pan de Hamburguesa").precioVenta(100.0).unidadMedida(unidadUnidad)
                    .precioCompra(70.0).stockActual(500).stockMaximo(1000).esParaElaborar(true).build();

            categoriaIngrediente.getArticulos().add(harina);
            categoriaIngrediente.getArticulos().add(carne);
            categoriaBebida.getArticulos().add(CocaCola);
            categoriaIngrediente.getArticulos().add(queso);
            categoriaIngrediente.getArticulos().add(pan);

            // Artículos Manufacturados
            ArticuloManufacturado hamburguesaSimple = ArticuloManufacturado.builder().id(29L).denominacion("Hamburguesa Simple").precioVenta(3500.0).unidadMedida(unidadUnidad)
                    .descripcion("Hamburguesa de carne con queso y lechuga").tiempoEstimadoMinutos(15)
                    .preparacion("Cocinar carne, armar y servir.").build();
            ArticuloManufacturado pizzaMuzzarella = ArticuloManufacturado.builder().id(30L).denominacion("Pizza Muzzarella").precioVenta(4000.0).unidadMedida(unidadUnidad)
                    .descripcion("Pizza con salsa de tomate y abundante muzzarella").tiempoEstimadoMinutos(25)
                    .preparacion("Estirar masa, agregar ingredientes, hornear.").build();
            ArticuloManufacturado heladoArtesanal = ArticuloManufacturado.builder().id(31L).denominacion("Helado Artesanal").precioVenta(2000.0).unidadMedida(unidadUnidad)
                    .descripcion("Helado de vainilla y chocolate").tiempoEstimadoMinutos(5)
                    .preparacion("Servir bochas de helado.").build();

            categoriaComida.getArticulos().add(hamburguesaSimple);
            categoriaComida.getArticulos().add(pizzaMuzzarella);
            categoriaPostre.getArticulos().add(heladoArtesanal);

            // Detalles de Artículos Manufacturados
            ArticuloManufacturadoDetalle detHamburguesa1 = ArticuloManufacturadoDetalle.builder().id(32L).cantidad(200).articuloInsumo(carne).build();
            ArticuloManufacturadoDetalle detHamburguesa2 = ArticuloManufacturadoDetalle.builder().id(33L).cantidad(50).articuloInsumo(queso).build();
            ArticuloManufacturadoDetalle detHamburguesa3 = ArticuloManufacturadoDetalle.builder().id(34L).cantidad(2).articuloInsumo(pan).build();

            hamburguesaSimple.getDetalles().add(detHamburguesa1);
            hamburguesaSimple.getDetalles().add(detHamburguesa2);
            hamburguesaSimple.getDetalles().add(detHamburguesa3);

            ArticuloManufacturadoDetalle detPizza1 = ArticuloManufacturadoDetalle.builder().id(35L).cantidad(300).articuloInsumo(harina).build();
            ArticuloManufacturadoDetalle detPizza2 = ArticuloManufacturadoDetalle.builder().id(36L).cantidad(250).articuloInsumo(queso).build();

            pizzaMuzzarella.getDetalles().add(detPizza1);
            pizzaMuzzarella.getDetalles().add(detPizza2);

            // Instanciar Usuarios y Clientes
            System.out.println("\n--- Usuarios y Clientes ---");
            // Usuarios
            Usuario userCliente1 = Usuario.builder().id(37L).authId("auth0|12345").username("juan.perez").rol(Rol.cliente).build();
            Usuario userCliente2 = Usuario.builder().id(50L).authId("auth0|65845").username("maria.lopez").rol(Rol.cliente).build();
            Usuario userEmpleado1 = Usuario.builder().id(38L).authId("auth0|67890").username("ana.gomez").rol(Rol.empleado).build();
            Usuario userAdmin1 = Usuario.builder().id(39L).authId("auth0|admin").username("root").rol(Rol.admin).build();

            // Clientes
            Imagen imgCliente1 = Imagen.builder().id(40L).denominacion("foto_juan.jpg").build();
            Cliente cliente1 = Cliente.builder().id(41L)
                    .nombre("Juan").apellido("Perez")
                    .telefono("2615551234").email("juan.perez@example.com")
                    .fechaNacimiento(LocalDate.of(1990, 5, 15))
                    .imagen(imgCliente1).usuario(userCliente1).build();
            cliente1.agregarDomicilio(domCliente1);

            Imagen imgCliente2 = Imagen.builder().id(40L).denominacion("foto_maria.jpg").build();
            Cliente cliente2 = Cliente.builder().id(41L)
                    .nombre("Maria").apellido("Lopez")
                    .telefono("2615875964").email("maria.lopez@example.com")
                    .fechaNacimiento(LocalDate.of(2000, 11, 17))
                    .imagen(imgCliente2).usuario(userCliente2).build();
            cliente2.agregarDomicilio(domCliente2);

            System.out.println("Cliente 1: " + cliente1.getNombre() + " " + cliente1.getApellido());
            System.out.println("Datos de Cliente 1: telefono:  " + cliente1.getTelefono() + ". Email: " + cliente1.getEmail());
            System.out.println("Domicilio: " + domCliente1.getCalle() + " " + domCliente1.getNumero() + " " + domCliente1.getLocalidad().getNombre());

            System.out.println("Cliente 2: " + cliente2.getNombre() + " " + cliente2.getApellido());
            System.out.println("Datos de Cliente 2: telefono:  " + cliente2.getTelefono() + ". Email: " + cliente2.getEmail());
            System.out.println("Domicilio: " + domCliente2.getCalle() + " " + domCliente2.getNumero() + " " + domCliente2.getLocalidad().getNombre());

            // añadir nuevo usuario
            Usuario nuevoUsuarioCliente = Usuario.builder()
                    .authId("auth0|98765")
                    .username("Melina.Floriani")
                    .rol(Rol.cliente)
                    .build();
            System.out.println("Nuevo usuario creado en memoria: " + nuevoUsuarioCliente.getUsername());

            try {
                //inserta nuevo usuario
                usuarioDAO.save(nuevoUsuarioCliente);

            } catch (SQLException e) {
                System.err.println("Error al insertar usuarios. Podrían ya existir o la tabla no coincide: " + e.getMessage());
            }

            System.out.println("\n--- Obteniendo Usuarios desde la Base de Datos usando DAO ---");
            List<Usuario> usuariosDesdeDB = usuarioDAO.findAll(); // ¡Llamada al DAO!
            if (usuariosDesdeDB.isEmpty()) {
                System.out.println("No se encontraron usuarios en la base de datos.");
            } else {
                for (Usuario user : usuariosDesdeDB) {
                    System.out.println("Usuario ID: " + user.getId() +
                            ", AuthID: " + user.getAuthId() +
                            ", Username: " + user.getUsername() +
                            ", Rol: " + user.getRol());
                }
            }

            // Instanciar Promociones
            Promocion happyHourHamburguesas = Promocion.builder().id(42L)
                    .denominacion("Happy Hour Hamburguesas")
                    .fechaDesde(LocalDate.of(2025, 6, 1)).fechaHasta(LocalDate.of(2025, 6, 30))
                    .horaDesde(LocalTime.of(17, 0)).horaHasta(LocalTime.of(19, 0))
                    .descripcionDescuento("50% de descuento en todas las hamburguesas")
                    .precioPromocional(1750.0).tipoPromocion(TipoPromocion.happyHour).build();
            happyHourHamburguesas.getArticulos().add(hamburguesaSimple);

            Promocion promoPizzaBebida = Promocion.builder().id(43L)
                    .denominacion("Combo Pizza + Bebida")
                    .fechaDesde(LocalDate.of(2025, 6, 5)).fechaHasta(LocalDate.of(2025, 6, 20))
                    .horaDesde(LocalTime.of(12, 0)).horaHasta(LocalTime.of(22, 0))
                    .descripcionDescuento("Pizza Muzzarella + Coca Cola 500ml")
                    .precioPromocional(4500.0).tipoPromocion(TipoPromocion.promocion1).build();
            promoPizzaBebida.getArticulos().add(pizzaMuzzarella);
            promoPizzaBebida.getArticulos().add(CocaCola);

            sucursalMaipu.getPedidos().add(Pedido.builder().id(99L).build());
            sucursalMaipu.getPedidos().add(Pedido.builder().id(100L).build());

            System.out.println("\n--- Promociones ---");
            System.out.println("Promocion 1: " + happyHourHamburguesas.getDenominacion());
            System.out.println("Descripcion Promo 1: " + happyHourHamburguesas.getDescripcionDescuento());
            System.out.println("Promocion 2: " + promoPizzaBebida.getDenominacion());
            System.out.println("Descripcion Promo 2: " + promoPizzaBebida.getDescripcionDescuento());

            System.out.println("\n--- Carta ---");
            System.out.println(categoriaComida.getDenominacion());
            System.out.println(hamburguesaSimple.getDenominacion() + ": " + hamburguesaSimple.getDescripcion() + ". Precio $" + hamburguesaSimple.getPrecioVenta());
            System.out.println(pizzaMuzzarella.getDenominacion() + ": " + pizzaMuzzarella.getDescripcion() + ". Precio $" + pizzaMuzzarella.getPrecioVenta());
            System.out.println(categoriaPostre.getDenominacion());
            System.out.println(heladoArtesanal.getDenominacion() + ": " + heladoArtesanal.getDescripcion() + ". Precio $" + heladoArtesanal.getPrecioVenta());
            System.out.println(categoriaBebida.getDenominacion());
            System.out.println(CocaCola.getDenominacion() + ": " + ". Precio $" + CocaCola.getPrecioVenta());

            //  Instanciar Pedidos y Detalles de Pedido
            System.out.println("\n--- Pedidos y Facturas ---");

            // Factura (instanciado antes del pedido para la relación)
            Factura factura1 = Factura.builder().id(44L)
                    .fechaFacturacion(LocalDate.now())
                    .mpPaymentId(1001).mpMerchantOrderId("ORD-XYZ-001").mpPreferenceId("PREF-ABC-001")
                    .mpPaymentType("Tarjeta de Crédito").formaPago(FormaPago.MercadoPago)
                    .totalVenta(4300.0).build();

            Pedido pedido1 = Pedido.builder().id(46L)
                    .horaEstimadaFinalizacion(LocalTime.of(20, 30))
                    .total(4300.0).totalCosto(2900.0)
                    .estado(Estado.pendiente).tipoEnvio(TipoEnvio.delivery)
                    .formaPago(FormaPago.MercadoPago).fechaPedido(LocalDate.now())
                    .factura(factura1).build();

            DetallePedido dp1_1 = DetallePedido.builder().id(47L).cantidad(1).subTotal(hamburguesaSimple.getPrecioVenta()).articulo(hamburguesaSimple).build();
            DetallePedido dp1_2 = DetallePedido.builder().id(48L).cantidad(1).subTotal(CocaCola.getPrecioVenta()).articulo(CocaCola).build();
            pedido1.getDetallesPedido().add(dp1_1);
            pedido1.getDetallesPedido().add(dp1_2);

            cliente2.getPedidos().add(pedido1); // Añado pedido al cliente
            sucursalMaipu.getPedidos().add(pedido1); // Añado pedido a la sucursal

            Factura factura2 = Factura.builder().id(45L)
                    .fechaFacturacion(LocalDate.now())
                    .mpPaymentId(1003).mpMerchantOrderId("ORD-XYZ-003").mpPreferenceId("PREF-ABC-003")
                    .mpPaymentType("Efectivo").formaPago(FormaPago.efectivo)
                    .totalVenta(2000.0).build();

            Pedido pedido2 = Pedido.builder().id(46L)
                    .horaEstimadaFinalizacion(LocalTime.of(20, 35))
                    .total(2000.0).totalCosto(1000.0)
                    .estado(Estado.pendiente).tipoEnvio(TipoEnvio.TakeAway)
                    .formaPago(FormaPago.efectivo).fechaPedido(LocalDate.now())
                    .factura(factura2).build();

            DetallePedido dp2_1 = DetallePedido.builder().id(47L).cantidad(1).subTotal(heladoArtesanal.getPrecioVenta()).articulo(heladoArtesanal).build();
            pedido2.getDetallesPedido().add(dp2_1);

            cliente2.getPedidos().add(pedido2);
            sucursalMaipu.getPedidos().add(pedido2);

            Factura factura3 = Factura.builder().id(45L)
                    .fechaFacturacion(LocalDate.now())
                    .mpPaymentId(1004).mpMerchantOrderId("ORD-XYZ-004").mpPreferenceId("PREF-ABC-004")
                    .mpPaymentType("Efectivo").formaPago(FormaPago.efectivo)
                    .totalVenta(4800.0).build();

            Pedido pedido3 = Pedido.builder().id(46L)
                    .horaEstimadaFinalizacion(LocalTime.of(20, 50))
                    .total(4800.0).totalCosto(2900.0)
                    .estado(Estado.preparacion).tipoEnvio(TipoEnvio.TakeAway)
                    .formaPago(FormaPago.efectivo).fechaPedido(LocalDate.now())
                    .factura(factura3).build();

            DetallePedido dp3_1 = DetallePedido.builder().id(47L).cantidad(1).subTotal(pizzaMuzzarella.getPrecioVenta()).articulo(pizzaMuzzarella).build();
            DetallePedido dp3_2 = DetallePedido.builder().id(48L).cantidad(1).subTotal(CocaCola.getPrecioVenta()).articulo(CocaCola).build();
            pedido3.getDetallesPedido().add(dp3_1);
            pedido3.getDetallesPedido().add(dp3_2);

            cliente2.getPedidos().add(pedido3); // Añadir pedido al cliente
            sucursalCapital.getPedidos().add(pedido3);

            System.out.println("Pedido 1: ");
            System.out.println("Cliente: " + cliente1.getNombre() + " " + cliente1.getApellido());
            System.out.println("Detalle Pedido : " + dp1_1.getArticulo().getDenominacion() + dp1_1.getArticulo().getDenominacion());
            System.out.println("Total: $" + pedido1.getTotal());
            System.out.println("Tiempo estimado de entrega: " + pedido1.getHoraEstimadaFinalizacion());
            System.out.println("Forma de entrega: " + pedido1.getTipoEnvio());
            System.out.println("Forma de pago: " + pedido1.getFormaPago());
            System.out.println("Estado pedido: " + pedido1.getEstado());
            System.out.println("Sucursal: " + sucursalMaipu.getNombre());
            System.out.println("Factura N°: " + pedido1.getFactura().getMpPaymentId());

            System.out.println("\n----------------------------------------------");

            System.out.println("Pedido 2: ");
            System.out.println("Cliente: " + cliente2.getNombre() + " " + cliente2.getApellido());
            System.out.println("Detalle Pedido : " + dp2_1.getArticulo().getDenominacion());
            System.out.println("Total: $" + pedido2.getTotal());
            System.out.println("Tiempo estimado de entrega: " + pedido2.getHoraEstimadaFinalizacion());
            System.out.println("Forma de entrega: " + pedido2.getTipoEnvio());
            System.out.println("Forma de pago: " + pedido2.getFormaPago());
            System.out.println("Estado pedido: " + pedido2.getEstado());
            System.out.println("Sucursal: " + sucursalMaipu.getNombre());
            System.out.println("Factura N°: " + pedido2.getFactura().getMpPaymentId());

            System.out.println("\n----------------------------------------------");

            System.out.println("Pedido 3: ");
            System.out.println("Cliente: " + cliente2.getNombre() + " " + cliente2.getApellido());
            System.out.println("Detalle Pedido : " + dp3_1.getArticulo().getDenominacion() + ", " + dp3_2.getArticulo().getDenominacion());
            System.out.println("Total: $" + pedido3.getTotal());
            System.out.println("Tiempo estimado de entrega: " + pedido3.getHoraEstimadaFinalizacion());
            System.out.println("Forma de entrega: " + pedido3.getTipoEnvio());
            System.out.println("Forma de pago: " + pedido3.getFormaPago());
            System.out.println("Estado pedido: " + pedido3.getEstado());
            System.out.println("Sucursal: " + sucursalCapital.getNombre());
            System.out.println("Factura N°: " + pedido3.getFactura().getMpPaymentId());

                } catch (SQLException e) {
                    System.err.println("❌ Error de SQL al intentar conectar o operar con la BD: " + e.getMessage());
                    e.printStackTrace();
                } catch (Exception e) {
                    System.err.println("❌ Ocurrió un error inesperado durante la ejecución: " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    System.out.println("\n----------------------------------------------");
                    System.out.println("Sistema Buen Sabor: Ejecución finalizada.");
                    System.out.println("----------------------------------------------");
                }
    }
}

