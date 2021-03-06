package edu.eci.arsw.Coronavirus.controller;

import edu.eci.arsw.Coronavirus.cache.CoronavirusException;
import edu.eci.arsw.Coronavirus.services.CoronavirusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/coronavirus")
public class CoronavirusAPIController {

    @Autowired
    CoronavirusService css;

    /**
     * Regresa las regiones de un pais que han sido infectadas
     * @param country nombre del pais que se quiere consultar
     * @return regiones del pais especificado
     */
    @GetMapping("/{country}")
    public ResponseEntity<?> countryByName(@PathVariable String country){
        try {
            return new ResponseEntity<>(css.countryByName(country), HttpStatus.ACCEPTED);
        } catch (CoronavirusException e) {
            Logger.getLogger(CoronavirusAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Regresa el total de los casos de un pais
     * @param country nombre del pais
     * @return total de casos del pais especificado
     */
    @GetMapping("/info/{country}")
    public ResponseEntity<?> infoCountry(@PathVariable String country){
        try{
            return new ResponseEntity<>(css.infoCountry(country), HttpStatus.ACCEPTED);
        }catch(CoronavirusException e){
            Logger.getLogger(CoronavirusAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retorna todos los paises que tienen personas infectadas con el coronavirus
     * @return todos los paises
     */
    @GetMapping("")
    public ResponseEntity<?> countries(){
        try {
            return new ResponseEntity<>(css.countries(), HttpStatus.ACCEPTED);
        } catch (CoronavirusException e) {
            Logger.getLogger(CoronavirusAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}
