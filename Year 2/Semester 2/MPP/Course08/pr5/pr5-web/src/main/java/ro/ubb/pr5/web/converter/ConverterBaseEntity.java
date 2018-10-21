package ro.ubb.pr5.web.converter;

import ro.ubb.pr5.core.model.BaseEntity;
import ro.ubb.pr5.web.dto.BaseDto;

public interface ConverterBaseEntity<Model extends BaseEntity<Long>, Dto extends BaseDto> extends Converter<Model, Dto>{
}
