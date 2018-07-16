package com.ubb.licenta.amadeusintegration.api;

import com.google.gson.reflect.TypeToken;
import com.ubb.licenta.amadeusintegration.info.hotel.HotelPropertyResponse;
import com.ubb.licenta.amadeusintegration.info.hotel.HotelSearchResponse;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultApi {
    private ApiClient apiClient;

    public DefaultApi() {
        this( Configuration.getDefaultApiClient() );
    }

    public DefaultApi( ApiClient apiClient ) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient( ApiClient apiClient ) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for hotelGeosearchByCircle
     *
     * @param apikey                  API Key provided for your account, to identify you for API access. Make sure to keep this API key secret. (required)
     * @param latitude                Latitude of the center of the search. (required)
     * @param longitude               Longitude of the center of the search. (required)
     * @param radius                  Radius around the center to look for hotels in kilometers (km). (required)
     * @param checkIn                 Date on which the guest will begin their stay in the hotel. Past availability is not displayed, future availability becomes less useful from about 6 months from the current date. (required)
     * @param checkOut                Date on which the guest will end their stay in the hotel. (required)
     * @param lang                    The preferred language of the content related to each hotel. Content will be returned in this language if available. (optional, default to EN)
     * @param currency                The preferred &lt;a href&#x3D;\&quot;https://en.wikipedia.org/wiki/ISO_4217\&quot;&gt;currency&lt;/a&gt; for the results (optional, default to USD)
     * @param chain                   Narrows the hotel search to a given hotel provider. The hotel chain is indicated by the first two characters of the property code. (optional, default to 6C)
     * @param maxRate                 The maximum amount per night that any hotel in the shopping response should cost. This is calculated by dividing the total price of the stay for the given dates by the number of nights specified falling between the check_in and check_out dates. (optional, default to 500)
     * @param numberOfResults         The maximum number of hotels to return in the results set. Hotels are ordered by total price, so if more than the given maximum number of hotels are available, only the cheapest options are returned. (optional, default to 20)
     * @param allRooms                This option if enabled will return all hotel room rates, not just the lowest room rate. Note: This will have an impact on the response time due to the larger messages returned. (optional, default to false)
     * @param showSoldOut             This option if enabled will return hotel names and addresses even if rooms are sold out (closed properties) (optional, default to false)
     * @param amenity                 Hotel &lt;a href&#x3D;\&quot;hotels-api-supported-amenities-filter\&quot;&gt;amenities filter&lt;/a&gt; to search narrow down hotels with certain amenities. For example&amp;colon; amenity&#x3D;POOL. (Note: multiple amenities can be used in searches: amenity&#x3D;PARKING&amp;amenity&#x3D;RESTAURANT&amp;amenity&#x3D;PETS_ALLOWED).  (optional)
     * @param progressListener        Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call hotelGeosearchByCircleCall( String apikey, BigDecimal latitude, BigDecimal longitude, Integer radius, String checkIn, String checkOut, String lang,
                                                                String currency, String chain, BigDecimal maxRate, Integer numberOfResults, Boolean allRooms, Boolean showSoldOut,
                                                                List<String> amenity, final ProgressResponseBody.ProgressListener progressListener,
                                                                final ProgressRequestBody.ProgressRequestListener progressRequestListener ) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/hotels/search-circle";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
        if ( apikey != null )
            localVarQueryParams.addAll( apiClient.parameterToPair( "apikey", apikey ) );
        if ( latitude != null )
            localVarQueryParams.addAll( apiClient.parameterToPair( "latitude", latitude ) );
        if ( longitude != null )
            localVarQueryParams.addAll( apiClient.parameterToPair( "longitude", longitude ) );
        if ( radius != null )
            localVarQueryParams.addAll( apiClient.parameterToPair( "radius", radius ) );
        if ( checkIn != null )
            localVarQueryParams.addAll( apiClient.parameterToPair( "check_in", checkIn ) );
        if ( checkOut != null )
            localVarQueryParams.addAll( apiClient.parameterToPair( "check_out", checkOut ) );
        if ( lang != null )
            localVarQueryParams.addAll( apiClient.parameterToPair( "lang", lang ) );
        if ( currency != null )
            localVarQueryParams.addAll( apiClient.parameterToPair( "currency", currency ) );
        if ( chain != null )
            localVarQueryParams.addAll( apiClient.parameterToPair( "chain", chain ) );
        if ( maxRate != null )
            localVarQueryParams.addAll( apiClient.parameterToPair( "max_rate", maxRate ) );
        if ( numberOfResults != null )
            localVarQueryParams.addAll( apiClient.parameterToPair( "number_of_results", numberOfResults ) );
        if ( allRooms != null )
            localVarQueryParams.addAll( apiClient.parameterToPair( "all_rooms", allRooms ) );
        if ( showSoldOut != null )
            localVarQueryParams.addAll( apiClient.parameterToPair( "show_sold_out", showSoldOut ) );
        if ( amenity != null )
            localVarCollectionQueryParams.addAll( apiClient.parameterToPairs( "multi", "amenity", amenity ) );

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept( localVarAccepts );
        if ( localVarAccept != null ) localVarHeaderParams.put( "Accept", localVarAccept );

        final String[] localVarContentTypes = {

        };
        final String localVarContentType = apiClient.selectHeaderContentType( localVarContentTypes );
        localVarHeaderParams.put( "Content-Type", localVarContentType );

        if ( progressListener != null ) {
            apiClient.getHttpClient().networkInterceptors().add( chain1 -> {
                com.squareup.okhttp.Response originalResponse = chain1.proceed( chain1.request() );
                return originalResponse.newBuilder()
                        .body( new ProgressResponseBody( originalResponse.body(), progressListener ) )
                        .build();
            } );
        }

        String[] localVarAuthNames = new String[]{};
        return apiClient.buildCall( localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener );
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call hotelGeosearchByCircleValidateBeforeCall( String apikey, BigDecimal latitude, BigDecimal longitude, Integer radius, String checkIn, String checkOut,
                                                                               String lang, String currency, String chain, BigDecimal maxRate, Integer numberOfResults, Boolean allRooms,
                                                                               Boolean showSoldOut, List<String> amenity, final ProgressResponseBody.ProgressListener progressListener,
                                                                               final ProgressRequestBody.ProgressRequestListener progressRequestListener ) throws ApiException {

        // verify the required parameter 'apikey' is set
        if ( apikey == null ) {
            throw new ApiException( "Missing the required parameter 'apikey' when calling hotelGeosearchByCircle(Async)" );
        }

        // verify the required parameter 'latitude' is set
        if ( latitude == null ) {
            throw new ApiException( "Missing the required parameter 'latitude' when calling hotelGeosearchByCircle(Async)" );
        }

        // verify the required parameter 'longitude' is set
        if ( longitude == null ) {
            throw new ApiException( "Missing the required parameter 'longitude' when calling hotelGeosearchByCircle(Async)" );
        }

        // verify the required parameter 'radius' is set
        if ( radius == null ) {
            throw new ApiException( "Missing the required parameter 'radius' when calling hotelGeosearchByCircle(Async)" );
        }

        // verify the required parameter 'checkIn' is set
        if ( checkIn == null ) {
            throw new ApiException( "Missing the required parameter 'checkIn' when calling hotelGeosearchByCircle(Async)" );
        }

        // verify the required parameter 'checkOut' is set
        if ( checkOut == null ) {
            throw new ApiException( "Missing the required parameter 'checkOut' when calling hotelGeosearchByCircle(Async)" );
        }


        return hotelGeosearchByCircleCall( apikey, latitude, longitude, radius, checkIn, checkOut, lang, currency, chain, maxRate,
                numberOfResults, allRooms, showSoldOut, amenity, progressListener, progressRequestListener );

    }

    /**
     * Hotel Geosearch by Circle API - Locate the cheapest available rooms within a given radius of a defined point for a given stay period.
     * &lt;p&gt;A fast Hotel shopping API to see which hotels are available in a given area, on a given day and displays their lowest prices. With this API you can find out the price of the cheapest daily rate for all hotels within a specified radius of a point.&lt;/p&gt;  &lt;p&gt;This API allows you to quickly see the hotel locations in a region, and what prices in that area look like,  as well as the check-in and check-out dates, and get list of up to 140 properties (names, codes, image, amenities) with their locations and rates. Optional parameters such as currency and maximum rates, amenities or hotel chain codes are also available and can be used to narrow down the results. More optional parameters such as show_sold_out &amp; rooms can be used to show sold out rooms and all available rooms. &lt;/p&gt;  &lt;p&gt;The API is based on our high-speed hotel pricing cache, which is also used to power the &lt;a href&#x3D;\&quot;https://hotelsearchengine.amadeus.com/\&quot;&gt;Amadeus Hotel Search Engine&lt;/a&gt; application. Results are returned very quickly, response times are generally under 2s. Our cache has great global coverage and is constantly refreshed with the latest prices.&lt;/p&gt;
     *
     * @param apikey          API Key provided for your account, to identify you for API access. Make sure to keep this API key secret. (required)
     * @param latitude        Latitude of the center of the search. (required)
     * @param longitude       Longitude of the center of the search. (required)
     * @param radius          Radius around the center to look for hotels in kilometers (km). (required)
     * @param checkIn         Date on which the guest will begin their stay in the hotel. Past availability is not displayed, future availability becomes less useful from about 6 months from the current date. (required)
     * @param checkOut        Date on which the guest will end their stay in the hotel. (required)
     * @param lang            The preferred language of the content related to each hotel. Content will be returned in this language if available. (optional, default to EN)
     * @param currency        The preferred &lt;a href&#x3D;\&quot;https://en.wikipedia.org/wiki/ISO_4217\&quot;&gt;currency&lt;/a&gt; for the results (optional, default to USD)
     * @param chain           Narrows the hotel search to a given hotel provider. The hotel chain is indicated by the first two characters of the property code. (optional, default to 6C)
     * @param maxRate         The maximum amount per night that any hotel in the shopping response should cost. This is calculated by dividing the total price of the stay for the given dates by the number of nights specified falling between the check_in and check_out dates. (optional, default to 500)
     * @param numberOfResults The maximum number of hotels to return in the results set. Hotels are ordered by total price, so if more than the given maximum number of hotels are available, only the cheapest options are returned. (optional, default to 20)
     * @param allRooms        This option if enabled will return all hotel room rates, not just the lowest room rate. Note: This will have an impact on the response time due to the larger messages returned. (optional, default to false)
     * @param showSoldOut     This option if enabled will return hotel names and addresses even if rooms are sold out (closed properties) (optional, default to false)
     * @param amenity         Hotel &lt;a href&#x3D;\&quot;hotels-api-supported-amenities-filter\&quot;&gt;amenities filter&lt;/a&gt; to search narrow down hotels with certain amenities. For example&amp;colon; amenity&#x3D;POOL. (Note: multiple amenities can be used in searches: amenity&#x3D;PARKING&amp;amenity&#x3D;RESTAURANT&amp;amenity&#x3D;PETS_ALLOWED).  (optional)
     * @return HotelSearchResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public HotelSearchResponse hotelGeosearchByCircle( String apikey, BigDecimal latitude, BigDecimal longitude, Integer radius, String checkIn, String checkOut, String lang, String currency, String chain, BigDecimal maxRate, Integer numberOfResults, Boolean allRooms, Boolean showSoldOut, List<String> amenity ) throws ApiException {
        ApiResponse<HotelSearchResponse> resp = hotelGeosearchByCircleWithHttpInfo( apikey, latitude, longitude, radius, checkIn, checkOut, lang, currency, chain, maxRate, numberOfResults, allRooms, showSoldOut, amenity );
        return resp.getData();
    }

    /**
     * Hotel Geosearch by Circle API - Locate the cheapest available rooms within a given radius of a defined point for a given stay period.
     * &lt;p&gt;A fast Hotel shopping API to see which hotels are available in a given area, on a given day and displays their lowest prices. With this API you can find out the price of the cheapest daily rate for all hotels within a specified radius of a point.&lt;/p&gt;  &lt;p&gt;This API allows you to quickly see the hotel locations in a region, and what prices in that area look like,  as well as the check-in and check-out dates, and get list of up to 140 properties (names, codes, image, amenities) with their locations and rates. Optional parameters such as currency and maximum rates, amenities or hotel chain codes are also available and can be used to narrow down the results. More optional parameters such as show_sold_out &amp; rooms can be used to show sold out rooms and all available rooms. &lt;/p&gt;  &lt;p&gt;The API is based on our high-speed hotel pricing cache, which is also used to power the &lt;a href&#x3D;\&quot;https://hotelsearchengine.amadeus.com/\&quot;&gt;Amadeus Hotel Search Engine&lt;/a&gt; application. Results are returned very quickly, response times are generally under 2s. Our cache has great global coverage and is constantly refreshed with the latest prices.&lt;/p&gt;
     *
     * @param apikey          API Key provided for your account, to identify you for API access. Make sure to keep this API key secret. (required)
     * @param latitude        Latitude of the center of the search. (required)
     * @param longitude       Longitude of the center of the search. (required)
     * @param radius          Radius around the center to look for hotels in kilometers (km). (required)
     * @param checkIn         Date on which the guest will begin their stay in the hotel. Past availability is not displayed, future availability becomes less useful from about 6 months from the current date. (required)
     * @param checkOut        Date on which the guest will end their stay in the hotel. (required)
     * @param lang            The preferred language of the content related to each hotel. Content will be returned in this language if available. (optional, default to EN)
     * @param currency        The preferred &lt;a href&#x3D;\&quot;https://en.wikipedia.org/wiki/ISO_4217\&quot;&gt;currency&lt;/a&gt; for the results (optional, default to USD)
     * @param chain           Narrows the hotel search to a given hotel provider. The hotel chain is indicated by the first two characters of the property code. (optional, default to 6C)
     * @param maxRate         The maximum amount per night that any hotel in the shopping response should cost. This is calculated by dividing the total price of the stay for the given dates by the number of nights specified falling between the check_in and check_out dates. (optional, default to 500)
     * @param numberOfResults The maximum number of hotels to return in the results set. Hotels are ordered by total price, so if more than the given maximum number of hotels are available, only the cheapest options are returned. (optional, default to 20)
     * @param allRooms        This option if enabled will return all hotel room rates, not just the lowest room rate. Note: This will have an impact on the response time due to the larger messages returned. (optional, default to false)
     * @param showSoldOut     This option if enabled will return hotel names and addresses even if rooms are sold out (closed properties) (optional, default to false)
     * @param amenity         Hotel &lt;a href&#x3D;\&quot;hotels-api-supported-amenities-filter\&quot;&gt;amenities filter&lt;/a&gt; to search narrow down hotels with certain amenities. For example&amp;colon; amenity&#x3D;POOL. (Note: multiple amenities can be used in searches: amenity&#x3D;PARKING&amp;amenity&#x3D;RESTAURANT&amp;amenity&#x3D;PETS_ALLOWED).  (optional)
     * @return ApiResponse&lt;HotelSearchResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<HotelSearchResponse> hotelGeosearchByCircleWithHttpInfo( String apikey, BigDecimal latitude, BigDecimal longitude, Integer radius, String checkIn,
                                                                                String checkOut, String lang, String currency, String chain, BigDecimal maxRate, Integer numberOfResults,
                                                                                Boolean allRooms, Boolean showSoldOut, List<String> amenity ) throws ApiException {
        com.squareup.okhttp.Call call = hotelGeosearchByCircleValidateBeforeCall( apikey, latitude, longitude, radius, checkIn, checkOut, lang,
                currency, chain, maxRate, numberOfResults, allRooms, showSoldOut, amenity, null, null );
        Type localVarReturnType = new TypeToken<HotelSearchResponse>() {
        }.getType();

        return apiClient.execute( call, localVarReturnType );
    }

    /**
     * Hotel Geosearch by Circle API - Locate the cheapest available rooms within a given radius of a defined point for a given stay period. (asynchronously)
     * &lt;p&gt;A fast Hotel shopping API to see which hotels are available in a given area, on a given day and displays their lowest prices. With this API you can find out the price of the cheapest daily rate for all hotels within a specified radius of a point.&lt;/p&gt;  &lt;p&gt;This API allows you to quickly see the hotel locations in a region, and what prices in that area look like,  as well as the check-in and check-out dates, and get list of up to 140 properties (names, codes, image, amenities) with their locations and rates. Optional parameters such as currency and maximum rates, amenities or hotel chain codes are also available and can be used to narrow down the results. More optional parameters such as show_sold_out &amp; rooms can be used to show sold out rooms and all available rooms. &lt;/p&gt;  &lt;p&gt;The API is based on our high-speed hotel pricing cache, which is also used to power the &lt;a href&#x3D;\&quot;https://hotelsearchengine.amadeus.com/\&quot;&gt;Amadeus Hotel Search Engine&lt;/a&gt; application. Results are returned very quickly, response times are generally under 2s. Our cache has great global coverage and is constantly refreshed with the latest prices.&lt;/p&gt;
     *
     * @param apikey          API Key provided for your account, to identify you for API access. Make sure to keep this API key secret. (required)
     * @param latitude        Latitude of the center of the search. (required)
     * @param longitude       Longitude of the center of the search. (required)
     * @param radius          Radius around the center to look for hotels in kilometers (km). (required)
     * @param checkIn         Date on which the guest will begin their stay in the hotel. Past availability is not displayed, future availability becomes less useful from about 6 months from the current date. (required)
     * @param checkOut        Date on which the guest will end their stay in the hotel. (required)
     * @param lang            The preferred language of the content related to each hotel. Content will be returned in this language if available. (optional, default to EN)
     * @param currency        The preferred &lt;a href&#x3D;\&quot;https://en.wikipedia.org/wiki/ISO_4217\&quot;&gt;currency&lt;/a&gt; for the results (optional, default to USD)
     * @param chain           Narrows the hotel search to a given hotel provider. The hotel chain is indicated by the first two characters of the property code. (optional, default to 6C)
     * @param maxRate         The maximum amount per night that any hotel in the shopping response should cost. This is calculated by dividing the total price of the stay for the given dates by the number of nights specified falling between the check_in and check_out dates. (optional, default to 500)
     * @param numberOfResults The maximum number of hotels to return in the results set. Hotels are ordered by total price, so if more than the given maximum number of hotels are available, only the cheapest options are returned. (optional, default to 20)
     * @param allRooms        This option if enabled will return all hotel room rates, not just the lowest room rate. Note: This will have an impact on the response time due to the larger messages returned. (optional, default to false)
     * @param showSoldOut     This option if enabled will return hotel names and addresses even if rooms are sold out (closed properties) (optional, default to false)
     * @param amenity         Hotel &lt;a href&#x3D;\&quot;hotels-api-supported-amenities-filter\&quot;&gt;amenities filter&lt;/a&gt; to search narrow down hotels with certain amenities. For example&amp;colon; amenity&#x3D;POOL. (Note: multiple amenities can be used in searches: amenity&#x3D;PARKING&amp;amenity&#x3D;RESTAURANT&amp;amenity&#x3D;PETS_ALLOWED).  (optional)
     * @param callback        The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call hotelGeosearchByCircleAsync( String apikey, BigDecimal latitude, BigDecimal longitude, Integer radius, String checkIn, String checkOut, String lang, String currency, String chain, BigDecimal maxRate, Integer numberOfResults, Boolean allRooms, Boolean showSoldOut, List<String> amenity, final ApiCallback<HotelSearchResponse> callback ) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if ( callback != null ) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update( long bytesRead, long contentLength, boolean done ) {
                    callback.onDownloadProgress( bytesRead, contentLength, done );
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress( long bytesWritten, long contentLength, boolean done ) {
                    callback.onUploadProgress( bytesWritten, contentLength, done );
                }
            };
        }

        com.squareup.okhttp.Call call = hotelGeosearchByCircleValidateBeforeCall( apikey, latitude, longitude, radius, checkIn, checkOut, lang, currency, chain, maxRate, numberOfResults, allRooms, showSoldOut, amenity, progressListener, progressRequestListener );
        Type localVarReturnType = new TypeToken<HotelSearchResponse>() {
        }.getType();
        apiClient.executeAsync( call, localVarReturnType, callback );
        return call;
    }

    /**
     * Build call for hotelPropertyCodeSearch
     *
     * @param apikey                  API Key provided for your account, to identify you for API access. Make sure to keep this API key secret. (required)
     * @param propertyCode            A Hotel property code based on 2 letter chain code + 3 letter &lt;a href&#x3D;\&quot;https://en.wikipedia.org/wiki/International_Air_Transport_Association_airport_code\&quot;&gt;IATA code&lt;/a&gt; of the city + 3 char property unique id. (required)
     * @param checkIn                 Date on which the guest will begin their stay in the hotel. Past availability is not displayed, future availability becomes less useful from about 6 months from the current date. (required)
     * @param checkOut                Date on which the guest will end their stay in the hotel. (required)
     * @param lang                    The preferred language of the content related to each hotel. Content will be returned in this language if available. (optional, default to EN)
     * @param currency                The preferred &lt;a href&#x3D;\&quot;https://en.wikipedia.org/wiki/ISO_4217\&quot;&gt;currency&lt;/a&gt; for the results (optional, default to USD)
     * @param allRooms                This option if enabled will return all hotel room rates, not just the lowest room rate. Note: This will have an impact on the response time due to the larger messages returned. (optional, default to true)
     * @param showSoldOut             This option if enabled will return hotel names and addresses even if rooms are sold out (closed properties) (optional, default to false)
     * @param progressListener        Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call hotelPropertyCodeSearchCall( String apikey, String propertyCode, String checkIn, String checkOut, String lang, String currency,
                                                                 Boolean allRooms, Boolean showSoldOut, final ProgressResponseBody.ProgressListener progressListener,
                                                                 final ProgressRequestBody.ProgressRequestListener progressRequestListener ) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/hotels/{property_code}"
                .replaceAll( "\\{" + "property_code" + "\\}", apiClient.escapeString( propertyCode ) );

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
        if ( apikey != null )
            localVarQueryParams.addAll( apiClient.parameterToPair( "apikey", apikey ) );
        if ( checkIn != null )
            localVarQueryParams.addAll( apiClient.parameterToPair( "check_in", checkIn ) );
        if ( checkOut != null )
            localVarQueryParams.addAll( apiClient.parameterToPair( "check_out", checkOut ) );
        if ( lang != null )
            localVarQueryParams.addAll( apiClient.parameterToPair( "lang", lang ) );
        if ( currency != null )
            localVarQueryParams.addAll( apiClient.parameterToPair( "currency", currency ) );
        if ( allRooms != null )
            localVarQueryParams.addAll( apiClient.parameterToPair( "all_rooms", allRooms ) );
        if ( showSoldOut != null )
            localVarQueryParams.addAll( apiClient.parameterToPair( "show_sold_out", showSoldOut ) );

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept( localVarAccepts );
        if ( localVarAccept != null ) localVarHeaderParams.put( "Accept", localVarAccept );

        final String[] localVarContentTypes = {

        };
        final String localVarContentType = apiClient.selectHeaderContentType( localVarContentTypes );
        localVarHeaderParams.put( "Content-Type", localVarContentType );

        if ( progressListener != null ) {
            apiClient.getHttpClient().networkInterceptors().add( chain -> {
                com.squareup.okhttp.Response originalResponse = chain.proceed( chain.request() );
                return originalResponse.newBuilder()
                        .body( new ProgressResponseBody( originalResponse.body(), progressListener ) )
                        .build();
            } );
        }

        String[] localVarAuthNames = new String[]{};
        return apiClient.buildCall( localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener );
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call hotelPropertyCodeSearchValidateBeforeCall( String apikey, String propertyCode, String checkIn, String checkOut, String lang, String currency,
                                                                                Boolean allRooms, Boolean showSoldOut, final ProgressResponseBody.ProgressListener progressListener,
                                                                                final ProgressRequestBody.ProgressRequestListener progressRequestListener ) throws ApiException {

        // verify the required parameter 'apikey' is set
        if ( apikey == null ) {
            throw new ApiException( "Missing the required parameter 'apikey' when calling hotelPropertyCodeSearch(Async)" );
        }

        // verify the required parameter 'propertyCode' is set
        if ( propertyCode == null ) {
            throw new ApiException( "Missing the required parameter 'propertyCode' when calling hotelPropertyCodeSearch(Async)" );
        }

        // verify the required parameter 'checkIn' is set
        if ( checkIn == null ) {
            throw new ApiException( "Missing the required parameter 'checkIn' when calling hotelPropertyCodeSearch(Async)" );
        }

        // verify the required parameter 'checkOut' is set
        if ( checkOut == null ) {
            throw new ApiException( "Missing the required parameter 'checkOut' when calling hotelPropertyCodeSearch(Async)" );
        }


        return hotelPropertyCodeSearchCall( apikey, propertyCode, checkIn, checkOut, lang, currency, allRooms, showSoldOut, progressListener, progressRequestListener );

    }

    /**
     * Hotel Property Code Search - Find out more room and rate information once you have found your preferred hotel.
     * &lt;p&gt;This API allows you to quickly see the detailed information of a single hotel, including descriptions, address, GPS location, amenities, awards, lowest priced room and all room prices and booking information. &lt;/p&gt;  &lt;p&gt;This API gives you more information on a specific property. Optional parameters such as show_sold_out &amp; rooms can be used to show sold out rooms and all available rooms. &lt;/p&gt;  &lt;p&gt;The API is based on our high-speed hotel pricing cache, which is also used to power the &lt;a href&#x3D;\&quot;https://hotelsearchengine.amadeus.com/\&quot;&gt;Amadeus Hotel Search Engine&lt;/a&gt; application. Results are returned very quickly, response times are generally under 2s. Our cache has great global coverage and is constantly refreshed with the latest prices.&lt;/p&gt;
     *
     * @param apikey       API Key provided for your account, to identify you for API access. Make sure to keep this API key secret. (required)
     * @param propertyCode A Hotel property code based on 2 letter chain code + 3 letter &lt;a href&#x3D;\&quot;https://en.wikipedia.org/wiki/International_Air_Transport_Association_airport_code\&quot;&gt;IATA code&lt;/a&gt; of the city + 3 char property unique id. (required)
     * @param checkIn      Date on which the guest will begin their stay in the hotel. Past availability is not displayed, future availability becomes less useful from about 6 months from the current date. (required)
     * @param checkOut     Date on which the guest will end their stay in the hotel. (required)
     * @param lang         The preferred language of the content related to each hotel. Content will be returned in this language if available. (optional, default to EN)
     * @param currency     The preferred &lt;a href&#x3D;\&quot;https://en.wikipedia.org/wiki/ISO_4217\&quot;&gt;currency&lt;/a&gt; for the results (optional, default to USD)
     * @param allRooms     This option if enabled will return all hotel room rates, not just the lowest room rate. Note: This will have an impact on the response time due to the larger messages returned. (optional, default to true)
     * @param showSoldOut  This option if enabled will return hotel names and addresses even if rooms are sold out (closed properties) (optional, default to false)
     * @return HotelPropertyResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public HotelPropertyResponse hotelPropertyCodeSearch( String apikey, String propertyCode, String checkIn, String checkOut, String lang,
                                                          String currency, Boolean allRooms, Boolean showSoldOut ) throws ApiException {
        ApiResponse<HotelPropertyResponse> resp = hotelPropertyCodeSearchWithHttpInfo( apikey, propertyCode, checkIn, checkOut, lang, currency, allRooms, showSoldOut );
        return resp.getData();
    }

    /**
     * Hotel Property Code Search - Find out more room and rate information once you have found your preferred hotel.
     * &lt;p&gt;This API allows you to quickly see the detailed information of a single hotel, including descriptions, address, GPS location, amenities, awards, lowest priced room and all room prices and booking information. &lt;/p&gt;  &lt;p&gt;This API gives you more information on a specific property. Optional parameters such as show_sold_out &amp; rooms can be used to show sold out rooms and all available rooms. &lt;/p&gt;  &lt;p&gt;The API is based on our high-speed hotel pricing cache, which is also used to power the &lt;a href&#x3D;\&quot;https://hotelsearchengine.amadeus.com/\&quot;&gt;Amadeus Hotel Search Engine&lt;/a&gt; application. Results are returned very quickly, response times are generally under 2s. Our cache has great global coverage and is constantly refreshed with the latest prices.&lt;/p&gt;
     *
     * @param apikey       API Key provided for your account, to identify you for API access. Make sure to keep this API key secret. (required)
     * @param propertyCode A Hotel property code based on 2 letter chain code + 3 letter &lt;a href&#x3D;\&quot;https://en.wikipedia.org/wiki/International_Air_Transport_Association_airport_code\&quot;&gt;IATA code&lt;/a&gt; of the city + 3 char property unique id. (required)
     * @param checkIn      Date on which the guest will begin their stay in the hotel. Past availability is not displayed, future availability becomes less useful from about 6 months from the current date. (required)
     * @param checkOut     Date on which the guest will end their stay in the hotel. (required)
     * @param lang         The preferred language of the content related to each hotel. Content will be returned in this language if available. (optional, default to EN)
     * @param currency     The preferred &lt;a href&#x3D;\&quot;https://en.wikipedia.org/wiki/ISO_4217\&quot;&gt;currency&lt;/a&gt; for the results (optional, default to USD)
     * @param allRooms     This option if enabled will return all hotel room rates, not just the lowest room rate. Note: This will have an impact on the response time due to the larger messages returned. (optional, default to true)
     * @param showSoldOut  This option if enabled will return hotel names and addresses even if rooms are sold out (closed properties) (optional, default to false)
     * @return ApiResponse&lt;HotelPropertyResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<HotelPropertyResponse> hotelPropertyCodeSearchWithHttpInfo( String apikey, String propertyCode, String checkIn, String checkOut, String lang, String currency, Boolean allRooms, Boolean showSoldOut ) throws ApiException {
        com.squareup.okhttp.Call call = hotelPropertyCodeSearchValidateBeforeCall( apikey, propertyCode, checkIn, checkOut, lang, currency, allRooms, showSoldOut, null, null );
        Type localVarReturnType = new TypeToken<HotelPropertyResponse>() {
        }.getType();
        return apiClient.execute( call, localVarReturnType );
    }

    /**
     * Hotel Property Code Search - Find out more room and rate information once you have found your preferred hotel. (asynchronously)
     * &lt;p&gt;This API allows you to quickly see the detailed information of a single hotel, including descriptions, address, GPS location, amenities, awards, lowest priced room and all room prices and booking information. &lt;/p&gt;  &lt;p&gt;This API gives you more information on a specific property. Optional parameters such as show_sold_out &amp; rooms can be used to show sold out rooms and all available rooms. &lt;/p&gt;  &lt;p&gt;The API is based on our high-speed hotel pricing cache, which is also used to power the &lt;a href&#x3D;\&quot;https://hotelsearchengine.amadeus.com/\&quot;&gt;Amadeus Hotel Search Engine&lt;/a&gt; application. Results are returned very quickly, response times are generally under 2s. Our cache has great global coverage and is constantly refreshed with the latest prices.&lt;/p&gt;
     *
     * @param apikey       API Key provided for your account, to identify you for API access. Make sure to keep this API key secret. (required)
     * @param propertyCode A Hotel property code based on 2 letter chain code + 3 letter &lt;a href&#x3D;\&quot;https://en.wikipedia.org/wiki/International_Air_Transport_Association_airport_code\&quot;&gt;IATA code&lt;/a&gt; of the city + 3 char property unique id. (required)
     * @param checkIn      Date on which the guest will begin their stay in the hotel. Past availability is not displayed, future availability becomes less useful from about 6 months from the current date. (required)
     * @param checkOut     Date on which the guest will end their stay in the hotel. (required)
     * @param lang         The preferred language of the content related to each hotel. Content will be returned in this language if available. (optional, default to EN)
     * @param currency     The preferred &lt;a href&#x3D;\&quot;https://en.wikipedia.org/wiki/ISO_4217\&quot;&gt;currency&lt;/a&gt; for the results (optional, default to USD)
     * @param allRooms     This option if enabled will return all hotel room rates, not just the lowest room rate. Note: This will have an impact on the response time due to the larger messages returned. (optional, default to true)
     * @param showSoldOut  This option if enabled will return hotel names and addresses even if rooms are sold out (closed properties) (optional, default to false)
     * @param callback     The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call hotelPropertyCodeSearchAsync( String apikey, String propertyCode, String checkIn, String checkOut, String lang, String currency, Boolean allRooms,
                                                                  Boolean showSoldOut, final ApiCallback<HotelPropertyResponse> callback ) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if ( callback != null ) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update( long bytesRead, long contentLength, boolean done ) {
                    callback.onDownloadProgress( bytesRead, contentLength, done );
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress( long bytesWritten, long contentLength, boolean done ) {
                    callback.onUploadProgress( bytesWritten, contentLength, done );
                }
            };
        }

        com.squareup.okhttp.Call call = hotelPropertyCodeSearchValidateBeforeCall( apikey, propertyCode, checkIn, checkOut, lang, currency, allRooms, showSoldOut, progressListener, progressRequestListener );
        Type localVarReturnType = new TypeToken<HotelPropertyResponse>() {
        }.getType();
        apiClient.executeAsync( call, localVarReturnType, callback );
        return call;
    }
}
