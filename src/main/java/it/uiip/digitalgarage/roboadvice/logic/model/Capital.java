package it.uiip.digitalgarage.roboadvice.logic.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

public @Data class Capital {

    private Long id;
    private Long idUser;
    private BigDecimal amount;
    private LocalDate date;

}
