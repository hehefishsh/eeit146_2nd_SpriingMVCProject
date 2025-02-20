package tw.eeit1462.springmvcproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.eeit1462.springmvcproject.model.Room;
import tw.eeit1462.springmvcproject.repository.RoomRepository;

@Service
public class RoomService {
	
	@Autowired
	private RoomRepository roomRepository;


    // 查所有會議室
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // ID查會議室
    public Optional<Room> getRoomById(Integer id) {
        return roomRepository.findById(id);
    }

    // 新增會議室
    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    // 刪除會議室
    public void deleteRoom(Integer id) {
        roomRepository.deleteById(id);
    }
}


