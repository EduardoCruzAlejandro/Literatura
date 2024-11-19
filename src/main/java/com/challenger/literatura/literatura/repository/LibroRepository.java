package com.challenger.literatura.literatura.repository;

import com.challenger.literatura.literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface LibroRepository extends JpaRepository<Libro, Long> {
    @Query("SELECT l FROM Libro l JOIN FETCH l.autores")
    List<Libro> findAllWithAutores();
}