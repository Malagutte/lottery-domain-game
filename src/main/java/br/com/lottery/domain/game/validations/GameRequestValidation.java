package br.com.lottery.domain.game.validations;

import static org.apache.commons.lang3.StringUtils.equalsAny;

public class GameRequestValidation {

    private GameRequestValidation(){
    }

    public static boolean isValidGetGameRequest(String type, Integer gameNumber) {
        return equalsAny(type.toLowerCase(), "megasena", "lotofacil") && gameNumber > 0;
    }
}
