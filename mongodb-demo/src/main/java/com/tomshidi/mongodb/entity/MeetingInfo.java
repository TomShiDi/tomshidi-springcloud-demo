package com.tomshidi.mongodb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tomshidi
 * @date 2023/2/6 17:32
 */
@Document(collection = "meeting_info")
public class MeetingInfo implements Serializable {

    @Id
    private String meetingId;

    @Field(name = "meeting_type_id")
    private String meetingTypeId;

    @Field(name = "meeting_subject")
    private String meetingSubject;

    @Field(name = "creator_id")
    private String creatorId;

    @Field(name = "meeting_room_id")
    private String meetingRoomId;

    @Field(name = "meeting_status")
    private int meetingStatus;

    @Field(name = "meeting_description")
    private String meetingDescription;

    @Field(name = "start_date", targetType = FieldType.DATE_TIME)
    private Date startDate;

    @Field(name = "end_date", targetType = FieldType.DATE_TIME)
    private Date endDate;

    @Field(name = "is_online_meeting", targetType = FieldType.BOOLEAN)
    private boolean isOnlineMeeting;

    @Field(name = "cancel_reason", targetType = FieldType.STRING)
    private String cancelReason;

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getMeetingTypeId() {
        return meetingTypeId;
    }

    public void setMeetingTypeId(String meetingTypeId) {
        this.meetingTypeId = meetingTypeId;
    }

    public String getMeetingSubject() {
        return meetingSubject;
    }

    public void setMeetingSubject(String meetingSubject) {
        this.meetingSubject = meetingSubject;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getMeetingRoomId() {
        return meetingRoomId;
    }

    public void setMeetingRoomId(String meetingRoomId) {
        this.meetingRoomId = meetingRoomId;
    }

    public int getMeetingStatus() {
        return meetingStatus;
    }

    public void setMeetingStatus(int meetingStatus) {
        this.meetingStatus = meetingStatus;
    }

    public String getMeetingDescription() {
        return meetingDescription;
    }

    public void setMeetingDescription(String meetingDescription) {
        this.meetingDescription = meetingDescription;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isOnlineMeeting() {
        return isOnlineMeeting;
    }

    public void setOnlineMeeting(boolean onlineMeeting) {
        isOnlineMeeting = onlineMeeting;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }
}
