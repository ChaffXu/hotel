package cn.edu.nju.service;

import cn.edu.nju.dao.HotelRepository;
import cn.edu.nju.dao.MemberRepository;
import cn.edu.nju.dao.RoomsRepository;
import cn.edu.nju.entity.MemberEntity;
import cn.edu.nju.entity.RoomsEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author Qiang
 * @since 07/03/2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Commit
public class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    RoomsRepository roomsRepository;

    @Test
    public void recharge() throws Exception {

        Iterable<MemberEntity> memberEntities = memberRepository.findAll();
        Random random = new Random();

        for (MemberEntity memberEntity : memberEntities) {

            int result = random.nextInt(10);
            if (result < 3) {
                memberService.recharge(memberEntity.getId() , random.nextInt(300 + 1));
            }




        }
    }



    @Test
    public void convertPoints() throws Exception {
         // TODO wait until user has points
    }



    @Test
    public void reserve() throws Exception {
        Iterable<MemberEntity> memberEntities = memberRepository.findAll();
        Random random = new Random();
        List<RoomsEntity> roomsEntities = roomsRepository.findByStatus(0);
        int index = 0;
        for (MemberEntity memberEntity : memberEntities) {

            int result = random.nextInt(10);
            if (result < 5) {
                memberService.reserve(memberEntity.getId(), roomsEntities.get(index).getId());
            }
            index += 20;



        }
    }

    @Test
    public void addHotCities() throws Exception {
        Iterable<MemberEntity> memberEntities = memberRepository.findAll();
        int[]cities={20,126,130,131,153,154,156,157,179,180,186,187,195,196};
        int[]luxuries={127,132,155,158,181,188,197};
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
                int index0=0;
                int index1=0;
                int index2=0;

                for (MemberEntity memberEntity : memberEntities) {
                    Random random = new Random();
                    int result = random.nextInt(10);
                    if (result < 5 && index0<roomsEntities0.size()) {
                        memberService.add(memberEntity.getId(), roomsEntities0.get(index0).getId(), new Timestamp(c1.getTimeInMillis()));
                        index0+=6;
                    }
                    else if (result < 7 && index1<roomsEntities1.size()) {
                        memberService.add(memberEntity.getId(), roomsEntities1.get(index1).getId(), new Timestamp(c1.getTimeInMillis()));
                        index1+=4;
                    }
                    else if(index2<roomsEntities2.size()){
                        memberService.add(memberEntity.getId(), roomsEntities2.get(index2).getId(), new Timestamp(c1.getTimeInMillis()));
                        index2+=3;
                    }

                }
            }

            for(int i=0;i<luxuries.length;i++){
                List<RoomsEntity> roomsEntities0 = roomsRepository.findByHotelIdAndLevel(luxuries[i],0);
                List<RoomsEntity> roomsEntities1 = roomsRepository.findByHotelIdAndLevel(luxuries[i],1);
                List<RoomsEntity> roomsEntities2 = roomsRepository.findByHotelIdAndLevel(luxuries[i],2);
                int index0=0;
                int index1=0;
                int index2=0;

                for (MemberEntity memberEntity : memberEntities) {
                    Random random = new Random();
                    int result = random.nextInt(10);
                    if (result < 5 && index0<roomsEntities0.size()) {
                        memberService.add(memberEntity.getId(), roomsEntities0.get(index0).getId(), new Timestamp(c1.getTimeInMillis()));
                        index0+=7;
                    }
                    else if (result < 7 && index1<roomsEntities1.size()) {
                        memberService.add(memberEntity.getId(), roomsEntities1.get(index1).getId(), new Timestamp(c1.getTimeInMillis()));
                        index1+=4;
                    }
                    else if(index2<roomsEntities2.size()){
                        memberService.add(memberEntity.getId(), roomsEntities2.get(index2).getId(), new Timestamp(c1.getTimeInMillis()));
                        index2+=4;
                    }

                }
            }
            c1.add(Calendar.DATE, 1);
        }

    }

    @Test
    public void addRegularCities() throws Exception {
        Iterable<MemberEntity> memberEntities = memberRepository.findAll();
        int[]cities={135,136,137,138,141,142,143,144,149,150,171,172,175,176};
//        Timestamp t1=new Timestamp(2013,1,31,12,0,0,0);
//        Timestamp t2=new Timestamp(2013,12,31,12,0,0,0);
        Calendar c1 = Calendar.getInstance();
        c1.set(2016,5,1);
        Calendar c2 = Calendar.getInstance();
        c2.set(2017,4,31);

        while(c1.compareTo(c2)!=0){

            for(int i=0;i<cities.length;i++) {
                List<RoomsEntity> roomsEntities0 = roomsRepository.findByHotelIdAndLevel(cities[i], 0);
                List<RoomsEntity> roomsEntities1 = roomsRepository.findByHotelIdAndLevel(cities[i], 1);
                List<RoomsEntity> roomsEntities2 = roomsRepository.findByHotelIdAndLevel(cities[i], 2);
                int index0 = 0;
                int index1 = 0;
                int index2 = 0;

                for (MemberEntity memberEntity : memberEntities) {
                    Random random = new Random();
                    int result = random.nextInt(10);
                    if (result < 5 && index0 < roomsEntities0.size()) {
                        memberService.add(memberEntity.getId(), roomsEntities0.get(index0).getId(), new Timestamp(c1.getTimeInMillis()));
                        index0 += 10;
                    } else if (result < 7 && index1 < roomsEntities1.size()) {
                        memberService.add(memberEntity.getId(), roomsEntities1.get(index1).getId(), new Timestamp(c1.getTimeInMillis()));
                        index1 += 5;
                    } else if (index2 < roomsEntities2.size()) {
                        memberService.add(memberEntity.getId(), roomsEntities2.get(index2).getId(), new Timestamp(c1.getTimeInMillis()));
                        index2 += 4;
                    }

                }
            }
            c1.add(Calendar.DATE, 1);
        }

    }

    @Test
    public void addColdCities() throws Exception {
        Iterable<MemberEntity> memberEntities = memberRepository.findAll();
        int[]cities={151,152,165,166,167,168,177,178,189,190};
//        Timestamp t1=new Timestamp(2013,1,31,12,0,0,0);
//        Timestamp t2=new Timestamp(2013,12,31,12,0,0,0);
        Calendar c1 = Calendar.getInstance();
        c1.set(2016,5,1);
        Calendar c2 = Calendar.getInstance();
        c2.set(2017,4,31);

        while(c1.compareTo(c2)!=0){

            for(int i=0;i<cities.length;i++) {
                List<RoomsEntity> roomsEntities0 = roomsRepository.findByHotelIdAndLevel(cities[i], 0);
                List<RoomsEntity> roomsEntities1 = roomsRepository.findByHotelIdAndLevel(cities[i], 1);
                List<RoomsEntity> roomsEntities2 = roomsRepository.findByHotelIdAndLevel(cities[i], 2);
                int index0 = 0;
                int index1 = 0;
                int index2 = 0;

                for (MemberEntity memberEntity : memberEntities) {
                    Random random = new Random();
                    int result = random.nextInt(10);
                    if (result < 5 && index0 < roomsEntities0.size()) {
                        memberService.add(memberEntity.getId(), roomsEntities0.get(index0).getId(), new Timestamp(c1.getTimeInMillis()));
                        index0 += 15;
                    } else if (result < 7 && index1 < roomsEntities1.size()) {
                        memberService.add(memberEntity.getId(), roomsEntities1.get(index1).getId(), new Timestamp(c1.getTimeInMillis()));
                        index1 += 7;
                    } else if (index2 < roomsEntities2.size()) {
                        memberService.add(memberEntity.getId(), roomsEntities2.get(index2).getId(), new Timestamp(c1.getTimeInMillis()));
                        index2 += 5;
                    }

                }
            }
            c1.add(Calendar.DATE, 1);
        }

    }

    @Test
    public void addTravelCities() throws Exception {
        Iterable<MemberEntity> memberEntities = memberRepository.findAll();
        int[]cities={128,129,133,134,139,140,145,146,147,148,159,160,161,162,163,164,169,170,173,174,182,183,184,185,191,192,193,194,198,199};
//        Timestamp t1=new Timestamp(2013,1,31,12,0,0,0);
//        Timestamp t2=new Timestamp(2013,12,31,12,0,0,0);
        Calendar c1 = Calendar.getInstance();
        c1.set(2016,5,1);
        Calendar c2 = Calendar.getInstance();
        c2.set(2017,4,31);

        while(c1.compareTo(c2)!=0){
            int month=c1.getTime().getMonth();

            for(int i=0;i<cities.length;i++) {
                List<RoomsEntity> roomsEntities0 = roomsRepository.findByHotelIdAndLevel(cities[i], 0);
                List<RoomsEntity> roomsEntities1 = roomsRepository.findByHotelIdAndLevel(cities[i], 1);
                List<RoomsEntity> roomsEntities2 = roomsRepository.findByHotelIdAndLevel(cities[i], 2);
                int index0 = 0;
                int index1 = 0;
                int index2 = 0;

                for (MemberEntity memberEntity : memberEntities) {
                    Random random = new Random();
                    int result = random.nextInt(10);
                    if (result < 5 && index0 < roomsEntities0.size()) {
                        memberService.add(memberEntity.getId(), roomsEntities0.get(index0).getId(), new Timestamp(c1.getTimeInMillis()));

                        if(month==0||month==1||month==10||month==11){
                            index0 += 15;
                        }else if(month==2||month==3||month==8){
                            index0+=10;
                        }else{
                            index0+=6;
                        }

                    } else if (result < 7 && index1 < roomsEntities1.size()) {
                        memberService.add(memberEntity.getId(), roomsEntities1.get(index1).getId(), new Timestamp(c1.getTimeInMillis()));
                        if(month==0||month==1||month==10||month==11){
                            index1 += 7;
                        }else if(month==2||month==3||month==8){
                            index1+=5;
                        }else{
                            index1+=4;
                        }
                    } else if (index2 < roomsEntities2.size()) {
                        memberService.add(memberEntity.getId(), roomsEntities2.get(index2).getId(), new Timestamp(c1.getTimeInMillis()));
                        if(month==0||month==1||month==10||month==11){
                            index2 += 5;
                        }else if(month==2||month==3||month==8){
                            index2+=4;
                        }else{
                            index2+=3;
                        }
                    }

                }
            }
            c1.add(Calendar.DATE, 1);
        }

    }
}

