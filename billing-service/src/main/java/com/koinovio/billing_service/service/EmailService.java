package com.koinovio.billing_service.service;

import com.koinovio.billing_service.model.Bill;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendBillEmail(Bill bill, byte[] pdf){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message,true);

            messageHelper.setFrom("noreply@koinovio.com");
            messageHelper.setTo(bill.getTenantEmail());
            messageHelper.setSubject("Your Koinovio bill for " + bill.getMonth() + "/" + bill.getYear());
            messageHelper.setText(
                    "Dear " + bill.getTenantName() + ", \n\n" +
                            "Please find your bill for " + bill.getMonth() + "/" + bill.getYear() +
                            "Total amount: " + bill.getTotalAmount() + " €" + "\n\n" +
                            "Regards, \nKoinovio"
            );

            messageHelper.addAttachment(
                    "bill_" + bill.getMonth() + "_" + bill.getYear() + ".pdf",
                    new ByteArrayResource(pdf)
            );
            mailSender.send(message);
            log.info("Email sent succesfully to: {}", bill.getTenantEmail());


        }catch (MessagingException ex){
            log.error("Failed to send email to: {}", bill.getTenantEmail());
        }

    }
}
