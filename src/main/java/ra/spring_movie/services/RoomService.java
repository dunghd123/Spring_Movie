package ra.spring_movie.services;

import ra.spring_movie.dto.response.MessageResponse;

import ra.spring_movie.entity.Room;

import java.util.List;

public interface RoomService {
    MessageResponse insertRoom(Room room);
    MessageResponse updateRoom(Room room);
    MessageResponse deleteRoom(int roomid);
    List<Room> viewRoom();
}
