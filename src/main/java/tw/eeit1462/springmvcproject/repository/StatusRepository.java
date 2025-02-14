package tw.eeit1462.springmvcproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.eeit1462.springmvcproject.model.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {

	Optional<Status> findByStatusName(String string);

}
