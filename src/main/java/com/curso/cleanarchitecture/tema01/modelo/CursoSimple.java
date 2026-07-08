package com.curso.cleanarchitecture.tema01.modelo;

/**
 * Modelo ANÉMICO de un curso: solo datos, cero comportamiento.
 *
 * <p><b>Capa que representa:</b> ninguna en concreto, y ese es justo el problema.
 * En el ejemplo acoplado del Tema 1 esta clase hace de "entidad", de "fila de
 * tabla" y de "DTO de respuesta" a la vez. Cuando una misma clase sirve para
 * todo, ninguna capa tiene un modelo propio y todas quedan acopladas entre sí.</p>
 *
 * <p><b>Qué enseña:</b> el modelo anémico. Compárese con la entidad {@code Curso}
 * de la sección 15 de la teoría, que expone {@code validarPuedeRecibirInscripcion()}
 * y {@code ocuparPlaza()}. Aquí no hay ningún método de negocio, así que las
 * reglas "curso cerrado no acepta inscripciones" y "no superar las plazas"
 * tendrán que vivir FUERA de esta clase, en quien la use. En el Tema 1 acabarán
 * dentro de un controlador REST, que es el peor sitio posible.</p>
 *
 * <p><b>Dependencias:</b> ninguna, es Java puro. Eso está bien (el dominio debe
 * poder entenderse sin frameworks, Advertencia 3 de la teoría); lo que está mal
 * es que no protege nada.</p>
 */
public class CursoSimple {

    private Long id;
    private String nombre;

    // Dato crítico de negocio: de él depende la regla "no superar las plazas".
    // Al ser un simple int con getter/setter, la clase no puede impedir que
    // alguien lo ponga a -3 desde fuera. La invariante no está protegida.
    private int plazasDisponibles;

    // Otro dato crítico: la regla "un curso cerrado no acepta inscripciones"
    // depende de este boolean, pero la decisión de respetarla queda en manos
    // del código cliente. La entidad no puede defenderse a sí misma.
    private boolean cerrado;

    /**
     * Constructor vacío: típico de clases pensadas para frameworks (JPA,
     * Jackson...). Permite crear un curso "a medio construir", sin nombre ni
     * plazas, algo que en el negocio real no tiene sentido.
     */
    public CursoSimple() {
    }

    public CursoSimple(Long id, String nombre, int plazasDisponibles, boolean cerrado) {
        this.id = id;
        this.nombre = nombre;
        this.plazasDisponibles = plazasDisponibles;
        this.cerrado = cerrado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPlazasDisponibles() {
        return plazasDisponibles;
    }

    // PROBLEMA: este setter es la puerta por la que la regla de negocio se
    // escapa de la entidad. En el controlador acoplado veremos un
    // curso.setPlazasDisponibles(curso.getPlazasDisponibles() - 1),
    // es decir, la operación de negocio "ocupar una plaza" hecha a mano y sin
    // validación desde una clase externa. En la versión limpia esto será un
    // método ocuparPlaza() que valida antes de restar.
    public void setPlazasDisponibles(int plazasDisponibles) {
        this.plazasDisponibles = plazasDisponibles;
    }

    public boolean isCerrado() {
        return cerrado;
    }

    // PROBLEMA: cerrar o abrir un curso es una decisión de negocio (¿se puede
    // reabrir un curso ya empezado?), pero aquí es un simple flag que cualquiera
    // puede cambiar sin condiciones.
    public void setCerrado(boolean cerrado) {
        this.cerrado = cerrado;
    }
}
