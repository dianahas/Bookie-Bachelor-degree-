package com.ubb.licenta.amadeusintegration.auth;

import com.squareup.okhttp.Credentials;
import com.ubb.licenta.amadeusintegration.api.Pair;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class HttpBasicAuth implements Authentication {
    private String username;
    private String password;

    @Override
    public void applyToParams( List<Pair> queryParams, Map<String, String> headerParams ) {
        if ( username == null && password == null ) {
            return;
        }
        headerParams.put( "Authorization", Credentials.basic(
                username == null ? "" : username,
                password == null ? "" : password ) );
    }
}
