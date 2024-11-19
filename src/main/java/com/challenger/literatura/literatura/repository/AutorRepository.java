<<<<<<< HEAD
package com.challenger.literatura.literatura.repository;

import com.challenger.literatura.literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AutorRepository extends JpaRepository<Autor, Long> {

    Autor findByNombre(String nombre);
}
=======
package com.challenger.literatura.literatura.repository;

import com.challenger.literatura.literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AutorRepository extends JpaRepository<Autor, Long> {

    Autor findByNombre(String nombre);
}
>>>>>>> 9ed73d94a27d4af91ed1b6fa3c2491d71764ac91
