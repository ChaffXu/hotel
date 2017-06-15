package cn.edu.nju.dao;

import cn.edu.nju.entity.LiveMesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Qiang
 * @since 27/02/2017
 */
public interface LiveMesRepository extends PagingAndSortingRepository<LiveMesEntity, Integer> {
    List<LiveMesEntity> findByHotelId(int hotelId);
    Page<LiveMesEntity> findByHotelId(int hotelId, Pageable pageable);
    List<LiveMesEntity> findByMemberId(int id);
    List<LiveMesEntity> findByRoomId(int roomId);

//    @Query(value = "select * from live_mes where room_id=?1 and TIMESTAMPDIFF(DATE,?2,in_time)=0", nativeQuery = true)
//    List<LiveMesEntity> findByRoomIdAndAndInTime(int roomId, Timestamp t);

}
