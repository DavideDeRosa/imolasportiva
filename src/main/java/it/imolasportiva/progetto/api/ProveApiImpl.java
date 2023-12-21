package it.imolasportiva.progetto.api;

import imolasportiva.api.ProveApi;
import imolasportiva.model.TestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Controller
@RequestMapping("/api/v1")
public class ProveApiImpl implements ProveApi {

    public ResponseEntity<Object> provaFile(Object format) {
        if ("ics".equals(format)) {
            try {
                log.info("Invocazione provaFile() parte ics");

                ClassPathResource icsFile = new ClassPathResource("icsFiles/invite.ics");

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType("text/calendar"));
                headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=calendar.ics");

                InputStream inputStream = icsFile.getInputStream();
                InputStreamResource resource = new InputStreamResource(inputStream);

                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(headers.getContentLength())
                        .body(resource);
            } catch (IOException e) {
                log.info(e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            log.info("Invocazione provaFile() parte json");

            return ResponseEntity.ok("json richiamato");
        }
    }

    public ResponseEntity<String> provaDiversiFontiDati(String id, String param1, TestDTO test) {
        log.info("Invocazione provaDiverseFonti()");
        return new ResponseEntity<String>("parametro url: " + id + " parametro query: " + param1 + "parametro body: " + test, HttpStatus.OK);
    }
}

