package com.ubb.licenta.rating.service;

import com.ubb.licenta.constants.DocumentTypeEnum;
import com.ubb.licenta.rating.ReviewsRatingInfo;
import com.ubb.licenta.rating.entity.ReviewsRatingEntity;
import com.ubb.licenta.rating.repository.ReviewsRatingRepository;
import com.ubb.licenta.utils.ValidationUtils;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReviewsRatingObject {

    @Autowired
    private ReviewsRatingRepository reviewsRatingRepository;

    private final static MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private final static MapperFacade mapper = mapperFactory.getMapperFacade();

    static {
        mapperFactory.classMap( ReviewsRatingInfo.class, ReviewsRatingEntity.class )
                .byDefault()
                .register();

        mapperFactory.classMap( ReviewsRatingEntity.class, ReviewsRatingInfo.class )
                .byDefault()
                .register();
    }

    public ReviewsRatingInfo saveReviewRating( ReviewsRatingInfo reviewsRatingInfo ) {
        ValidationUtils.validateRequiredObject( reviewsRatingInfo, "reviewsRatingInfo" );
        ValidationUtils.validateRequiredObject( reviewsRatingInfo.getRating(), "reviewsRatingInfo.rating" );

        ReviewsRatingEntity reviewsRatingEntity = mapper.map( reviewsRatingInfo, ReviewsRatingEntity.class );
        reviewsRatingEntity.setType( DocumentTypeEnum.REVIEW_RATING_TYPE.getType() );

        return mapper.map( reviewsRatingRepository.save( reviewsRatingEntity ), ReviewsRatingInfo.class );
    }

    public List<ReviewsRatingInfo> getReviewsRatingByHotelCodes( List<String> hotelCodes ) {
        ValidationUtils.validateRequiredCollection( hotelCodes, "hotelCodes" );

        List<ReviewsRatingEntity> reviewsRatingEntityList = reviewsRatingRepository.findAllByHotelCodeIsIn( hotelCodes );

        return reviewsRatingEntityList.stream().map( reviewsRatingEntity -> mapper.map( reviewsRatingEntity, ReviewsRatingInfo.class ) ).collect( Collectors.toList() );
    }

    public ReviewsRatingInfo getReviewsRatingByHotelCode( String hotelCode ) {
        ValidationUtils.validateRequiredObject( hotelCode, "hotelCode" );

        return mapper.map( reviewsRatingRepository.findByHotelCode( hotelCode ), ReviewsRatingInfo.class );
    }
}
