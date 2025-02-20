package tw.eeit1462.springmvcproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.eeit1462.springmvcproject.model.Position;

public interface PositionRepository extends JpaRepository<Position, Integer> {

}
