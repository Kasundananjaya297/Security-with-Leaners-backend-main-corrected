package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.PaymentsDTO;
import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.DTO.UsersDTO;
import com.example.SecuritywithLeaners.Entity.*;
import com.example.SecuritywithLeaners.Entity.Package;
import com.example.SecuritywithLeaners.Repo.AgreementRepo;
import com.example.SecuritywithLeaners.Repo.PaymentsRepo;
import com.example.SecuritywithLeaners.Repo.StudentRepo;
import com.example.SecuritywithLeaners.Util.SaveUer;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class PaymentService {
    @Autowired
    private PaymentsRepo paymentsRepo; //inject paymentsRepo
    @Autowired
    private AgreementRepo agreementRepo; //inject agreementRepo
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private SaveUer saveUer;

    public ResponseDTO savePayments(PaymentsDTO PaymentsDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        Payments payments = new Payments();
        Agreement agreement = new Agreement();
        Student student = new Student();
        Package aPackage = new Package();// created new object of package

        AgreementID agreementID = new AgreementID();

        student.setStdID(PaymentsDTO.getStdID());// set student id
        aPackage.setPackageID(PaymentsDTO.getPackageID()); // set package id

        agreement.setStdID(student);
        agreement.setPackageID(aPackage);

        agreementID.setPackageID(aPackage);
        agreementID.setStdID(student);
        agreement.setAgreementID(agreementID);

        payments.setAmount(PaymentsDTO.getAmount());
        payments.setPaymentDate(PaymentsDTO.getPaymentDate());
        payments.setPaymentTime(PaymentsDTO.getPaymentTime());
        payments.setAgreement(agreement);
        try {
            if(agreementRepo.existsById(agreementID)){ // check if agreement exists
                System.out.print("Agreement found");
                double totalAmountPaid = paymentsRepo.getTotalAmountPaid(PaymentsDTO.getStdID(),PaymentsDTO.getPackageID());
                double amount = agreementRepo.getTotalAmountToPay(PaymentsDTO.getStdID());
                //System.out.println("total amount pays "+agreementRepo.getTotalAmountToPays(PaymentsDTO.getStdID()).toString());
//                System.out.println("Amount to pay: "+amount);
//                System.out.printf("Amount paid: %.2f\n",PaymentsDTO.getAmount());
                if(totalAmountPaid == 0){
                    studentRepo.updateRegistrationStatus(true,PaymentsDTO.getStdID());
                    //gernerate passord and add student to user table
                    UsersDTO usersDTO = new UsersDTO();
                    usersDTO.setUsername(PaymentsDTO.getStdID());
                    usersDTO.setRole("STUDENT");
                    usersDTO.setIsActive(true);
                    usersDTO.setGeneratedPassword(SaveUer.generateRandomPassword(PaymentsDTO.getStdID()));
                    authenticationService.SaveUserInternally(usersDTO);
                }

                if(amount >= (totalAmountPaid+ PaymentsDTO.getAmount())){
                    paymentsRepo.save(payments);
                    responseDTO.setMessage("Payment saved successfully");
                    double totalAmountPaid1 = paymentsRepo.getTotalAmountPaid(PaymentsDTO.getStdID(),PaymentsDTO.getPackageID());
                    agreementRepo.updateTotalAmountPaid(PaymentsDTO.getStdID(),totalAmountPaid1,PaymentsDTO.getPackageID());
                    responseDTO.setStatus(HttpStatus.ACCEPTED);
                    responseDTO.setCode(varList.RSP_SUCCES);
                }else {
                    responseDTO.setMessage("Amount paid is more than the total amount to pay");
                    responseDTO.setStatus(HttpStatus.BAD_REQUEST);
                    responseDTO.setCode(varList.RSP_FAIL);
                }

            }else{
                responseDTO.setMessage("Agreement not found");
                responseDTO.setStatus(HttpStatus.NOT_FOUND);
                responseDTO.setCode(varList.RSP_NO_DATA_FOUND);
            }

        }catch (Exception e){
            responseDTO.setMessage("Payment not saved");
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            log.error("Error in saving payment",e);
            responseDTO.setCode(varList.RSP_FAIL);
        }
        return responseDTO;
    }
    public ResponseDTO getPayments(String stdID, String packageID){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if (paymentsRepo.getPaymentsByStdIDAndPackageID(stdID, packageID).size() > 0) {
                List<PaymentsDTO> paymentDTOs =new ArrayList<>();
                for(Payments payments:paymentsRepo.getPaymentsByStdIDAndPackageID(stdID, packageID)){
                    PaymentsDTO paymentsDTO = new PaymentsDTO();
                    paymentsDTO.setAmount(payments.getAmount());
                    paymentsDTO.setPaymentDate(payments.getPaymentDate());
                    paymentsDTO.setPaymentTime(payments.getPaymentTime());
                    paymentsDTO.setPaymentID(payments.getPaymentID());
                    paymentDTOs.add(paymentsDTO);
                }
                responseDTO.setContent(paymentDTOs);
                responseDTO.setMessage("Payments found");
                responseDTO.setStatus(HttpStatus.ACCEPTED);
                responseDTO.setCode(varList.RSP_SUCCES);
            } else {
                responseDTO.setMessage("Payments not found");
                responseDTO.setStatus(HttpStatus.NOT_FOUND);
                responseDTO.setCode(varList.RSP_NO_DATA_FOUND);
            }
        }catch (Exception e){
            responseDTO.setMessage("Error in getting payments");
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            log.error("Error in getting payments",e);
            responseDTO.setCode(varList.RSP_FAIL);
        }

        return responseDTO;
    }

}
