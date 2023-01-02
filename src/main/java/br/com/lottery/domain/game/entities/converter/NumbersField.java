package br.com.lottery.domain.game.entities.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


import java.io.IOException;
import java.util.Collection;

@Converter(autoApply = true)
public class NumbersField implements AttributeConverter<Collection<Integer>, String> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Collection<Integer> numbers) {
        try {
            return OBJECT_MAPPER.writeValueAsString(numbers);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Collection<Integer> convertToEntityAttribute(String dbData) {
        try {
            var typeRef = OBJECT_MAPPER.getTypeFactory()
                    .constructCollectionType(Collection.class, Integer.class);
            return OBJECT_MAPPER.readValue(dbData, typeRef);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}