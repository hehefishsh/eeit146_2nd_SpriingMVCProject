package tw.eeit1462.springmvcproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tw.eeit1462.springmvcproject.model.Room;
import tw.eeit1462.springmvcproject.service.RoomService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    // 顯示所有會議室
    @GetMapping
    public String getAllRooms(Model model) {
        List<Room> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms); 
        return "meetingroom";
    }

    
    // 顯示新增會議室表單
    @GetMapping("/new")
    public String newRoomForm(Model model) {
        model.addAttribute("room", new Room());
        return "roomform";
    }

    // 新增或更新會議室
    @PostMapping("/save")
    public String saveRoom(@ModelAttribute Room room, Model model) {
        if (room == null || room.getRoomName() == null || room.getRoomName().isEmpty()) {
            model.addAttribute("errorMessage", "會議室名稱不能為空");
            return "roomform"; 
        }

        roomService.saveRoom(room);
        return "redirect:/rooms"; 
    }

    // 刪除會議室
    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Integer id, Model model) {
        Optional<Room> optionalRoom = roomService.getRoomById(id);

        if (optionalRoom.isPresent()) {
            roomService.deleteRoom(id);
        } else {
            model.addAttribute("errorMessage", "找不到該會議室");
            return "rooms"; 
        }
        return "redirect:/rooms"; 
    }
}
