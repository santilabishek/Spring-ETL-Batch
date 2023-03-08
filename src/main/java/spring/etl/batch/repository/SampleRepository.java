package spring.etl.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.etl.batch.model.Sample;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Integer> {

}
