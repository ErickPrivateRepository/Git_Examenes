package com.cibertec.principal.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.principal.entidades.Clientes;
import com.cibertec.principal.repositorios.ClienteRepositorio;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServicio {

    private final ClienteRepositorio clienteRepository;

    @Autowired
    public ClienteServicio(ClienteRepositorio clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Clientes> getAllClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Clientes> getClienteById(Long id) {
        return clienteRepository.findById(id);
    }

    public Clientes saveCliente(Clientes cliente) {
        return clienteRepository.save(cliente);
    }

    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}

