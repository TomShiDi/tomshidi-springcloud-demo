package com.tomshidi.mongodb.service;

import com.tomshidi.mongodb.entity.MeetingInfo;

import java.util.List;

/**
 * @author tomshidi
 * @date 2023/2/7 9:56
 */
public interface MeetingService {

    MeetingInfo queryMeetingInfoByMeetingId(String meetingId);

    List<MeetingInfo> queryMeetingInfosByMeetingStatus(int meetingStatus);

    MeetingInfo save(MeetingInfo meetingInfo);

    MeetingInfo update(MeetingInfo meetingInfo);
}
