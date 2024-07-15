package com.sandbox.PhotoAppApiUsers.users.shared;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()){
            case 400:
                //uradi ovo
                break;
            case 404:

                if (methodKey.contains("getAlbums")) {
                    return new ResponseStatusException(HttpStatusCode.valueOf(404), "User's albums are not found");
                }
                return new ResponseStatusException(HttpStatusCode.valueOf(404), response.reason());

            default:
                return new Exception(response.reason());
        }
        return null;
    }
}
