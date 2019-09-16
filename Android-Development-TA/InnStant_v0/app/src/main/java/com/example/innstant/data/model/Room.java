package com.example.innstant.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room  {
    @SerializedName("_id")
     private _id _id;
    @SerializedName("roomId")
    private String roomId;
    @SerializedName("name")
     private String name;
    @SerializedName("ownerId")
     private String ownerId;
    @SerializedName("type")
     private String type;
    @SerializedName("location")
     private String location;
    @SerializedName("latitude")
     private Double latitude;
    @SerializedName("longitude")
     private Double longitude;
    @SerializedName("amenities")
     private List<String> amenities;
    @SerializedName("description")
     private String description;
    @SerializedName("price")
     private String price;
    @SerializedName("dpPercentage")
     private Integer dpPercentage;
    @SerializedName("rating")
     private  double rating;
    @SerializedName("total_review")
     private  Integer total_review;
    @SerializedName("photo")
     private List<String> photos;

}
