package ro.ubb.lazy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.lazy.model.BaseEntity;

import java.io.Serializable;

/**
 * Created by radu.
 */
@NoRepositoryBean
@Transactional
public interface CatalogRepository<Entity extends BaseEntity<ID>, ID extends Serializable>
        extends JpaRepository<Entity, ID> {
}
