package com.cibertec.principal.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.principal.entidades.Reservas;
import com.cibertec.principal.repositorios.ReservaRepositorio;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaServicio {

    private final ReservaRepositorio reservaRepository;

    @Autowired
    public ReservaServicio(ReservaRepositorio reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public List<Reservas> getAllReservas() {
        return reservaRepository.findAll();
    }

    public Optional<Reservas> getReservaById(Long id) {
        return reservaRepository.findById(id);
    }

    public Reservas saveReserva(Reservas reserva) {
        return reservaRepository.save(reserva);
    }

    public void deleteReserva(Long id) {
        reservaRepository.deleteById(id);
    }
}

