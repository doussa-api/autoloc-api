package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.PaiementDTO;

import java.util.List;

public interface IPaiementService {
    PaiementDTO create(PaiementDTO paiementDTO);
    PaiementDTO findById(Long id);
    List<PaiementDTO> findAll();
    PaiementDTO update(Long id, PaiementDTO paiementDTO);
    void deleteById(Long id);
}
