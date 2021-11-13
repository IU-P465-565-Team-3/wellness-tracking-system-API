package com.wellness.tracking.dto.mapper;

import com.wellness.tracking.dto.ListingDTO;
import com.wellness.tracking.model.FitnessPlan;
import com.wellness.tracking.model.Listing;
import com.wellness.tracking.model.MultimediaPost;
import org.springframework.beans.BeanUtils;

public class ListingMapper<PK extends java.io.Serializable> {
    public static Listing toListing(ListingDTO listingDTO) {
        Listing listing;
        switch (listingDTO.getType()) {
            case FITNESS_PLAN:
                listing = new FitnessPlan();
                break;
            case MULTIMEDIA_POST:
                listing = new MultimediaPost();
                break;
            default:
                listing = new Listing();
        }
        BeanUtils.copyProperties(listingDTO, listing);
        return listing;
    }

    public ListingDTO toListingDTO(Listing listing) {
        ListingDTO dto = new ListingDTO<PK>();
        BeanUtils.copyProperties(listing, dto);
        return dto;
    }

}
