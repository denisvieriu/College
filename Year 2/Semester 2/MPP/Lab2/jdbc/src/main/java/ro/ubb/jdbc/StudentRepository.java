package ro.ubb.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    private static final String URL = "jdbc:postgresql://localhost:5432/catalogjdbc";
    private static final String user = System.getProperty("user");
    private static final String password = System.getProperty("password");

    public StudentRepository() {
    }

    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String sql = "select * from student";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                int grade = rs.getInt("grade");
                students.add(new Student(id, name, grade));
            }
            return students;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void save(Student student) {
        String sql = "insert into student (name, grade) values (?,?)";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1,student.getName());
            statement.setInt(2,student.getGrade());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void update(Student student){
        String sql = "UPDATE student set name=?, grade=? where id=?";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1,student.getName());
            statement.setInt(2,student.getGrade());
            statement.setLong(3,student.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void deleteById(Long studentId){
        String sql = "DELETE from student where id=?";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1,studentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
