package com.tomshidi.mongodb.service.impl;

import com.tomshidi.base.enums.BaseExceptionEnum;
import com.tomshidi.base.exceptions.BaseException;
import com.tomshidi.mongodb.entity.MeetingInfo;
import com.tomshidi.mongodb.repository.MeetingInfoRepository;
import com.tomshidi.mongodb.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author tomshidi
 * @date 2023/2/7 9:56
 */
@Service
public class MeetingServiceImpl implements MeetingService {

    private MeetingInfoRepository meetingInfoRepository;

    @Autowired
    public void setMeetingInfoRepository(MeetingInfoRepository meetingInfoRepository) {
        this.meetingInfoRepository = meetingInfoRepository;
    }


    @Override
    public MeetingInfo queryMeetingInfoByMeetingId(String meetingId) {
        return meetingInfoRepository.findById(meetingId).orElse(null);
    }

    @Override
    public List<MeetingInfo> queryMeetingInfosByMeetingStatus(int meetingStatus) {
        return meetingInfoRepository.queryMeetingInfosByMeetingStatus(meetingStatus);
    }

    @Override
    public MeetingInfo save(MeetingInfo meetingInfo) {
        return meetingInfoRepository.save(meetingInfo);
    }

    @Override
    public MeetingInfo update(MeetingInfo meetingInfo) {
        if (ObjectUtils.isEmpty(meetingInfo.getMeetingId())) {
            throw new BaseException(BaseExceptionEnum.RECORD_DATA_NOT_FOUND);
        }
        MeetingInfo pre = meetingInfoRepository.findById(meetingInfo.getMeetingId()).orElse(null);
        if (ObjectUtils.isEmpty(pre)) {
            throw new BaseException(BaseExceptionEnum.RECORD_DATA_NOT_FOUND);
        }
        return meetingInfoRepository.save(meetingInfo);
    }
}
