package cn.edu.nju.service;

import cn.edu.nju.dao.*;
import cn.edu.nju.entity.HotelEntity;
import cn.edu.nju.entity.MemberEntity;
import cn.edu.nju.entity.ReservedEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @author Qiang
 * @since 07/03/2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Commit
public class HotelServiceTest {


    @Autowired
    HotelService hotelService;
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    RoomsRepository roomsRepository;
    @Autowired
    ReservedRepository reservedRepository;

    @Autowired
    LiveMesRepository liveMesRepository;
    @Autowired
    PayRecordRepository payRecordRepository;
    @Test
    public void name() throws Exception {
        Random random = new Random();
        liveMesRepository.findAll().forEach(liveMesEntity -> {

            liveMesEntity.setInTime(new Timestamp(System.currentTimeMillis() + random.nextInt(7*3600*24*1000)) );
            liveMesRepository.save(liveMesEntity);
        });

        payRecordRepository.findAll().forEach(payRecordEntity -> {
            payRecordEntity.setTime(new Timestamp(System.currentTimeMillis() + random.nextInt(7*3600*24*1000) ));
            payRecordRepository.save(payRecordEntity);
        });

    }

    @Test
    public void addOutRecords() throws Exception {




    }


    @Test
    public void addInRecords() throws Exception {
        List<HotelEntity> availableHotel = hotelRepository.findByStatus(1);
        List<MemberEntity> memberEntities = memberRepository.findByStatus(1);
        Random random = new Random();
        Random random2 = new Random();
        for (int i = 0; i < availableHotel.size(); i++) {
            int hotelId = availableHotel.get(i).getId();
            for (int j = 0; j < 10; j++) {
                int roomId = roomsRepository.findByHotelIdAndStatus(hotelId, 0).get(0).getId();
                int personNum = random.nextInt(2) + 1;
                String personMes = "Not any description yet.";
                int isMember = random2.nextInt(2) ;
                int payMethod;
                int memberId = 0;
                if (isMember == 1) {
                    memberId = memberEntities.get(random.nextInt(memberEntities.size())).getId();
                    payMethod = random.nextInt(3);
                    if (payMethod == 0) {
                        memberEntities.get(random.nextInt(memberEntities.size()));
                    }


                } else {
                    payMethod = random.nextInt(2) + 1;
                }

                hotelService.addInRecords(personNum, personMes, isMember, payMethod, memberId, roomId, hotelId, false);
            }

        }

    }

//    @Test
//    public void addByReserved() throws Exception {
//        List<HotelEntity> availableHotel = hotelRepository.findByStatus(1);
//        List<MemberEntity> memberEntities = memberRepository.findByStatus(1);
//        List<ReservedEntity> reservedEntities = reservedRepository.findByStatus(0);
//        for (int i = 0; i < reservedEntities.size(); i++) {
//            Random random = new Random();
//            String personMes = "Not any description yet.";
//            int isMember=1;
//
//            int result = random.nextInt(10);
//            if (result < 7) {
//                //live
//                Random random2 = new Random();
//                int personNum = random2.nextInt(2) + 1;
//                hotelService.addInRecords(personNum, personMes, isMember, payMethod, memberId, roomId, hotelId, false);
//            } else {
//                //cancel
//                reservedEntities.get(i).setStatus(2);
//            }
//        }
//    }
//
//
//
//        for (int i = 0; i < availableHotel.size(); i++) {
//            int hotelId = availableHotel.get(i).getId();
//            for (int j = 0; j < 10; j++) {
//                int roomId = roomsRepository.findByHotelIdAndStatus(hotelId, 0).get(0).getId();
//                int personNum = random.nextInt(2) + 1;
//                String personMes = "Not any description yet.";
//                int isMember = random2.nextInt(2) ;
//                int payMethod;
//                int memberId = 0;
//                if (isMember == 1) {
//                    memberId = memberEntities.get(random.nextInt(memberEntities.size())).getId();
//                    payMethod = random.nextInt(3);
//                    if (payMethod == 0) {
//                        memberEntities.get(random.nextInt(memberEntities.size()));
//                    }
//
//
//                } else {
//                    payMethod = random.nextInt(2) + 1;
//                }
//
//                hotelService.addInRecords(personNum, personMes, isMember, payMethod, memberId, roomId, hotelId, false);
//            }
//
//        }
//
//    }



    @Test
    public void re() throws Exception {
        liveMesRepository.findAll().forEach( liveMesEntity -> {
            if (liveMesEntity.getPersonNum() == 0) {
                liveMesEntity.setPersonNum(2);
                liveMesRepository.save(liveMesEntity);
            }


        });
    }

    @Test
    public void testEdit() throws Exception {


        List<HotelEntity> hotelEntities = hotelRepository.findByStatus(1);
        Random random = new Random();
        for (int i = 0; i < 50; i++) {

            if (random.nextInt(3) != 1) {
                hotelService.saveModifyApplication("I want to change to a new name", "new address", "new description",hotelEntities.get(i).getId(), "/img/hotel/hotel2.jpeg" );

//                hotelRepository.save()
            }


        }
    }


    @Test
    public void addPlaces() throws Exception {

        String[] cities = {"北京","天津","上海","重庆", "河北", "河南", "云南", "辽宁","黑龙江","湖南","安徽","山东","新疆","江苏","浙江","江西","湖北","广西","甘肃","山西","内蒙古","陕西","吉林","福建","贵州","广东","青海","西藏","四川","宁夏","海南","台湾","香港","澳门"  };
        int[ ] nums = {3,2,3,2,2,2,2,2,2,2,2,2,2,3,3,2,2,2,2,2,2,2,2,2,2,3,2,2,3,2,2,2,3,2};
        int count=0;

        List<HotelEntity> hotelEntities = hotelRepository.findByStatus(1);

        for (int i = 0; i < cities.length; i++) {

            for (int j = 0; j < nums[i]; j++) {

                hotelEntities.get(count).setCity(cities[i]);
                hotelEntities.get(count).setType(j);
                count++;
            }
        }

        for(int i=count;i<hotelEntities.size();i++){
            hotelEntities.get(i).setCity("其它");
        }


        hotelRepository.save(hotelEntities);

    }
}