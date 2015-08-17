package mhewedy.usingspark.service;

import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Objects;

/**
 * Created by mhewedy on 8/17/15.
 */
public abstract class JsonService<T> extends Service{

    @Override
    public String doService(Request request, Response response) {
        response.type("application/json");
        return GSON.toJson(getObject(request, response));
    }

    public abstract T getObject(Request request, Response response);
}
