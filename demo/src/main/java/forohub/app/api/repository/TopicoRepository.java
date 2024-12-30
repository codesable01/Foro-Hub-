package forohub.app.api.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import forohub.app.api.domain.topico.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    // Métodos relacionados con POST (Creación de datos)
    boolean existsByTituloAndMensaje(String titulo, String mensaje);  // Verifica si existe un registro
    Optional<Topico> findByTituloAndMensaje(String titulo, String mensaje);  // Devuelve el registro si existe

    // Métodos relacionados con GET (Consulta de datos)
    Page<Topico> findByCurso(String curso, Pageable pageable);  // Filtrar por curso
      @Query("SELECT t FROM Topico t WHERE FUNCTION('YEAR', t.fechaCreacion) = :year")
    Page<Topico> findByFechaCreacionYear(int year, Pageable pageable);  // Filtrar por año


    // Método para verificar si el tópico existe por ID
   // Optional<Topico> findById(Long id);  // Verifica si el tópico existe
}
