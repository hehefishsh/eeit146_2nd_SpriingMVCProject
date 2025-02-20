package tw.eeit1462.springmvcproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.eeit1462.springmvcproject.model.Type;

public interface TypeRepository extends JpaRepository<Type, Integer> {

	Optional<Type> findByTypeName(String exceptionMessage);
}