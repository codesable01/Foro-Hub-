# Información sobre el método `findByTituloAndMensaje`

Este método busca un registro en la base de datos que coincida con el título y mensaje proporcionados.

## Parámetros:
- **titulo**: El título del tópico a buscar.
- **mensaje**: El mensaje del tópico a buscar.

## Retorno:
- **Optional<Topico>**: Contiene el registro encontrado si existe, o un `Optional` vacío si no.

### Usar este método cuando:
- Necesitas recuperar los datos completos del registro que coincide con el título y mensaje.
- Deseas retornar el objeto existente o trabajar con sus propiedades.

### Ejemplo de uso:
```java
Optional<Topico> topico = topicoRepository.findByTituloAndMensaje("Título", "Mensaje");
topico.ifPresent(System.out::println);
