package forohub.app.api.service;

import forohub.app.api.domain.topico.Topico;
import forohub.app.api.dto.topicodto.DatosRegistroTopico;
import forohub.app.api.dto.topicodto.DatosActualizarTopico;

import forohub.app.api.repository.TopicoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;

    public TopicoService(TopicoRepository topicoRepository) {
        this.topicoRepository = topicoRepository;
    }

    @Transactional
    public Topico crearTopico(DatosRegistroTopico datos) {
        // Verificamos si ya existe un tópico con el mismo título y mensaje
        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            throw new RuntimeException("Ya existe un tópico con el mismo título y mensaje");
        }
        
        // Transformación: convertimos el DTO a una entidad
        Topico topico = new Topico(datos);
        
        // Guardamos el tópico en la base de datos
        return topicoRepository.save(topico);
    }

    public Topico obtenerTopicoPorTituloYMensaje(String titulo, String mensaje) {
        // Buscamos el tópico por título y mensaje
        Optional<Topico> topicoOpt = topicoRepository.findByTituloAndMensaje(titulo, mensaje);
        
        if (topicoOpt.isEmpty()) {
            throw new RuntimeException("Tópico no encontrado");
        }
        
        // Retornamos el tópico encontrado
        return topicoOpt.get();
    }



         // Métodos relacionados con GET (Consulta de datos)

     // Nuevos métodos para listar tópicos
     public Page<Topico> listarTopicos(Pageable pageable) {
        return topicoRepository.findAll(pageable);
    }

    public Page<Topico> listarTopicosPorCurso(String curso, Pageable pageable) {
        return topicoRepository.findByCurso(curso, pageable);
    }

    public Page<Topico> listarTopicosPorAnio(int year, Pageable pageable) {
        return topicoRepository.findByFechaCreacionYear(year, pageable);
    }



    public Topico actualizarTopico(Long id, DatosActualizarTopico datosActualizarTopico) {
        // Buscar el topico por su id
        Topico topico = topicoRepository.findById(id).orElseThrow(() -> new RuntimeException("Topico no encontrado"));
        
        // Actualizar los datos del topico
        topico.actualizarDatos(datosActualizarTopico);
        
        // Guardar el topico actualizado
        return topicoRepository.save(topico);
    }




    public void eliminarTopico(Long id) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isPresent()) {
            topicoRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Tópico no encontrado con ID: " + id);
        }
    }
}

