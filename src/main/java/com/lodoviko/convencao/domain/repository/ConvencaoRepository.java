package com.lodoviko.convencao.domain.repository;

import com.lodoviko.convencao.domain.model.Convencao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConvencaoRepository extends JpaRepository<Convencao, Long> {

    boolean existsByDsReduzidoAndSqConvencaoNot(String dsReduzido, Long sqConvencao);
    boolean existsByDsConvencaoAndSqConvencaoNot(String dsReduzido, Long sqConvencao);
}
