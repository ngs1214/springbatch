package seung.springbatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seung.springbatch.entity.BeforeEntity;

public interface BeforeRepository extends JpaRepository<BeforeEntity, Long> {
}
