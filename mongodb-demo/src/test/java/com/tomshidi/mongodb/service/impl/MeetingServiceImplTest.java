package com.tomshidi.mongodb.service.impl;

import com.tomshidi.mongodb.entity.MeetingInfo;
import com.tomshidi.mongodb.service.MeetingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author tomshidi
 * @date 2023/2/7 10:27
 */
@SpringBootTest
class MeetingServiceImplTest {

    @Autowired
    private MeetingService meetingService;

    @Test
    void queryMeetingInfoByMeetingId() {
        MeetingInfo meetingInfo = meetingService.queryMeetingInfoByMeetingId("63e1c8f262c07328f67fde2c");
        assertNotNull(meetingInfo);
    }

    @Test
    void queryMeetingInfosByMeetingStatus() {
        List<MeetingInfo> meetingInfoList = meetingService.queryMeetingInfosByMeetingStatus(0);
        assertNotEquals(0, meetingInfoList.size());
    }

    @Test
    void save() {
        MeetingInfo meetingInfo = new MeetingInfo();
        meetingInfo.setMeetingRoomId("R000");
        meetingInfo.setMeetingSubject("会议主题");
        meetingInfo.setCreatorId("tomshidi");
        meetingInfo.setMeetingTypeId("T001");
        meetingInfo.setOnlineMeeting(false);
        meetingInfo.setStartDate(new Date());
        meetingInfo.setMeetingStatus(0);
        MeetingInfo result = meetingService.save(meetingInfo);
        assertNotNull(result);
    }

    @Test
    void update() {
        MeetingInfo meetingInfo = new MeetingInfo();
        meetingInfo.setMeetingId("63e1c8f262c07328f67fde2c");
        meetingInfo.setMeetingRoomId("R000");
        meetingInfo.setMeetingSubject("会议主题2");
        meetingInfo.setCreatorId("tomshidi");
        meetingInfo.setMeetingTypeId("T001");
        meetingInfo.setOnlineMeeting(false);
        meetingInfo.setStartDate(new Date());
        meetingInfo.setMeetingStatus(0);
        MeetingInfo result = meetingService.update(meetingInfo);
        assertNotNull(result);
    }
}