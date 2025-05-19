package br.com.projetos.controller;

import br.com.projetos.services.PersonServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/v1")
public class TestLogController {

    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @GetMapping
    public String test () {

        logger.debug("Debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");

        return "Logs generated successfully!";
    }
}
