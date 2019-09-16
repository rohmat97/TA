package com.example.innstant.data.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class _id {
    @SerializedName("timestamp")
      Long timestamp ;
    @SerializedName("machineIdentifier")
      Long machineIdentifier;
    @SerializedName("processIdentifier")
      Long processIdentifier;
    @SerializedName("counter")
      Long counter;
    @SerializedName("timeSecond")
      Long timeSecond;
    @SerializedName("time")
      Long    time;
    @SerializedName("date")
      String date;
}
