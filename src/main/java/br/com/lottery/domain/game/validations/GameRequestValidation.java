package br.com.lottery.domain.game.validations;

import org.apache.commons.lang3.StringUtils;

public class GameRequestValidation {

    public static boolean isValidGetGameRequest(String type, Integer gameNumber) {
        return !StringUtils.isBlank(type) && gameNumber != null && gameNumber > 0;
    }
}
