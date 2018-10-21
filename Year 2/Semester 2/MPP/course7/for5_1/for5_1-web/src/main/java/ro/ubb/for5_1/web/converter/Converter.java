package ro.ubb.for5_1.web.converter;

public interface Converter<Model, Dto> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);
}
