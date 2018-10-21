package ro.ubb.pr102.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ro.ubb.pr102.core.model.BaseEntity;

import javax.transaction.Transactional;
import java.io.Serializable;

@Transactional
@NoRepositoryBean
public interface CustomRepository<T extends BaseEntity<ID>, ID extends Serializable> extends JpaRepository<T, ID> {
}
