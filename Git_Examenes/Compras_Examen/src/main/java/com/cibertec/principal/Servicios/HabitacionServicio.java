package com.cibertec.principal.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.principal.entidades.Habitaciones;
import com.cibertec.principal.repositorios.HabitacionRepositorio;

import java.util.List;
import java.util.Optional;

@Service
public class HabitacionServicio {

    private final HabitacionRepositorio habitacionRepository;

    @Autowired
    public HabitacionServicio(HabitacionRepositorio habitacionRepository) {
        this.habitacionRepository = habitacionRepository;
    }

    public List<Habitaciones> getAllHabitaciones() {
        return habitacionRepository.findAll();
    }

    public Optional<Habitaciones> getHabitacionById(Long id) {
        return habitacionRepository.findById(id);
    }

    public Habitaciones saveHabitacion(Habitaciones habitacion) {
        return habitacionRepository.save(habitacion);
    }

    public void deleteHabitacion(Long id) {
        habitacionRepository.deleteById(id);
    }
}

