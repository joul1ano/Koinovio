package com.koinovio.management_service.service;

import com.koinovio.management_service.dto.building.BuildingResponse;
import com.koinovio.management_service.dto.apartment.CreateApartmentRequest;
import com.koinovio.management_service.dto.building.CreateBuildingRequest;
import com.koinovio.management_service.dto.messaging.ApartmentBillInfo;
import com.koinovio.management_service.dto.messaging.BuildingExpensesMessage;
import com.koinovio.management_service.dto.messaging.BuildingExpensesRequest;
import com.koinovio.management_service.exceptions.ResourceNotFoundException;
import com.koinovio.management_service.messaging.BillingMessageProducer;
import com.koinovio.management_service.model.Apartment;
import com.koinovio.management_service.model.Building;
import com.koinovio.management_service.model.Tenant;
import com.koinovio.management_service.repository.ApartmentRepository;
import com.koinovio.management_service.repository.BuildingRepository;
import com.koinovio.management_service.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingRepository buildingRepository;
    private final ApartmentRepository apartmentRepository;
    private final TenantRepository tenantRepository;
    private final BillingMessageProducer billingMessageProducer;

    @Transactional
    public BuildingResponse createNewBuilding(CreateBuildingRequest request) {
        //saving building
        var building = Building.builder()
                .address(request.getAddress())
                .floors(request.getFloors())
                .zipCode(request.getZipCode()).build();
        var savedBuilding = buildingRepository.save(building);


        //saving apartments and entities

        for(CreateApartmentRequest apartmReq : request.getApartmentsList()){
            var apartment = Apartment.builder()
                    .floor(apartmReq.getFloor())
                    .size(apartmReq.getSize())
                    .building(savedBuilding).build();
            var savedApartment = apartmentRepository.save(apartment);

            if(apartmReq.getTenant() != null && !apartmReq.getTenant().getName().isBlank() && !apartmReq.getTenant().getEmail().isBlank()){
                var tenant = Tenant.builder()
                        .name(apartmReq.getTenant().getName())
                        .email(apartmReq.getTenant().getEmail())
                        .isActive(true)
                        .apartment(savedApartment).build();
                tenantRepository.save(tenant);
            }

        }

        return BuildingResponse.builder()
                .buildingId(savedBuilding.getId())
                .address(savedBuilding.getAddress())
                .zipCode(savedBuilding.getZipCode())
                .floors(savedBuilding.getFloors())
                .numberOfApartments(request.getApartmentsList().size())
                .build();

    }

    public List<BuildingResponse> getAllBuildings() {
        List<Building> buildings = buildingRepository.findAll();

        return buildings.stream().map(building -> BuildingResponse.builder()
                .buildingId(building.getId())
                .address(building.getAddress())
                .zipCode(building.getZipCode())
                .floors(building.getFloors())
                .numberOfApartments(apartmentRepository.countByBuilding_Id(building.getId()))
                .build())
                .toList();

    }

    public BuildingResponse getBuildingById(Long id) {
        var building = buildingRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Building with id: " + id +" does not exist"));

        return BuildingResponse.builder()
                .buildingId(building.getId())
                .address(building.getAddress())
                .zipCode(building.getZipCode())
                .floors(building.getFloors())
                .numberOfApartments(apartmentRepository.countByBuilding_Id(building.getId()))
                .build();
    }

    @Transactional
    public void submitExpenses(Long buildingId, BuildingExpensesRequest request){
        //todo: make sure that the expenses are submited for a valid month
        Building building = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new ResourceNotFoundException("Building not found"));

        List<ApartmentBillInfo> apartmentBillInfos = apartmentRepository.findAllByBuilding_Id(buildingId)
                .stream().map(
                        apartment -> ApartmentBillInfo.builder()
                                .apartmentId(apartment.getId())
                                .floor(apartment.getFloor())
                                .size(apartment.getSize())
                                .tenantName(apartment.getTenant() != null ? apartment.getTenant().getName() : null)
                                .tenantEmail(apartment.getTenant() != null ? apartment.getTenant().getEmail() : null)
                                .build()
                ).toList();

        BuildingExpensesMessage message = BuildingExpensesMessage.builder()
                .buildingId(buildingId)
                .buildingAddress(building.getAddress())
                .month(request.getMonth())
                .year(request.getYear())
                .expenses(request.getExpenses())
                .apartments(apartmentBillInfos)
                .build();

        billingMessageProducer.sendBillingMessage(message);
        building.setLastExpenseSubmission(LocalDate.of(request.getYear(), request.getMonth(), 1));
    }
}
