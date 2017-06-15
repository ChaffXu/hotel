package cn.edu.nju.service;

import cn.edu.nju.dao.*;
import cn.edu.nju.entity.HotelEntity;
import cn.edu.nju.entity.MemberEntity;
import cn.edu.nju.entity.ReservedEntity;
import cn.edu.nju.entity.RoomsEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Calendar;
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
    MemberService memberService;
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

    @Test
    public void addByReserved() throws Exception {
        List<ReservedEntity> reservedEntities = reservedRepository.findByStatus(0);
        for (int i = 0; i < reservedEntities.size(); i++) {
            Random random = new Random();
            int result = random.nextInt(10);
            if (result < 7) {
                //live
                  reservedEntities.get(i).setStatus(1);
                  reservedRepository.save(reservedEntities.get(i));
                Random random2 = new Random();
                int personNum = random2.nextInt(2) + 1;
                String personMes = "Not any description yet.";
                int payMethod = random.nextInt(3);
                int memberId=reservedEntities.get(i).getMemberId();
                int roomId=reservedEntities.get(i).getRoomId();
                int hotelId=roomsRepository.findOne(roomId).getHotelId();
                Timestamp t=reservedEntities.get(i).getTime();
//                System.out.println("********************");
//                System.out.println(roomId);
//                System.out.println(hotelId);
//                System.out.println(memberId);
//                System.out.println("********************");

                hotelService.addByReserved(personNum, personMes, payMethod, memberId, roomId, hotelId, t);
            } else {
                //cancel
                reservedEntities.get(i).setStatus(2);
                reservedRepository.save(reservedEntities.get(i));

            }
        }
    }

    @Test
    public void addInHotCities() throws Exception{
        List<MemberEntity> memberEntities = memberRepository.findByStatus(1);
        int[]cities={20,126,130,131,153,154,156,157,179,180,186,187,195,196};
        int[]luxuries={127,132,155,158,181,188,197};
//        Timestamp t1=new Timestamp(2013,1,31,12,0,0,0);
//        Timestamp t2=new Timestamp(2013,12,31,12,0,0,0);
        Calendar c1 = Calendar.getInstance();
        c1.set(2016,5,5);
        Calendar c2 = Calendar.getInstance();
        c2.set(2017,4,31);

        while(c1.compareTo(c2)!=0){

            for(int i=0;i<cities.length;i++){
                List<RoomsEntity> roomsEntities0 = roomsRepository.findByHotelIdAndLevel(cities[i],0);
                List<RoomsEntity> roomsEntities1 = roomsRepository.findByHotelIdAndLevel(cities[i],1);
                List<RoomsEntity> roomsEntities2 = roomsRepository.findByHotelIdAndLevel(cities[i],2);

                Random random1 = new Random();
                Random random2= new Random();
                Random random3=new Random();

                int index0=0;
                int index1=0;
                int index2=0;

                for(int j=0;j<40;j++){
                    int personNum = random2.nextInt(2) + 1;
                    String personMes = "Not any description yet.";
                    int isMember = random3.nextInt(2) ;
                    int payMethod;
                    int memberId = 0;
                    if (isMember == 1) {
                        memberId = memberEntities.get(random2.nextInt(memberEntities.size())).getId();
                        payMethod = random2.nextInt(3);

                    } else {
                        payMethod = random2.nextInt(2) + 1;
                    }

                    int result = random1.nextInt(10);
                    if (result < 5 && index0< roomsEntities0.size()) {
                        hotelService.addNoReserved(personNum,personMes,payMethod,memberId,roomsEntities0.get(index0).getId(),cities[i],new Timestamp(c1.getTimeInMillis()));
                        index0+=1;
                    }
                    else if (result < 7 && index1< roomsEntities1.size()) {
                        hotelService.addNoReserved(personNum,personMes,payMethod,memberId,roomsEntities1.get(index1).getId(),cities[i],new Timestamp(c1.getTimeInMillis()));
                        index1+=1;
                    }
                    else if(index2< roomsEntities2.size()){
                        hotelService.addNoReserved(personNum,personMes,payMethod,memberId,roomsEntities2.get(index2).getId(),cities[i],new Timestamp(c1.getTimeInMillis()));
                        index2+=1;
                    }
                }
            }

            for(int i=0;i<luxuries.length;i++){
                List<RoomsEntity> roomsEntities0 = roomsRepository.findByHotelIdAndLevel(cities[i],0);
                List<RoomsEntity> roomsEntities1 = roomsRepository.findByHotelIdAndLevel(cities[i],1);
                List<RoomsEntity> roomsEntities2 = roomsRepository.findByHotelIdAndLevel(cities[i],2);

                Random random1 = new Random();
                Random random2=new Random();
                Random random3=new Random();

                int index0=0;
                int index1=0;
                int index2=0;

                for(int j=0;j<40;j++){
                    int personNum = random2.nextInt(2) + 1;
                    String personMes = "Not any description yet.";
                    int isMember = random3.nextInt(2) ;
                    int payMethod;
                    int memberId = 0;
                    if (isMember == 1) {
                        memberId = memberEntities.get(random2.nextInt(memberEntities.size())).getId();
                        payMethod = random2.nextInt(3);

                    } else {
                        payMethod = random2.nextInt(2) + 1;
                    }

                    int result = random1.nextInt(10);
                    if (result < 5 && index0< roomsEntities0.size()) {
                        hotelService.addNoReserved(personNum,personMes,payMethod,memberId,roomsEntities0.get(index0).getId(),luxuries[i],new Timestamp(c1.getTimeInMillis()));
                        index0+=2;
                    }
                    else if (result < 7 && index1< roomsEntities1.size()) {
                        hotelService.addNoReserved(personNum,personMes,payMethod,memberId,roomsEntities1.get(index1).getId(),luxuries[i],new Timestamp(c1.getTimeInMillis()));
                        index1+=2;
                    }
                    else if(index2< roomsEntities2.size()){
                        hotelService.addNoReserved(personNum,personMes,payMethod,memberId,roomsEntities2.get(index2).getId(),luxuries[i],new Timestamp(c1.getTimeInMillis()));
                        index2+=2;
                    }
                }
            }
            c1.add(Calendar.DATE, 1);
        }
    }

    @Test
    public void addInRegularCities() throws Exception{
        List<MemberEntity> memberEntities = memberRepository.findByStatus(1);
        int[]cities={135,136,137,138,141,142,143,144,149,150,171,172,175,176};
//        Timestamp t1=new Timestamp(2013,1,31,12,0,0,0);
//        Timestamp t2=new Timestamp(2013,12,31,12,0,0,0);
        Calendar c1 = Calendar.getInstance();
        c1.set(2016,5,1);
        Calendar c2 = Calendar.getInstance();
        c2.set(2017,4,31);

        while(c1.compareTo(c2)!=0){

            for(int i=0;i<cities.length;i++){
                List<RoomsEntity> roomsEntities0 = roomsRepository.findByHotelIdAndLevel(cities[i],0);
                List<RoomsEntity> roomsEntities1 = roomsRepository.findByHotelIdAndLevel(cities[i],1);
                List<RoomsEntity> roomsEntities2 = roomsRepository.findByHotelIdAndLevel(cities[i],2);

                Random random1 = new Random();
                Random random2=new Random();
                Random random3=new Random();

                int index0=0;
                int index1=0;
                int index2=0;

                for(int j=0;j<40;j++){
                    int personNum = random2.nextInt(2) + 1;
                    String personMes = "Not any description yet.";
                    int isMember = random3.nextInt(2) ;
                    int payMethod;
                    int memberId = 0;
                    if (isMember == 1) {
                        memberId = memberEntities.get(random2.nextInt(memberEntities.size())).getId();
                        payMethod = random2.nextInt(3);

                    } else {
                        payMethod = random2.nextInt(2) + 1;
                    }

                    int result = random1.nextInt(10);
                    if (result < 5 && index0< roomsEntities0.size()) {
                        hotelService.addNoReserved(personNum,personMes,payMethod,memberId,roomsEntities0.get(index0).getId(),cities[i],new Timestamp(c1.getTimeInMillis()));
                        index0+=2;
                    }
                    else if (result < 7 && index1< roomsEntities1.size()) {
                        hotelService.addNoReserved(personNum,personMes,payMethod,memberId,roomsEntities1.get(index1).getId(),cities[i],new Timestamp(c1.getTimeInMillis()));
                        index1+=2;
                    }
                    else if(index2< roomsEntities2.size()){
                        hotelService.addNoReserved(personNum,personMes,payMethod,memberId,roomsEntities2.get(index2).getId(),cities[i],new Timestamp(c1.getTimeInMillis()));
                        index2+=2;
                    }
                }
            }

            c1.add(Calendar.DATE, 1);
        }
    }

    @Test
    public void addInColdCities() throws Exception{
        List<MemberEntity> memberEntities = memberRepository.findByStatus(1);
        int[]cities={151,152,165,166,167,168,177,178,189,190};
//        Timestamp t1=new Timestamp(2013,1,31,12,0,0,0);
//        Timestamp t2=new Timestamp(2013,12,31,12,0,0,0);
        Calendar c1 = Calendar.getInstance();
        c1.set(2016,5,1);
        Calendar c2 = Calendar.getInstance();
        c2.set(2017,4,31);

        while(c1.compareTo(c2)!=0){

            for(int i=0;i<cities.length;i++){
                List<RoomsEntity> roomsEntities0 = roomsRepository.findByHotelIdAndLevel(cities[i],0);
                List<RoomsEntity> roomsEntities1 = roomsRepository.findByHotelIdAndLevel(cities[i],1);
                List<RoomsEntity> roomsEntities2 = roomsRepository.findByHotelIdAndLevel(cities[i],2);

                Random random1 = new Random();
                Random random2=new Random();
                Random random3=new Random();

                int index0=0;
                int index1=0;
                int index2=0;

                for(int j=0;j<40;j++){
                    int personNum = random2.nextInt(2) + 1;
                    String personMes = "Not any description yet.";
                    int isMember = random3.nextInt(2) ;
                    int payMethod;
                    int memberId = 0;
                    if (isMember == 1) {
                        memberId = memberEntities.get(random2.nextInt(memberEntities.size())).getId();
                        payMethod = random2.nextInt(3);

                    } else {
                        payMethod = random2.nextInt(2) + 1;
                    }

                    int result = random1.nextInt(10);
                    if (result < 5 && index0< roomsEntities0.size()) {
                        hotelService.addNoReserved(personNum,personMes,payMethod,memberId,roomsEntities0.get(index0).getId(),cities[i],new Timestamp(c1.getTimeInMillis()));
                        index0+=4;
                    }
                    else if (result < 7 && index1< roomsEntities1.size()) {
                        hotelService.addNoReserved(personNum,personMes,payMethod,memberId,roomsEntities1.get(index1).getId(),cities[i],new Timestamp(c1.getTimeInMillis()));
                        index1+=4;
                    }
                    else if(index2< roomsEntities2.size()){
                        hotelService.addNoReserved(personNum,personMes,payMethod,memberId,roomsEntities2.get(index2).getId(),cities[i],new Timestamp(c1.getTimeInMillis()));
                        index2+=4;
                    }
                }
            }

            c1.add(Calendar.DATE, 1);
        }
    }

    @Test
    public void addInTravelCities() throws Exception{
        List<MemberEntity> memberEntities = memberRepository.findByStatus(1);
        int[]cities={128,129,133,134,139,140,145,146,147,148,159,160,161,162,163,164,169,170,173,174,182,183,184,185,191,192,193,194,198,199};
//        Timestamp t1=new Timestamp(2013,1,31,12,0,0,0);
//        Timestamp t2=new Timestamp(2013,12,31,12,0,0,0);
        Calendar c1 = Calendar.getInstance();
        c1.set(2016,5,1);
        Calendar c2 = Calendar.getInstance();
        c2.set(2017,4,31);

        while(c1.compareTo(c2)!=0){
            int month=c1.getTime().getMonth();

            for(int i=0;i<cities.length;i++){
                List<RoomsEntity> roomsEntities0 = roomsRepository.findByHotelIdAndLevel(cities[i],0);
                List<RoomsEntity> roomsEntities1 = roomsRepository.findByHotelIdAndLevel(cities[i],1);
                List<RoomsEntity> roomsEntities2 = roomsRepository.findByHotelIdAndLevel(cities[i],2);

                Random random1 = new Random();
                Random random2=new Random();
                Random random3=new Random();

                int index0=0;
                int index1=0;
                int index2=0;

                for(int j=0;j<40;j++){
                    int personNum = random2.nextInt(2) + 1;
                    String personMes = "Not any description yet.";
                    int isMember = random3.nextInt(2) ;
                    int payMethod;
                    int memberId = 0;
                    if (isMember == 1) {
                        memberId = memberEntities.get(random2.nextInt(memberEntities.size())).getId();
                        payMethod = random2.nextInt(3);

                    } else {
                        payMethod = random2.nextInt(2) + 1;
                    }

                    int result = random1.nextInt(10);
                    if (result < 5 && index0< roomsEntities0.size()) {
                        hotelService.addNoReserved(personNum,personMes,payMethod,memberId,roomsEntities0.get(index0).getId(),cities[i],new Timestamp(c1.getTimeInMillis()));
                        if(month==0||month==1||month==10||month==11){
                            index0 += 4;
                        }else if(month==2||month==3||month==8){
                            index0+=2;
                        }else{
                            index0+=1;
                        }
                    }
                    else if (result < 7 && index1< roomsEntities1.size()) {
                        hotelService.addNoReserved(personNum,personMes,payMethod,memberId,roomsEntities1.get(index1).getId(),cities[i],new Timestamp(c1.getTimeInMillis()));
                        if(month==0||month==1||month==10||month==11){
                            index1 += 4;
                        }else if(month==2||month==3||month==8){
                            index1+=2;
                        }else{
                            index1+=1;
                        }
                    }
                    else if(index2< roomsEntities2.size()){
                        hotelService.addNoReserved(personNum,personMes,payMethod,memberId,roomsEntities2.get(index2).getId(),cities[i],new Timestamp(c1.getTimeInMillis()));
                        if(month==0||month==1||month==10||month==11){
                            index1 += 4;
                        }else if(month==2||month==3||month==8){
                            index1+=2;
                        }else{
                            index1+=1;
                        }
                    }
                }
            }

            c1.add(Calendar.DATE, 1);
        }
    }

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