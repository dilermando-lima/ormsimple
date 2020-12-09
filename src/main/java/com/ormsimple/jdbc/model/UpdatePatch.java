package com.ormsimple.jdbc.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class UpdatePatch {
    

    private Map<String,Object> mapAlter;

    public UpdatePatch( Map<String,Object> mapAlter ) {
        this.mapAlter = mapAlter;
    }

    public UpdatePatch() {
        this.mapAlter = new LinkedHashMap<String,Object>();
    }

    public UpdatePatch alter(String col, Object value){
        mapAlter.put(col, value);
        return this;
    }

    public UpdatePatch alter(String col, Object value, boolean conditionToAddOnAlter){
        if( conditionToAddOnAlter )  mapAlter.put(col, value);
        return this;
    }


    public Map<String, Object> getMapAlter() {
        return this.mapAlter;
    }

}
