package com.koinovio.billing_service.service;

import com.koinovio.billing_service.dto.ApartmentBillInfo;
import com.koinovio.billing_service.dto.BuildingExpensesMessage;
import com.koinovio.billing_service.dto.ExpenseItem;
import com.koinovio.billing_service.model.Bill;
import com.koinovio.billing_service.model.BillItem;
import com.koinovio.billing_service.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillingService {
    private final BillRepository billRepository;
    private final PdfGeneratorService pdfService;
    private final EmailService emailService;

    public void processBill(BuildingExpensesMessage message) {
        // in case something goes wrong and rabbitmq keeps requeuing a message
        if(billRepository.existsByBuildingIdAndMonthAndYear(message.getBuildingId(), message.getMonth(), message.getYear())){
            log.warn("Bills already processed for building {} {}/{} - Skipping", message.getBuildingId(),message.getMonth(),message.getYear());
            return;
        }
        List<Bill> bills = new ArrayList<>();


        List<ApartmentBillInfo> inhabitedApartments = message.getApartments().stream()
                .filter(apartmentBillInfo -> apartmentBillInfo.getTenantEmail() != null)
                .toList();

        long numberOfAptsInGroundFloor = inhabitedApartments.stream()
                .filter(apartmentBillInfo -> apartmentBillInfo.getFloor() == 0)
                .count();

        for(ApartmentBillInfo apartment : inhabitedApartments){
            List<BillItem> billItems = new ArrayList<>();

            for(ExpenseItem expenseItem : message.getExpenses()){
                if (expenseItem.getDescription().toLowerCase().contains("elevator") && apartment.getFloor() ==0){
                    billItems.add(BillItem.builder()
                            .description(expenseItem.getDescription())
                            .amount(0.0)
                            .build());
                }else if(expenseItem.getDescription().toLowerCase().contains("elevator")){
                    billItems.add(BillItem.builder()
                            .description(expenseItem.getDescription())
                            .amount(expenseItem.getAmount() / (inhabitedApartments.size() - numberOfAptsInGroundFloor))
                            .build());
                }else{
                    billItems.add(BillItem.builder()
                            .description(expenseItem.getDescription())
                            .amount(expenseItem.getAmount() / inhabitedApartments.size())
                            .build());
                }

            }

            bills.add(Bill.builder()
                    .buildingId(message.getBuildingId())
                    .buildingAddress(message.getBuildingAddress())
                    .month(message.getMonth())
                    .year(message.getYear())
                    .apartmentId(apartment.getApartmentId())
                    .floor(apartment.getFloor())
                    .tenantName(apartment.getTenantName())
                    .tenantEmail(apartment.getTenantEmail())
                    .billItems(billItems)
                    .totalAmount(billItems.stream().mapToDouble(BillItem::getAmount).sum())
                    .documentCreatedAt(LocalDateTime.now())
                    .build());
        }
        billRepository.saveAll(bills);
        sendBillEmails(bills);

    }

    private void sendBillEmails(List<Bill> bills) {
        bills.stream().forEach(bill -> {
            try {
                emailService.sendBillEmail(bill, pdfService.generateBillPdf(bill));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
