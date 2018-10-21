package ro.ubb.for5_1.web.converter;

import ro.ubb.for5_1.core.model.BaseEntity;
import ro.ubb.for5_1.web.dto.BaseDto;

public interface ConverterBaseEntity<Model extends BaseEntity<Long>, Dto extends BaseDto> extends Converter<Model, Dto>{
}
