package cn.edu.nju.controller;

import cn.edu.nju.dao.HotelRepository;
import cn.edu.nju.entity.HotelEntity;
import cn.edu.nju.entity.LiveMesEntity;
import cn.edu.nju.entity.RoomsEntity;
import cn.edu.nju.service.FileService;
import cn.edu.nju.service.HotelService;
import cn.edu.nju.service.StorageService;
import cn.edu.nju.util.SystemDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Qiang
 * @since 27/02/2017
 */
@Controller
@RequestMapping("/hotel")
public class HotelController {



    private final HotelRepository hotelRepository;
    private final HotelService hotelService;
    private final
    StorageService fileService;

    @Autowired
    public HotelController(StorageService storageService, HotelRepository hotelRepository, HotelService hotelService) {
        this.fileService = storageService;
        this.hotelRepository = hotelRepository;
        this.hotelService = hotelService;
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Map<String, Object> uploadHandler(@RequestParam("file[]")MultipartFile file, HttpServletRequest request){
        ;
        System.out.println(file.getSize());;
        fileService.store(file);
        return null;
    }
    @RequestMapping(value = {"/", "/index"})
    public String index(@SessionAttribute(SystemDefault.USER_ID) int id, Model model) {


        HotelEntity hotel = hotelRepository.findOne(id);

        if (hotel == null) {
            System.err.println(id);
        }

        if (hotel.getStatus() == 0) {
            model.addAttribute("status", 0);

        } else {
            model.addAttribute("status", 1);
            List<RoomsEntity> roomsEntities = hotelService.findRoomsByHotelId(id, false, -1 );
            model.addAttribute("rooms", roomsEntities);
        }


        return "hotel/index";
    }

    @RequestMapping("/record")
    public String record(Model model, @SessionAttribute(SystemDefault.USER_ID) int id) {

        HotelEntity hotel = hotelRepository.findOne(id);

        if (hotel.getStatus() == 0) {
            model.addAttribute("status", 0);

        } else {
            model.addAttribute("status", 1);
            //TODO
            List<LiveMesEntity> liveMesEntities = hotelService.findLiveMesByHotelId(id, -1);

            model.addAttribute("records", liveMesEntities);
        }

        return "/hotel/record";
    }




    @RequestMapping(value = "/addInRecords", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addInRecords(int personNum, String personMes, int isMember, int payMethod, int memberId, int roomId, @SessionAttribute(SystemDefault.USER_ID) int hotelId) {


        return hotelService.addInRecords(personNum, personMes, isMember, payMethod, memberId, roomId, hotelId);
    }

    @RequestMapping(value = "/addOutRecords", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addOutRecords(int recordId) {

        // TODO REMOVE futile return
        return hotelService.addOutRecords(recordId);
    }

    @RequestMapping("/info")
    public String info(Model model, @SessionAttribute(SystemDefault.USER_ID) int id) {

        HotelEntity hotel = hotelRepository.findOne(id);

        model.addAttribute("hotel", hotel);

        if (hotelService.isApplyingForEditing(id)) {
            model.addAttribute("edit", true);
        }
        if (hotelService.isApplyingForOpen(id)) {
            model.addAttribute("open", true);
        }

        return "/hotel/info";
    }


    @RequestMapping("/statistics")
    public String statistics() {
        return "/hotel/statistics";
    }


    @RequestMapping(value = "/open", method = RequestMethod.POST)
    public String openApplication(Model model, String reason, @SessionAttribute(SystemDefault.USER_ID) int id) {
        hotelService.saveOpenApplication(reason, id);
        return info(model, id);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editApplication(Model model, String name, String address, String description, @SessionAttribute(SystemDefault.USER_ID) int id) {
        //TODO upload image
        hotelService.saveModifyApplication(name, address, description, id, "");

        return info(model, id);
    }

    @RequestMapping(value = "/addRooms", method = RequestMethod.POST)
    @ResponseBody
    public List<RoomsEntity> addNewRooms(String time, boolean wifi, String picUrl, int area, int type, int price, int number, @SessionAttribute(SystemDefault.USER_ID) int id) {

        // TODO REMOVE futile return
        return hotelService.addRooms(time, wifi, picUrl, area, type, price, number, id);
    }

    @RequestMapping("/register")
    public String register() {
        return "redirect:/register";
    }

    @RequestMapping("/logout")
    public String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:/index";
    }


}
