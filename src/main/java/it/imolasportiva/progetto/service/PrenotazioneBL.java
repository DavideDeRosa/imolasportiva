package it.imolasportiva.progetto.service;

import it.imolasportiva.progetto.dto.PrenotazioneDTO;
import it.imolasportiva.progetto.entity.PrenotazioneEntity;
import it.imolasportiva.progetto.error.ErrorEnum;
import it.imolasportiva.progetto.error.ErrorException;
import it.imolasportiva.progetto.mapper.PrenotazioneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PrenotazioneBL {

    private final PrenotazioneService prenotazioneService;
    private final PrenotazioneMapper prenotazioneMapper;
    @Autowired
    public PrenotazioneBL(PrenotazioneService prenotazioneService, PrenotazioneMapper prenotazioneMapper) {
        this.prenotazioneService = prenotazioneService;
        this.prenotazioneMapper = prenotazioneMapper;
    }

    public PrenotazioneDTO getPrenotazioneDTOById(Long id){
        Optional<PrenotazioneEntity> prenotazione = prenotazioneService.findById(id);
        if(!prenotazione.isPresent()){
            throw new ErrorException(ErrorEnum.PrenotazioneNotFound);
        }

        return prenotazioneMapper.prenotazioneEntityToPrenotazioneDTO(prenotazione.get());
    }

    public PrenotazioneDTO postPrenotazione(PrenotazioneDTO prenotazioneDTO){
        PrenotazioneEntity prenotazioneEntity = prenotazioneMapper.prenotazioneDTOToPrenotazioneEntity(prenotazioneDTO);
        prenotazioneEntity = prenotazioneService.savePrenotazione(prenotazioneEntity);
        return prenotazioneMapper.prenotazioneEntityToPrenotazioneDTO(prenotazioneEntity);
    }

    public PrenotazioneDTO putPrenotazione(Long id, PrenotazioneDTO prenotazioneDTO){
        Optional<PrenotazioneEntity> prenotazione = prenotazioneService.findById(id);
        if(!prenotazione.isPresent()){
            throw new ErrorException(ErrorEnum.PrenotazioneNotFound);
        }

        prenotazioneDTO.setId(id);
        PrenotazioneEntity prenotazioneEntity = prenotazioneMapper.prenotazioneDTOToPrenotazioneEntity(prenotazioneDTO);
        prenotazioneEntity = prenotazioneService.updatePrenotazione(prenotazioneEntity);

        return prenotazioneMapper.prenotazioneEntityToPrenotazioneDTO(prenotazioneEntity);
    }

    public void deletePrenotazione(Long id){
        if(getPrenotazioneDTOById(id) != null){
            prenotazioneService.deletePrenotazione(id);
        }
    }

    public List<PrenotazioneDTO> getPrenotazioni(Integer anno, Integer mese){
        if(anno == null){
            if(mese == null){
                return findAll();
            }

            throw new ErrorException(ErrorEnum.AnnoNotFound);
        }

        if(mese == null){
            return getPrenotazioneAnno(anno);
        }else{
            if(mese <= 0 || mese > 12){
                throw new ErrorException(ErrorEnum.MeseError);
            }

            return getPrenotazioneAnnoMese(anno, mese);
        }
    }

    public List<PrenotazioneDTO> findAll(){
        List<PrenotazioneEntity> prenotazioneEntityList = prenotazioneService.findAll();
        List<PrenotazioneDTO> prenotazioneDTOList = new ArrayList<>();

        for (PrenotazioneEntity x : prenotazioneEntityList) {
            prenotazioneDTOList.add(prenotazioneMapper.prenotazioneEntityToPrenotazioneDTO(x));
        }

        return prenotazioneDTOList;
    }

    public List<PrenotazioneDTO> getPrenotazioneAnno(int anno){
        List<PrenotazioneEntity> prenotazioneEntityList = prenotazioneService.findByYear(anno);
        List<PrenotazioneDTO> prenotazioneDTOList = new ArrayList<>();

        for (PrenotazioneEntity x : prenotazioneEntityList) {
            prenotazioneDTOList.add(prenotazioneMapper.prenotazioneEntityToPrenotazioneDTO(x));
        }

        return prenotazioneDTOList;
    }

    public List<PrenotazioneDTO> getPrenotazioneAnnoMese(int anno, int mese){
        List<PrenotazioneEntity> prenotazioneEntityList = prenotazioneService.findByYearAndMonth(anno, mese);
        List<PrenotazioneDTO> prenotazioneDTOList = new ArrayList<>();

        for (PrenotazioneEntity x : prenotazioneEntityList) {
            prenotazioneDTOList.add(prenotazioneMapper.prenotazioneEntityToPrenotazioneDTO(x));
        }

        return prenotazioneDTOList;
    }
}
