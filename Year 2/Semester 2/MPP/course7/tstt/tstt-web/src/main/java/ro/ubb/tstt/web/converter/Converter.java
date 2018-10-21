package ro.ubb.tstt.web.converter;

public interface Converter<Model, Dto> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);
}
