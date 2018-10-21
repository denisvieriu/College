package ro.ubb.tst5.web.converter;

import ro.ubb.tst5.core.model.BaseEntity;
import ro.ubb.tst5.web.dto.BaseDto;

public interface ConverterBaseEntity<Model extends BaseEntity<Long>, Dto extends BaseDto> extends Converter<Model, Dto>{
}
