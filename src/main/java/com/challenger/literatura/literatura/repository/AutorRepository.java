package com.challenger.literatura.literatura.repository;

import com.challenger.literatura.literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AutorRepository extends JpaRepository<Autor, Long> {

    Autor findByNombre(String nombre);
}