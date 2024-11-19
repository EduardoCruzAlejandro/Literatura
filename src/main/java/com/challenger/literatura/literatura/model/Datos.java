<<<<<<< HEAD
package com.challenger.literatura.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos(
       @JsonAlias("results") List<DatosLibro> datos
) {
}
=======
package com.challenger.literatura.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos(
       @JsonAlias("results") List<DatosLibro> datos
) {
}
>>>>>>> 9ed73d94a27d4af91ed1b6fa3c2491d71764ac91
