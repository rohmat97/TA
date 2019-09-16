package com.example.innstant.data.model;

import com.google.gson.annotations.SerializedName;

import java.security.Timestamp;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @SerializedName("_id")
    private _id _id;
    @SerializedName("transactionId")
    private String transactionId;
    @SerializedName("hostId")
    private String hostId;
    @SerializedName("hostName")
    private  String hostName;
    @SerializedName("guestId")
    private String guestId;
    @SerializedName("guestName")
    private  String guestName;
    @SerializedName("roomId")
    private String roomId;
    @SerializedName("roomName")
    private  String roomName;
    @SerializedName("transactionTimestamp")
    private Date transactionTimestamp;
    @SerializedName("bookStartDate")
    private String bookStartDate;
    @SerializedName("bookEndDate")
    private String bookEndDate;
    @SerializedName("paymentStatus")
    private String paymentStatus;
    @SerializedName("isBookingCanceled")
    private  Boolean isBookingCanceled;
    @SerializedName("status")
    private Boolean status;
}