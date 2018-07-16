package com.ubb.licenta.hotelsearch.service;

import com.ubb.licenta.hotel.HotelPropertyInfo;
import com.ubb.licenta.hotelsearch.*;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.ubb.licenta.constants.ApplicationConstants.DEFAULT_RADIUS_HOTEL_SEARCH;
import static com.ubb.licenta.constants.ApplicationConstants.HOTELS_PER_PAGE;

@Component
public class HotelSearchObject {

    @Autowired
    private HotelSearchRepository hotelSearchRepository;

    public HotelSearchUIResponse searchHotels( HotelSearchRequestCriteria hotelSearchRequestCriteria ) {
        Integer currentPage;

        if ( hotelSearchRequestCriteria.getInitialSearch() ) {
            currentPage = 1;

        } else {
            currentPage = hotelSearchRequestCriteria.getStartIndex() / 10 + 1;
        }

        //this method is cached based on the given search criteria
        //LinkedHashMap used to keep the order of insertion for found hotels
        LinkedHashMap<String, HotelPropertyInfo> hotels = hotelSearchRepository.getAllHotelsByCircle( hotelSearchRequestCriteria );

        Integer initialHotelCount = hotels.size();

        hotels = hotelSearchRepository.paginateForHotelSearch(currentPage, hotels);

        return processHotels( hotels, currentPage, initialHotelCount );
    }

    public HotelSearchUIResponse searchByHotel( HotelSearchRequestCriteria hotelSearchRequestCriteria ) {
        Map<String, HotelPropertyInfo> allHotelsPerLocation = hotelSearchRepository.searchByHotel( hotelSearchRequestCriteria );
        Integer currentPage = 1;

        return processHotels( allHotelsPerLocation, currentPage, 1 );
    }

    /**
     * Map the final response for the UI
     */
    private HotelSearchUIResponse processHotels( Map<String, HotelPropertyInfo> hotels,
                                                 Integer currentPage, Integer initialHotelCount) {
        HotelSearchUIResponse hotelSearchUIResponse = new HotelSearchUIResponse();
        hotelSearchUIResponse.setResults( new ArrayList<>() );
        Boolean successful = false;

        if ( !CollectionUtils.isEmpty( hotels ) ) {
            successful = true;
            for ( String hotelCode : hotels.keySet() ) {
                HotelUIInfoResponse hotelUIInfoResponse = new HotelUIInfoResponse();
                HotelPropertyInfo hotelPropertyInfo = hotels.get( hotelCode );

                if ( hotelPropertyInfo.getTotalPriceAppropriateRoom() != null ) {
                    hotelUIInfoResponse.setPropertyCode( hotelCode );
                    hotelUIInfoResponse.setPropertyName( hotelPropertyInfo.getPropertyName() );
                    hotelUIInfoResponse.setAddress( hotelPropertyInfo.getAddress() );
                    hotelUIInfoResponse.setLocation( hotelPropertyInfo.getLocation() );
                    hotelUIInfoResponse.setTotalPrice( hotelPropertyInfo.getTotalPriceAppropriateRoom() );
                    hotelUIInfoResponse.setRating( hotelPropertyInfo.getRating() );
                    hotelUIInfoResponse.setMaxRoomDimension( hotelPropertyInfo.getMaxRoomDimension() );
                    hotelUIInfoResponse.setPictureUrl( hotelPropertyInfo.getPictureUrl() );

                    hotelSearchUIResponse.getResults().add( hotelUIInfoResponse );
                }
            }

        }

        hotelSearchUIResponse.setSuccessful( successful );
        hotelSearchUIResponse.setTotalNrOfHotels( initialHotelCount );
        hotelSearchUIResponse.setTotalNrOfPages( computeTotalNrOfPages( initialHotelCount ) );
        hotelSearchUIResponse.setCurrentPage( currentPage );

        return hotelSearchUIResponse;
    }


