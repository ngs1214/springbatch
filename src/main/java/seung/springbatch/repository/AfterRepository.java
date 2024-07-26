package seung.springbatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seung.springbatch.entity.AfterEntity;

public interface AfterRepository extends JpaRepository<AfterEntity, Long> {
}
