package ro.ubb.for5_1.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.for5_1.core.model.BaseEntity;

import java.io.Serializable;

@Transactional
@NoRepositoryBean
public interface CustomRepository<T extends BaseEntity<ID>, ID extends Serializable> extends JpaRepository<T, ID> {
}
