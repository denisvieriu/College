package ro.ubb.catalog.core.validators;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ro.ubb.catalog.core.model.Student;

/**
 * Created by radu.
 */
@Component
public class StudentValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Student.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
//        Student student = (Student) target;
//        if (StringUtils.isEmpty(student.getSerialNumber())) {
//            errors.rejectValue("serialNumber", "serialNumber.empty");
//        }
        ValidationUtils.rejectIfEmpty(errors, "serialNumber", "serialNumber is empty");
    }
}
