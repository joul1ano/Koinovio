package com.koinovio.management_service.mapper;

import com.koinovio.management_service.dto.tenant.TenantResponse;
import com.koinovio.management_service.model.Tenant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TenantMapper {
    TenantResponse toTenantResponse(Tenant tenant);
}
