package it.imolasportiva.progetto.service;

import it.imolasportiva.progetto.dto.PrenotazioneDTO;
import it.imolasportiva.progetto.entity.CampoEntity;
import it.imolasportiva.progetto.entity.PrenotazioneEntity;
import it.imolasportiva.progetto.error.ErrorEnum;
import it.imolasportiva.progetto.error.ErrorException;
import it.imolasportiva.progetto.mapper.PrenotazioneMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
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

    public PrenotazioneDTO validPrenotazione(PrenotazioneDTO prenotazioneDTO){
        Calendar c = Calendar.getInstance();
        c.setTime(prenotazioneDTO.getDataPrenotazione());
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());

        if(c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY || c.get(Calendar.HOUR_OF_DAY) < 8 || c.before(now)
                || c.get(Calendar.HOUR_OF_DAY) + prenotazioneDTO.getDurataPrenotazioneOre() > 24){
            throw new ErrorException(ErrorEnum.PrenotazioneWrongTime);
        }

        if(prenotazioneDTO.getNumeroPartecipanti() != 10 &&
                prenotazioneDTO.getNumeroPartecipanti() != 2 &&
                prenotazioneDTO.getNumeroPartecipanti() != 4){
            throw new ErrorException(ErrorEnum.NumeroPartecipantiError);
        }

        if(prenotazioneDTO.getCampo() == null){
            List<CampoEntity> campoList;

            if(prenotazioneDTO.getNumeroPartecipanti() == 10){
                campoList = campoService.findCampiLiberi(prenotazioneDTO.getDataPrenotazione(), "Calcio", prenotazioneDTO.getDurataPrenotazioneOre());
            }else{
                campoList = campoService.findCampiLiberi(prenotazioneDTO.getDataPrenotazione(), "Tennis", prenotazioneDTO.getDurataPrenotazioneOre());
            }

            if(campoList.isEmpty()){
                throw new ErrorException(ErrorEnum.CampoNotAvailable);
            }

            prenotazioneDTO.setCampo(campoList.get(0));
        }else{
            if(!prenotazioneService.findCampoOccupato(prenotazioneDTO.getCampo().getId(), prenotazioneDTO.getDataPrenotazione(), prenotazioneDTO.getDurataPrenotazioneOre()).isEmpty()){
                log.info(prenotazioneDTO.getCampo().getId().toString());
                throw new ErrorException(ErrorEnum.CampoNotAvailable);
            }else{
                CampoEntity campoEntity = campoService.findById(prenotazioneDTO.getCampo().getId()).get();
                int numeroPrenotati = prenotazioneDTO.getNumeroPartecipanti();

                if(numeroPrenotati == 10){
                    if(!campoEntity.getTipologia().equals("Calcio")){
                        throw new ErrorException(ErrorEnum.CampoError);
                    }
                }else{
                    if(!campoEntity.getTipologia().equals("Tennis")){
                        throw new ErrorException(ErrorEnum.CampoError);
                    }
                }
            }
        }

        return prenotazioneDTO;
    }
}
