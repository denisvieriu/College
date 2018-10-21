package ro.ubb.pr10.web.converter;

import ro.ubb.pr10.core.model.BaseEntity;
import ro.ubb.pr10.web.dto.BaseDto;

public interface ConverterBaseEntity<Model extends BaseEntity<Long>,Dto extends BaseDto> extends Converter<Model,Dto> {
}
