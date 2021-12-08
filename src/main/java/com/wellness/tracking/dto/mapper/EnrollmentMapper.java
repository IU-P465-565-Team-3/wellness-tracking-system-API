package com.wellness.tracking.dto.mapper;

import com.wellness.tracking.dto.EnrollmentDTO;
import com.wellness.tracking.dto.ListingDTO;
import com.wellness.tracking.model.Enrollment;
import org.springframework.beans.BeanUtils;

public class EnrollmentMapper<PK extends java.io.Serializable> {
    public static Enrollment toEnrollment(EnrollmentDTO enrollmentDTO) {
        Enrollment enrollment = new Enrollment();
        BeanUtils.copyProperties(enrollmentDTO, enrollment);
        if (enrollmentDTO.getListing() != null) {
            enrollment.setListing(ListingMapper.toListing(enrollmentDTO.getListing()));
        }
        return enrollment;
    }

    public EnrollmentDTO toEnrollmentDTO(Enrollment enrollment) {
        EnrollmentDTO enrollmentDTO = new EnrollmentDTO<PK>();
        ListingDTO listingDTO = new ListingMapper<Long>().toListingDTO(enrollment.getListing());
        BeanUtils.copyProperties(enrollment, enrollmentDTO);
        enrollmentDTO.setListing(listingDTO);
        return enrollmentDTO;
    }

}
