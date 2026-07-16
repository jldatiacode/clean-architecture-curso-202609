package com.curso.cleanarchitecture.tema06.memory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.curso.cleanarchitecture.tema06.base.Curso;
import com.curso.cleanarchitecture.tema06.base.CursoRepositoryPort;

@Repository
@Profile("memory")
public class CursoRepositoryInMemoryAdapter implements CursoRepositoryPort {

    private final Map<Long, Curso> cursos = new HashMap<>();

    public CursoRepositoryInMemoryAdapter() {
        cursos.put(
            1L,
            new Curso(1L, "Clean Architecture con Java", 20, false)
        );
    }

    @Override
    public Optional<Curso> buscarPorId(Long cursoId) {
        return Optional.ofNullable(cursos.get(cursoId));
    }

	@Override
	public void guardar(Curso curso) {
		cursos.put(curso.getId(), curso);
		
	}

}