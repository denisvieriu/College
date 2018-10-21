package ro.ubb.spring3.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.spring3.jpa.domain.BaseEntity;

import java.io.Serializable;

/**
 * Created by radu.
 */
@NoRepositoryBean
@Transactional
public interface CatalogRepository<T extends BaseEntity<ID>, ID extends Serializable>
        extends JpaRepository<T, ID> {


}