    public HotelSearchUIResponse sortHotels( SortHotelsCriteria sortHotelsCriteria ) {
        Integer currentPage;

        HotelSearchUIResponse hotelSearchUIResponse;
        List<HotelUIInfoResponse> sortedHotels;
        Boolean successful = false;

        HotelSearchRequestCriteria hotelSearchRequestCriteria = new HotelSearchRequestCriteria();
        hotelSearchRequestCriteria.setRadius( sortHotelsCriteria.getRadius() == null
                ? Integer.valueOf(DEFAULT_RADIUS_HOTEL_SEARCH) : sortHotelsCriteria.getRadius() );
        hotelSearchRequestCriteria.setPersons(sortHotelsCriteria.getPersons());
        hotelSearchRequestCriteria.setLongitude(sortHotelsCriteria.getLongitude());
        hotelSearchRequestCriteria.setLatitude(sortHotelsCriteria.getLatitude());
        hotelSearchRequestCriteria.setInDate(sortHotelsCriteria.getCheckInDate());
        hotelSearchRequestCriteria.setOutDate(sortHotelsCriteria.getCheckOutDate());
        hotelSearchRequestCriteria.setFullAddress(sortHotelsCriteria.getAddress());

        LinkedHashMap<String, HotelPropertyInfo> allHotelsPerLocation = hotelSearchRepository.getAllHotelsByCircle(hotelSearchRequestCriteria);

        if ( sortHotelsCriteria.getInitial() ) {
            currentPage = 1;
        } else {
            currentPage = sortHotelsCriteria.getStartIndex() / 10 + 1;
        }

        hotelSearchUIResponse = processHotels( allHotelsPerLocation, currentPage, allHotelsPerLocation.size() );

        //cacheable method.... or IS IT ? :O
        sortedHotels = sortHotelsForUI( hotelSearchUIResponse, sortHotelsCriteria, currentPage);

        List<HotelUIInfoResponse> hotels = paginateSortedHotels( currentPage, sortedHotels );
        if (!CollectionUtils.isEmpty( hotels ) ) {
            hotelSearchUIResponse.setResults( hotels );
            successful = true;
        }

        hotelSearchUIResponse.setSuccessful( successful );
        hotelSearchUIResponse.setIsSort( true );

        return hotelSearchUIResponse;
    }

    private List<HotelUIInfoResponse> paginateSortedHotels(Integer currentPage, List<HotelUIInfoResponse> sortedHotels) {

        Integer totalNrOfHotels = sortedHotels.size();
        Integer startIndex = HOTELS_PER_PAGE * (currentPage - 1);   //startIndex begins at 0
        Integer endIndex = totalNrOfHotels < (startIndex + HOTELS_PER_PAGE)
                ? totalNrOfHotels : startIndex + HOTELS_PER_PAGE;

        return sortedHotels.subList(startIndex, endIndex);
    }

    private Integer computeTotalNrOfPages( Integer nrOfHotels ) {
        Integer pages = nrOfHotels / HOTELS_PER_PAGE;

        if ( nrOfHotels % HOTELS_PER_PAGE != 0 ) {
            pages++;
        }

        return pages;
    }

    public List<HotelUIInfoResponse> sortHotelsForUI(HotelSearchUIResponse hotelSearchUIResponse,
                                                     SortHotelsCriteria sortHotelsCriteria,
                                                     Integer currentPage) {

        List<HotelUIInfoResponse> hotels = hotelSearchUIResponse.getResults();

        if ( !CollectionUtils.isEmpty( hotels ) ) {

            if ( !sortHotelsCriteria.getNotSort() ) {
                Comparator<HotelUIInfoResponse> hotelPriceComparator;

                if ( BooleanUtils.isTrue(sortHotelsCriteria.getByRating() ) ) {
                    hotelPriceComparator = Comparator.comparing( HotelUIInfoResponse::getRating );

                } else if ( sortHotelsCriteria.getByRoomDimension() ) {
                    //scot hotelurile ce nu au pretul setat
                    hotels = hotels.stream().filter( hotel -> hotel.getMaxRoomDimension()
                            .compareTo( BigDecimal.valueOf( -1 ) ) > 0 ).collect( Collectors.toList() );
                    hotelPriceComparator = Comparator.comparing( HotelUIInfoResponse::getMaxRoomDimension );

                } else {
                    //scot hotelurile ce nu au dimensiunea camerei setata //Todo: vazut daca e ok sau sa pastrez si hotelurile ce nu o trimis dimensiune
                    hotels = hotels.stream().filter( hotel -> hotel.getTotalPrice() != null ).collect( Collectors.toList() );
                    hotelPriceComparator = Comparator.comparing( h1 -> new BigDecimal( h1.getTotalPrice().getAmount() ) );
                }

                if ( ( sortHotelsCriteria.getAscending() == null || sortHotelsCriteria.getAscending() ) && sortHotelsCriteria.getByPrice() ) {
                    hotels.sort( hotelPriceComparator );
                } else {
                    hotels.sort( hotelPriceComparator.reversed() );
                }
            }

            hotelSearchUIResponse.setTotalNrOfHotels( hotels.size() );
            hotelSearchUIResponse.setTotalNrOfPages( computeTotalNrOfPages( hotels.size() ) );
            hotelSearchUIResponse.setCurrentPage( currentPage );

        }

        return hotels;
    }

}
