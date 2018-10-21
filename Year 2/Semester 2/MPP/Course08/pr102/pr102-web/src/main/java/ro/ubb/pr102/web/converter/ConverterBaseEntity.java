package ro.ubb.pr102.web.converter;


import ro.ubb.pr102.core.model.BaseEntity;
import ro.ubb.pr102.web.dto.BaseDto;

public interface ConverterBaseEntity<Model extends BaseEntity<Long>,Dto extends BaseDto> extends Converter<Model,Dto> {
}
