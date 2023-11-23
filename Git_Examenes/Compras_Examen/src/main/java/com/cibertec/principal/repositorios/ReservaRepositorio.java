package com.cibertec.principal.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.principal.entidades.Reservas;

public interface ReservaRepositorio extends JpaRepository<Reservas, Long> {

}
