package site.brainbrain.iqtest.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import site.brainbrain.iqtest.domain.entity.Testee;

public interface TesteeRepository extends JpaRepository<Testee, Long> {
}
