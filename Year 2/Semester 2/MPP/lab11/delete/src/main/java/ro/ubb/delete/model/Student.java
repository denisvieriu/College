package ro.ubb.delete.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by radu.
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Student {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER,
            orphanRemoval = true)
    private Set<StudentDiscipline> studentDisciplines = new HashSet<>();

    public Set<Discipline> getDisciplines() {
        return studentDisciplines.stream()
                .map(sd -> sd.getDiscipline())
                .collect(Collectors.toSet());
    }

}
