package ro.ubb.jdbc;

public class Main {
    public static void main(String[] args) {
        StudentRepository studentRepository = new StudentRepository();

        studentRepository.findAll().forEach(System.out::println);

        studentRepository.save(new Student(null, "sins", 9));

        studentRepository.update(new Student(1l, "sup", 9));//!!! id

        studentRepository.deleteById(1l);//!!!! id

        System.out.println("hello");
    }
}
