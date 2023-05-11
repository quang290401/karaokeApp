/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package KaraokeApp.repository;

import KaraokeApp.model.TypeRoom;
import KaraokeApp.model.roomNo;
import java.util.ArrayList;

/**
 *
 * @author 84374
 */
public class roomRes {
    ArrayList<roomNo> lstRoom;
    
    public roomRes() {
        lstRoom = new ArrayList<>();
    }
    public void addToList(){
        TypeRoomRes roomTypeList = new TypeRoomRes();
        roomTypeList.addIntoList();
       lstRoom.add(new roomNo("PHONG 1",roomTypeList.getLstRoomType().get(2)));
       lstRoom.add(new roomNo("PHONG 2",roomTypeList.getLstRoomType().get(1)));
       lstRoom.add(new roomNo("PHONG 3",roomTypeList.getLstRoomType().get(0)));
       lstRoom.add(new roomNo("PHONG 4",roomTypeList.getLstRoomType().get(1)));
       lstRoom.add(new roomNo("PHONG 5",roomTypeList.getLstRoomType().get(1)));
       lstRoom.add(new roomNo("PHONG 6",roomTypeList.getLstRoomType().get(1)));
       lstRoom.add(new roomNo("PHONG 7",roomTypeList.getLstRoomType().get(0)));
       lstRoom.add(new roomNo("PHONG 8",roomTypeList.getLstRoomType().get(1)));
       lstRoom.add(new roomNo("PHONG 9",roomTypeList.getLstRoomType().get(2)));
       lstRoom.add(new roomNo("PHONG 10",roomTypeList.getLstRoomType().get(2)));
    }

    public ArrayList<roomNo> getLstRoom() {
        return lstRoom;
    }

    public void setLstRoom(ArrayList<roomNo> lstRoom) {
        this.lstRoom = lstRoom;
    }
    
}
