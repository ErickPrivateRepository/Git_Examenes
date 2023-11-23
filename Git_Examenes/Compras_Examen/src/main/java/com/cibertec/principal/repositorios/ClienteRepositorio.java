package com.cibertec.principal.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.principal.entidades.Clientes;

public interface ClienteRepositorio extends JpaRepository<Clientes, Long> {

}
