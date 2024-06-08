package com.lodoviko.convencao.domain.repository;

import com.lodoviko.convencao.domain.model.Trancode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrancodeRepository extends JpaRepository<Trancode, Long> {

    @Query(nativeQuery = true, value = "select utr.* from tb_utr_trancode utr " +
            "inner join tb_ugr_grupo ugr on (ugr.ugr_sq_grupo = utr.ugr_sq_grupo) and ugr.ugr_is_situacao is true " +
            "inner join tb_uug_usuario_grupo uug on uug.ugr_sq_grupo = ugr.ugr_sq_grupo " +
            "inner join tb_usu_usuario usu on usu.usu_sq_usuario = uug.usu_sq_usuario " +
            "where usu.usu_ds_login like :dsLogin")
    List<Trancode> listarTrancodesUsuario(String dsLogin);
}