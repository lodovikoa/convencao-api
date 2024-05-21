package com.lodoviko.convencao.domain.repository;

import com.lodoviko.convencao.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByDsLogin(String dsLogin);

    @Query("select u from Usuario u where u.dsLogin like :dsLogin")
    Usuario findPorLogin(String dsLogin);

}
