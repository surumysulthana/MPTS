package com.example.movement;

import org.json.JSONException;
import org.json.JSONObject;

public interface JsonResponse {
    public void response(JSONObject jo) throws JSONException;
}
