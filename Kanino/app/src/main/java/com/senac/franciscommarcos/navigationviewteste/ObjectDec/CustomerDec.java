package com.senac.franciscommarcos.navigationviewteste.ObjectDec;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.senac.franciscommarcos.navigationviewteste.Models.Customer;

import java.lang.reflect.Type;

/**
 * Created by franc on 04/11/2017.
 */

public class CustomerDec implements JsonDeserializer {
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement element = json.getAsJsonObject();
        if(json.getAsJsonObject() != null){
            element = json.getAsJsonObject();
        }
        return (new Gson().fromJson(element, Customer.class));
    }
}
