package io.github.raduorleanu.and1.interfaces;

import java.util.ArrayList;

import io.github.raduorleanu.and1.models.Place;

public interface ICallbackResponse {

    void updateSpecificPlace(String placeId, ArrayList<String> userNameList);
}
