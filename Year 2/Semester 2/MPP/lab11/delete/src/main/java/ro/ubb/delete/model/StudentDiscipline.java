package ro.ubb.delete.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import java.util.Objects;

/**
 * Created by radu.
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@IdClass(StudentDisciplinePK.class)
@Getter
@Setter
@Builder
public class StudentDiscipline {

    @Id
    @ManyToOne
    private Student student;

    @Id
    @ManyToOne
    private Discipline discipline;

    private Integer grade;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDiscipline that = (StudentDiscipline) o;
        return Objects.equals(student.getId(), that.student.getId()) &&
                Objects.equals(discipline.getId(), that.discipline.getId()) &&
                Objects.equals(grade, that.grade);
    }

    @Override
    public int hashCode() {

        return Objects.hash(student.getId(), discipline.getId(), grade);
    }

    @Override
    public String toString() {
        return "StudentDiscipline{" +
                "student=" + student.getId() +
                ", discipline=" + discipline.getId() +
                ", grade=" + grade +
                '}';
    }
}
