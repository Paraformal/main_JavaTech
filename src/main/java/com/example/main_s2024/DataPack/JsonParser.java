package com.example.main_s2024.DataPack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {
    JsonParser(String jsonStr) throws JSONException {
        JSONObject jsonObj;
        String[] strings;
        jsonObj = new JSONObject(jsonStr); //assegnazione della stringa ad un oggetto JSONObject
        JSONArray jsonArray = jsonObj.getJSONArray("batteryInfo");
        strings = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            strings[i] = jsonArray.getString(i);
        }
        DynamicGeneralStats.getInstance().setBattery(strings);

        jsonArray = jsonObj.getJSONArray("cpuInfo");
        strings = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            strings[i] = jsonArray.getString(i);
        }
        StaticGeneralStats.getInstance().setCpuInfo(strings);

        try {
            jsonArray = jsonObj.getJSONArray("numericAvaibleFileSystem");
            Float[] floats = new Float[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                floats[i] = Float.valueOf(jsonArray.getString(i));
            }
            DynamicGeneralStats.getInstance().setAvaibleFileSystem(floats);
        } catch (JSONException e) {
            Float[] floats = new Float[jsonArray.length()];
            floats[0] = 0f;
            DynamicGeneralStats.getInstance().setAvaibleFileSystem(floats);
        }

        strings = jsonObj.getString("disks").split("\n");
        DynamicGeneralStats.getInstance().setDisks(strings);

        strings = jsonObj.getString("computerInfo").split("\n");
        StaticGeneralStats.getInstance().setComputerInfo(strings);

        strings = jsonObj.getString("miscellaneous").split("\n");
        StaticGeneralStats.getInstance().setMiscellaneous(strings);

        String string;
        string = jsonObj.getString("numericCpuLoad");
        DynamicGeneralStats.getInstance().setCpuLoad(Float.parseFloat(string));

        string = jsonObj.getString("numericBatteryPerc");
        if (!string.equals(""))
            DynamicGeneralStats.getInstance().setBatteryPerc(string);

        string = jsonObj.getString("numericPercPerThread");
        String[] tmpStr = string.split("\n");
        Float[] tmpFlo = new Float[tmpStr.length];
        for (int i = 0; i < tmpStr.length; i++) {
            tmpFlo[i] = Float.valueOf(tmpStr[i]);
        }
        DynamicGeneralStats.getInstance().setPercPerThread(tmpFlo);

        StaticGeneralStats.getInstance().notifyMyObservers();
    }
}
