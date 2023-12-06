package it.imolasportiva.progetto.api;

import imolasportiva.api.ProveApi;
import imolasportiva.model.TestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/api/v1")
public class ProveApiImpl implements ProveApi {

    public ResponseEntity<String> provaFile(String format){
        if("ics".equals(format)){
            log.info("Invocazione provaFile() parte ics");
            return new ResponseEntity<String>("ICS content", HttpStatus.OK);
        }else{
            log.info("Invocazione provaFile() parte json");
            return new ResponseEntity<String>("JSON content", HttpStatus.OK);
        }
    }

    public ResponseEntity<String> provaDiversiFontiDati(String id, String param1, TestDTO test){
        log.info("Invocazione provaDiverseFonti()");
        return new ResponseEntity<String>("parametro url: " + id + " parametro query: " + param1 + "parametro body: " + test, HttpStatus.OK);
    }
}

