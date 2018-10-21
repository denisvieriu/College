package ro.ubb.delete.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by radu.
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Discipline {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @OneToMany(mappedBy = "discipline", cascade = CascadeType.ALL, fetch = FetchType.EAGER,
                orphanRemoval = true)
    private Set<StudentDiscipline> studentDisciplines = new HashSet<>();

}
