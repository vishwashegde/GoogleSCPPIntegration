package org.example;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.Unit;
import org.apache.commons.collections4.CollectionUtils;


import java.time.Instant;
import java.util.List;
import java.util.Arrays;

public class DistanceMatrixTest {
    private static final String GOOGLE_API_KEY = "AIzaSyCsnDRoKLIGufCrppywbW94pVpB3YqMLbw";

    public static void main(String[] args) {
        GeoApiContext geoApiContext = new GeoApiContext.Builder().apiKey(GOOGLE_API_KEY).build();

        List<DistanceMatrixRow> results = Arrays.asList(DistanceMatrixApi.newRequest(geoApiContext)
                .origins("3035 McMurtry St, Cumming, GA 30188, USA")
                .destinations("2300 Windy Ridge Pkwy SE, Atlanta, GA 30339, USA")
                .units(Unit.IMPERIAL)
                .departureTime(Instant.now())
                .awaitIgnoreError().rows);

        if(CollectionUtils.isNotEmpty(results)){
            results.forEach(distanceMatrixRow -> {
                List<DistanceMatrixElement> elements = Arrays.asList(distanceMatrixRow.elements);
                if(CollectionUtils.isNotEmpty(elements)){
                    elements.forEach(distanceMatrixElement -> {
                        System.out.println("Distance is " + distanceMatrixElement.distance);
                        System.out.println("Duration in minutes " + distanceMatrixElement.duration.inSeconds / 60);
                        System.out.println("Duration in traffic " + distanceMatrixElement.durationInTraffic);
                    });
                }
            });
        }
    }
}
