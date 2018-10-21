package ro.ubb.pr5.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ro.ubb.pr5.core.model.BaseEntity;

import javax.transaction.Transactional;
import java.io.Serializable;

@Transactional
@NoRepositoryBean
interface CustomRepository<T extends BaseEntity<ID>, ID extends Serializable> extends JpaRepository<T, ID> {
}
