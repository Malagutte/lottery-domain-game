package br.com.lottery.domain.game.entities.converter;

import br.com.lottery.domain.game.dtos.response.CaixaGovResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.Collection;

@Converter(autoApply = true)
public class DetailsObject implements AttributeConverter<CaixaGovResponse, String> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(CaixaGovResponse data) {
        try {
            return OBJECT_MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public CaixaGovResponse convertToEntityAttribute(String dbData) {
        try {
            return OBJECT_MAPPER.readValue(dbData, CaixaGovResponse.class);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}