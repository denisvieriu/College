package ro.ubb.delete.service;

import ro.ubb.delete.model.Student;

import java.util.List;

/**
 * Created by radu.
 */
public interface StudentService {

    public List<Student> findAll();

    public void deleteById(Long studentId);

}
