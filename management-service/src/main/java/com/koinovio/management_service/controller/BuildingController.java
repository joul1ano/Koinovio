package com.koinovio.management_service.controller;

import com.koinovio.management_service.dto.building.BuildingResponse;
import com.koinovio.management_service.dto.building.CreateBuildingRequest;
import com.koinovio.management_service.dto.messaging.BuildingExpensesMessage;
import com.koinovio.management_service.dto.messaging.BuildingExpensesRequest;
import com.koinovio.management_service.service.BuildingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/buildings")
@Tag(name = "Buildings", description = "Operations related to buildings")
public class BuildingController {
    private final BuildingService buildingService;

    //todo: add permission by role (hasAdmin), in case i add tenant log in
    @PostMapping
    public ResponseEntity<BuildingResponse> createNewBuilding(@RequestBody @Valid CreateBuildingRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(buildingService.createNewBuilding(request));
    }

    @GetMapping
    public ResponseEntity<List<BuildingResponse>> getAllBuildings(){
        return ResponseEntity.ok(buildingService.getAllBuildings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildingResponse> getBuildingById(@PathVariable Long id){
        return ResponseEntity.ok(buildingService.getBuildingById(id));
    }

    //todo: place this to a separate controller maybe
    @PostMapping("/{id}/expenses")
    public ResponseEntity<Void> submitBuildingExpenses(@PathVariable Long id, @RequestBody @Valid BuildingExpensesRequest request){
        buildingService.submitExpenses(id,request);
        return ResponseEntity.accepted().build();

    }



}
