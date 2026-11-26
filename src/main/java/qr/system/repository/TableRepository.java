package qr.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qr.system.entity.TableEntity;

@Repository
public interface TableRepository extends JpaRepository<TableEntity, Long> {
}
