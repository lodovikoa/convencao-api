package com.lodoviko.convencao.domain.service;

import com.lodoviko.convencao.domain.model.Trancode;
import com.lodoviko.convencao.domain.repository.TrancodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrancodeService {

    @Autowired
    private TrancodeRepository trancodeRepository;

    public List<Trancode> listarTrancodesUsuario(String dsUsuario) {
        return trancodeRepository.listarTrancodesUsuario(dsUsuario);
    }
}
