package com.koinovio.management_service.mapper;

import com.koinovio.management_service.dto.apartment.ApartmentResponse;
import com.koinovio.management_service.model.Apartment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApartmentMapper {
    @Mapping(source = "id", target = "apartmentId")
    @Mapping(source = "building.id", target = "buildingId")
    @Mapping(source = "tenant", target = "tenant")
    ApartmentResponse toApartmentResponse(Apartment apartment);
}
