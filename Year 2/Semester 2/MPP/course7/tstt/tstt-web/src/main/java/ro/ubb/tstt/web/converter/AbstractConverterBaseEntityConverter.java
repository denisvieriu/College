package ro.ubb.tstt.web.converter;

import ro.ubb.tstt.core.model.BaseEntity;
import ro.ubb.tstt.web.dto.BaseDto;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractConverterBaseEntityConverter<Model extends BaseEntity<Long>,Dto extends BaseDto>
        extends AbstractConverter<Model,Dto> implements ConverterBaseEntity<Model,Dto>{

    public Set<Long> convertModelsToIDs(Set<Model> models){
        return models.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toSet());
    }

    public Set<Long> convertDtosToIDs(Set<Dto> dtos){
        return dtos.stream()
                .map(BaseDto::getId)
                .collect(Collectors.toSet());
    }
}