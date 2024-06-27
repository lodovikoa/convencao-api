package com.lodoviko.convencao.domain.repository;

import com.lodoviko.convencao.domain.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

    Boolean existsByDsUf(String dsUf);
    boolean existsByDsNome(String dsNome);
    boolean existsByDsUfAndSqEstadoNot(String dsUf, Long sqEstado);
    boolean existsByDsNomeAndSqEstadoNot(String dsNome, Long sqEstado);
}
