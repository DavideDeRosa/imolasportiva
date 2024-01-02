package it.imolasportiva.progetto.service;

import it.imolasportiva.progetto.dto.PrenotazioneDTO;
import it.imolasportiva.progetto.entity.CampoEntity;
import it.imolasportiva.progetto.entity.PrenotazioneEntity;
import it.imolasportiva.progetto.error.ErrorEnum;
import it.imolasportiva.progetto.error.ErrorException;
import it.imolasportiva.progetto.mapper.PrenotazioneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PrenotazioneBL {

    private final PrenotazioneService prenotazioneService;
    private final PrenotazioneMapper prenotazioneMapper;
    private final CampoService campoService;

    @Autowired
    public PrenotazioneBL(PrenotazioneService prenotazioneService, PrenotazioneMapper prenotazioneMapper, CampoService campoService) {
        this.prenotazioneService = prenotazioneService;
        this.prenotazioneMapper = prenotazioneMapper;
        this.campoService = campoService;
    }

    public PrenotazioneDTO getPrenotazioneDTOById(Long id) {
        Optional<PrenotazioneEntity> prenotazione = prenotazioneService.findById(id);
        if (!prenotazione.isPresent()) {
            throw new ErrorException(ErrorEnum.PRENOTAZIONENOTFOUND);
        }

        return prenotazioneMapper.prenotazioneEntityToPrenotazioneDTO(prenotazione.get());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public PrenotazioneDTO postPrenotazione(PrenotazioneDTO prenotazioneDTO) {
        PrenotazioneEntity prenotazioneEntity = prenotazioneMapper.prenotazioneDTOToPrenotazioneEntity(prenotazioneDTO);
        prenotazioneEntity = prenotazioneService.savePrenotazione(prenotazioneEntity);
        return prenotazioneMapper.prenotazioneEntityToPrenotazioneDTO(prenotazioneEntity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public PrenotazioneDTO putPrenotazione(Long id, PrenotazioneDTO prenotazioneDTO) {
        Optional<PrenotazioneEntity> prenotazione = prenotazioneService.findById(id);
        if (!prenotazione.isPresent()) {
            throw new ErrorException(ErrorEnum.PRENOTAZIONENOTFOUND);
        }

        prenotazioneDTO.setId(id);
        PrenotazioneEntity prenotazioneEntity = prenotazioneMapper.prenotazioneDTOToPrenotazioneEntity(prenotazioneDTO);
        prenotazioneEntity = prenotazioneService.updatePrenotazione(prenotazioneEntity);

        return prenotazioneMapper.prenotazioneEntityToPrenotazioneDTO(prenotazioneEntity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void deletePrenotazione(Long id) {
        if (getPrenotazioneDTOById(id) != null) {
            prenotazioneService.deletePrenotazione(id);
        }
    }

    public List<PrenotazioneDTO> getPrenotazioni(Integer anno, Integer mese) {
        if (anno == null) {
            if (mese == null) {
                return findAll();
            }

            throw new ErrorException(ErrorEnum.ANNONOTFOUND);
        }

        if (mese == null) {
            return getPrenotazioneAnno(anno);
        } else {
            if (mese <= 0 || mese > 12) {
                throw new ErrorException(ErrorEnum.MESEERROR);
            }

            return getPrenotazioneAnnoMese(anno, mese);
        }
    }

    public List<PrenotazioneDTO> findAll() {
        List<PrenotazioneEntity> prenotazioneEntityList = prenotazioneService.findAll();
        List<PrenotazioneDTO> prenotazioneDTOList = new ArrayList<>();

        for (PrenotazioneEntity x : prenotazioneEntityList) {
            prenotazioneDTOList.add(prenotazioneMapper.prenotazioneEntityToPrenotazioneDTO(x));
        }

        return prenotazioneDTOList;
    }

    public List<PrenotazioneDTO> getPrenotazioneAnno(int anno) {
        List<PrenotazioneEntity> prenotazioneEntityList = prenotazioneService.findByYear(anno);
        List<PrenotazioneDTO> prenotazioneDTOList = new ArrayList<>();

        for (PrenotazioneEntity x : prenotazioneEntityList) {
            prenotazioneDTOList.add(prenotazioneMapper.prenotazioneEntityToPrenotazioneDTO(x));
        }

        return prenotazioneDTOList;
    }

    public List<PrenotazioneDTO> getPrenotazioneAnnoMese(int anno, int mese) {
        List<PrenotazioneEntity> prenotazioneEntityList = prenotazioneService.findByYearAndMonth(anno, mese);
        List<PrenotazioneDTO> prenotazioneDTOList = new ArrayList<>();

        for (PrenotazioneEntity x : prenotazioneEntityList) {
            prenotazioneDTOList.add(prenotazioneMapper.prenotazioneEntityToPrenotazioneDTO(x));
        }

        return prenotazioneDTOList;
    }

    public PrenotazioneDTO validPrenotazionePost(PrenotazioneDTO prenotazioneDTO) {
        checkDataENumeroP(prenotazioneDTO);

        if (prenotazioneDTO.getCampo() == null) {
            List<CampoEntity> campoList;

            if (prenotazioneDTO.getNumeroPartecipanti() == 10) {
                campoList = campoService.findCampiLiberiPost(prenotazioneDTO.getDataPrenotazione(), prenotazioneDTO.getDataFinePrenotazione(), "Calcio");
            } else {
                campoList = campoService.findCampiLiberiPost(prenotazioneDTO.getDataPrenotazione(), prenotazioneDTO.getDataFinePrenotazione(), "Tennis");
            }

            if (campoList.isEmpty()) {
                throw new ErrorException(ErrorEnum.CAMPONOTAVAILABLE);
            }

            prenotazioneDTO.setCampo(campoList.get(0));
        } else {
            if (!prenotazioneService.findCampoOccupatoPost(prenotazioneDTO.getCampo().getId(), prenotazioneDTO.getDataPrenotazione(), prenotazioneDTO.getDataFinePrenotazione()).isEmpty()) {
                throw new ErrorException(ErrorEnum.CAMPONOTAVAILABLE);
            } else {
                checkCampoTipo(prenotazioneDTO);
            }
        }

        return prenotazioneDTO;
    }

    public PrenotazioneDTO validPrenotazionePut(PrenotazioneDTO prenotazioneDTO, Long idPrenotazione) {
        checkDataENumeroP(prenotazioneDTO);

        if (prenotazioneDTO.getCampo() == null) {
            List<CampoEntity> campoList;

            if (prenotazioneDTO.getNumeroPartecipanti() == 10) {
                campoList = campoService.findCampiLiberiPut(prenotazioneDTO.getDataPrenotazione(), prenotazioneDTO.getDataFinePrenotazione(), "Calcio", idPrenotazione);
            } else {
                campoList = campoService.findCampiLiberiPut(prenotazioneDTO.getDataPrenotazione(), prenotazioneDTO.getDataFinePrenotazione(), "Tennis", idPrenotazione);
            }

            if (campoList.isEmpty()) {
                throw new ErrorException(ErrorEnum.CAMPONOTAVAILABLE);
            }

            prenotazioneDTO.setCampo(campoList.get(0));
        } else {
            if (!prenotazioneService.findCampoOccupatoPut(prenotazioneDTO.getCampo().getId(), prenotazioneDTO.getDataPrenotazione(), prenotazioneDTO.getDataFinePrenotazione(), idPrenotazione).isEmpty()) {
                throw new ErrorException(ErrorEnum.CAMPONOTAVAILABLE);
            } else {
                checkCampoTipo(prenotazioneDTO);
            }
        }

        return prenotazioneDTO;
    }

    private void checkDataENumeroP(PrenotazioneDTO prenotazioneDTO) {
        if (prenotazioneDTO.getDataPrenotazione().getDayOfWeek() == DayOfWeek.TUESDAY ||
                prenotazioneDTO.getDataPrenotazione().getHour() < 7 ||
                prenotazioneDTO.getDataPrenotazione().isBefore(LocalDateTime.now()) ||
                prenotazioneDTO.getDataPrenotazione().getHour() + prenotazioneDTO.getDurataPrenotazioneOre() > 23) {
            throw new ErrorException(ErrorEnum.PRENOTAZIONEWRONGTIME);
        }

        if (prenotazioneDTO.getNumeroPartecipanti() != 10 &&
                prenotazioneDTO.getNumeroPartecipanti() != 2 &&
                prenotazioneDTO.getNumeroPartecipanti() != 4) {
            throw new ErrorException(ErrorEnum.NUMEROPARTECIPANTIERROR);
        }
    }

    private void checkCampoTipo(PrenotazioneDTO prenotazioneDTO) {
        CampoEntity campoEntity = campoService.findById(prenotazioneDTO.getCampo().getId()).get();
        int numeroPrenotati = prenotazioneDTO.getNumeroPartecipanti();

        if (numeroPrenotati == 10) {
            if (!campoEntity.getTipologia().equals("Calcio")) {
                throw new ErrorException(ErrorEnum.CAMPOERROR);
            }
        } else {
            if (!campoEntity.getTipologia().equals("Tennis")) {
                throw new ErrorException(ErrorEnum.CAMPOERROR);
            }
        }
    }
}
