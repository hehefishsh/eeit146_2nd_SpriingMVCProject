package tw.eeit1462.springmvcproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.eeit1462.springmvcproject.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

}
