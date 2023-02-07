package com.tomshidi.mongodb.repository;

import com.tomshidi.mongodb.entity.MeetingInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tomshidi
 * @date 2023/2/6 17:30
 */
@Repository
public interface MeetingInfoRepository extends MongoRepository<MeetingInfo, String> {
    MeetingInfo queryMeetingInfoByMeetingId(String meetingId);

    List<MeetingInfo> queryMeetingInfosByMeetingStatus(int meetingStatus);
}
