package ro.ubb.tstt.web.converter;

import ro.ubb.tstt.core.model.BaseEntity;
import ro.ubb.tstt.web.dto.BaseDto;

public interface ConverterBaseEntity<Model extends BaseEntity<Long>,Dto extends BaseDto> extends Converter<Model,Dto> {
}
