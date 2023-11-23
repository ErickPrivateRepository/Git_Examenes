package com.cibertec.principal.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cibertec.principal.Servicios.ClienteServicio;
import com.cibertec.principal.Servicios.HabitacionServicio;
import com.cibertec.principal.Servicios.ReservaServicio;
import com.cibertec.principal.entidades.Clientes;
import com.cibertec.principal.entidades.Habitaciones;
import com.cibertec.principal.entidades.Reservas;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/compra")
public class CompraControlador {

    private final ClienteServicio clienteServicio;
    private final HabitacionServicio habitacionServicio;
    private final ReservaServicio reservaServicio;

    @Autowired
    public CompraControlador(ClienteServicio clienteServicio, HabitacionServicio habitacionServicio, ReservaServicio reservaServicio) {
        this.clienteServicio = clienteServicio;
        this.habitacionServicio = habitacionServicio;
        this.reservaServicio = reservaServicio;
    }

    /* ------------- Catálogo de Productos ------------- */  
    /*---------------------------------------------------*/
    
    @GetMapping("/catalogo/clientes")
    public List<Clientes> verCatalogoClientes() {
        return clienteServicio.getAllClientes();
    }
    
    /*---------------------------------------------------*/

    @GetMapping("/catalogo/habitaciones")
    public List<Habitaciones> verCatalogoHabitaciones() {
        return habitacionServicio.getAllHabitaciones();
    }
    
    /*---------------------------------------------------*/

    @GetMapping("/catalogo/reservas")
    public List<Reservas> verCatalogoReservas() {
        return reservaServicio.getAllReservas();
    }

    /* ---------- Agregar productos al carrito --------- */
    /*---------------------------------------------------*/
    
    @PostMapping("/agregar-cliente")
    public Clientes agregarCliente(@RequestBody Clientes cliente) {
        return clienteServicio.saveCliente(cliente);
    }

    /*---------------------------------------------------*/
    
    @PostMapping("/agregar-habitacion")
    public Habitaciones agregarHabitacion(@RequestBody Habitaciones habitacion) {
        return habitacionServicio.saveHabitacion(habitacion);
    }
    
    /*---------------------------------------------------*/

    @PostMapping("/agregar-reserva")
    public Reservas agregarReserva(@RequestBody Reservas reserva) {
        return reservaServicio.saveReserva(reserva);
    }
    
    /*------------ Ver el carrito de compras ------------*/
    /*---------------------------------------------------*/   
    @GetMapping("/ver-cliente/{clienteId}")
    public Clientes verCliente(@PathVariable Long clienteId) {
        return clienteServicio.getClienteById(clienteId).orElse(null);
    }

    /*---------------------------------------------------*/
    
    @GetMapping("/ver-habitacion/{habitacionId}")
    public Habitaciones verHabitacion(@PathVariable Long habitacionId) {
        return habitacionServicio.getHabitacionById(habitacionId).orElse(null);
    }

    /*---------------------------------------------------*/
    
    @GetMapping("/ver-reserva/{reservaId}")
    public Reservas verReserva(@PathVariable Long reservaId) {
        return reservaServicio.getReservaById(reservaId).orElse(null);
    }
    
    /*----------- Procesar y Cancelar Compra -----------*/
    /*---------------------------------------------------*/
    
    @PostMapping("/procesar-compra")
    public String procesarCompra() {
        List<Reservas> reservas = reservaServicio.getAllReservas();
        for (Reservas reserva : reservas) {
            double totalCompra = calcularTotalCompra(reserva);

            boolean pagoExitoso = procesarPago(reserva.getCliente(), totalCompra);

            if (pagoExitoso) {
                reserva.setEstado("Confirmada");
                actualizarInventario(reserva);
                reservaServicio.saveReserva(reserva);
            } 
            else {
                return "Error - La compra no puedo ser procesada";
            }
        }
        return "Compra procesada con exito";
    }

    /*---------------------------------------------------*/
    
    @PostMapping("/cancelar-compra/{reservaId}")
    public String cancelarCompra(@PathVariable Long reservaId) {
        Optional<Reservas> optionalReserva = reservaServicio.getReservaById(reservaId);
        if (optionalReserva.isPresent()) {
            Reservas reserva = optionalReserva.get();

            liberarInventario(reserva);

            reserva.setEstado("Cancelada");
            reservaServicio.saveReserva(reserva);

            return "Compra cancelada con exito";
        } 
        else {
            return "La reserva con ID " + reservaId + " no se encontró";
        }
    }
    
    /*---------------------------------------------------*/

    private double calcularTotalCompra(Reservas reserva) {
        return reserva.getHabitacion().getPrecio(); 
    }
    
    /*---------------------------------------------------*/

    private boolean procesarPago(Clientes cliente, double totalCompra) {
        return true;
    }
    
    /*---------------------------------------------------*/

    private void actualizarInventario(Reservas reserva) {
        Habitaciones habitacion = reserva.getHabitacion();
        int stockActual = habitacion.getStock();
        if (stockActual > 0) {
            habitacion.setStock(stockActual - 1);
            habitacionServicio.saveHabitacion(habitacion);
        }
    }
    
    /*---------------------------------------------------*/

    private void liberarInventario(Reservas reserva) {
        Habitaciones habitacion = reserva.getHabitacion();
        habitacion.setStock(habitacion.getStock() + 1);
        habitacionServicio.saveHabitacion(habitacion);
    }

}



