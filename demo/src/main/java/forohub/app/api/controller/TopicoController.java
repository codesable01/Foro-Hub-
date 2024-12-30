package forohub.app.api.controller;

import forohub.app.api.domain.topico.Topico;
import forohub.app.api.dto.topicodto.DatosActualizarTopico;
import forohub.app.api.dto.topicodto.DatosRegistroTopico;
import forohub.app.api.service.TopicoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;


@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoService topicoService;

    public TopicoController(TopicoService topicoService) {
        this.topicoService = topicoService;
    }

    @PostMapping
    public ResponseEntity<?> crearTopico(@RequestBody @Valid DatosRegistroTopico datos, BindingResult result) {
        // Validación de los campos
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Datos inválidos");
        }

        try {
            Topico topico = topicoService.crearTopico(datos);
            return ResponseEntity.status(201).body(topico);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    // Nuevos endpoints para listar tópicos
    @GetMapping
    public ResponseEntity<Page<Topico>> listarTopicos(
            @PageableDefault(size = 10, sort = "fechaCreacion") Pageable pageable) {
        Page<Topico> topicos = topicoService.listarTopicos(pageable);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/curso/{curso}")
    public ResponseEntity<Page<Topico>> listarTopicosPorCurso(
            @PathVariable String curso,
            @PageableDefault(size = 10, sort = "fechaCreacion") Pageable pageable) {
        Page<Topico> topicos = topicoService.listarTopicosPorCurso(curso, pageable);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/anio/{year}")
    public ResponseEntity<Page<Topico>> listarTopicosPorAnio(
            @PathVariable int year,
            @PageableDefault(size = 10, sort = "fechaCreacion") Pageable pageable) {
        Page<Topico> topicos = topicoService.listarTopicosPorAnio(year, pageable);
        return ResponseEntity.ok(topicos);
    }


     // Método para actualizar un topico
    @PutMapping("/{id}")
    public ResponseEntity<Topico> actualizarTopico(@PathVariable Long id, @RequestBody DatosActualizarTopico datosActualizarTopico) {
        Topico topicoActualizado = topicoService.actualizarTopico(id, datosActualizarTopico);
        return ResponseEntity.ok(topicoActualizado);  // Retorna el topico actualizado
    }


     // Método para eliminar un topico
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id) {
        try {
            topicoService.eliminarTopico(id);
            return ResponseEntity.noContent().build(); // Respuesta HTTP 204
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // Respuesta HTTP 404
        }
    }
}

