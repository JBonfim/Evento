package com.eventosapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eventoapp.models.Evento;


public interface EventoRepository extends CrudRepository<Evento, String> {

}
