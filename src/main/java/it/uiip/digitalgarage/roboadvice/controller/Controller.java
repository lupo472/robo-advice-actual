package it.uiip.digitalgarage.roboadvice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Luca on 28/02/2017.
 */

@RestController
public class Controller {

    @RequestMapping(value="/ciao")
    public Boolean test() {

        return true;
    }
}
