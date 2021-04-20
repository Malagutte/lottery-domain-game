package br.com.lottery.domain.game.dtos.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SearchRequestDTO {

    private Collection<String> types;

    private Collection<String> fields;

    private LocalDateTime from;

    private LocalDateTime to;

    private String fieldSort;
    private Sort.Direction directionSort;


    public Query buildQuery() {
        final var query = new Query();

        if (types != null && !types.isEmpty())
            query.addCriteria(Criteria.where("type").in(types));

        if (fields != null && !fields.isEmpty())
            fields.forEach(f -> query.fields().include(f));

        if (from != null && to != null)
            query.addCriteria(Criteria.where("date").gte(from).lte(to));
        else if (from != null)
            query.addCriteria(Criteria.where("date").gte(from));
        else if (to != null)
            query.addCriteria(Criteria.where("date").lte(to));

        if (directionSort != null && StringUtils.isNotBlank(fieldSort))
            query.with(Sort.by(directionSort, fieldSort));
        else if (StringUtils.isNotBlank(fieldSort))
            query.with(Sort.by(fieldSort));

        return query;
    }
}
