package com.example.innstant.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @SerializedName("_id")
    private _id _id;

    @SerializedName("userId")
    private String userId;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("idCardNumber")
    private String idCardNumber;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("pin")
    private String pin;

    @SerializedName("profilePhoto")
    private String profilePhoto;

    @SerializedName("idCardPhoto")
    private String idCardPhoto;

    @SerializedName("userWithIdCardPhoto")
    private String userWithIdCardPhoto;

    @SerializedName("rooms")
    private List<Room> rooms;
}
