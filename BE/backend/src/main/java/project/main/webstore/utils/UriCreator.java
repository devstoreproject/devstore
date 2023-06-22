package project.main.webstore.utils;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class UriCreator {
    public static URI createUri(String defaultUrl1,String defaultUrl2, long resourceId1,long reviewId2) {
        return UriComponentsBuilder
                .newInstance()
                .path("api/" + defaultUrl1 + "/{resource-id1}" + defaultUrl2 + "/{resource-id2}")
                .buildAndExpand(resourceId1,reviewId2)
                .toUri();
    }

    public static URI createUri(String defaultUrl) {
        return UriComponentsBuilder
                .newInstance()
                .path("api/" + defaultUrl)
                .build()
                .toUri();
    }

    public static URI createUri(String defaultUrl, long resourceId) {
        return UriComponentsBuilder
                .newInstance()
                .path("api/" + defaultUrl + "/resource-id")
                .buildAndExpand(resourceId)
                .toUri();
    }
}