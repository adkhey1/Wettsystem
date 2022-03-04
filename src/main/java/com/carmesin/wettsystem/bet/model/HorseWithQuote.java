package com.carmesin.wettsystem.bet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
/**
 * Create HorseModel with Quote Object
 */
public class HorseWithQuote {

    private HorseModel horse;
    private double quote;

}
