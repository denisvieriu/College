package ro.ubb.pr10.web.converter;

public interface Converter<Model, Dto> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);
}
