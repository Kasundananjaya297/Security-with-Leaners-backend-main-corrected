package com.example.SecuritywithLeaners.Controller;


import com.example.SecuritywithLeaners.DTO.*;
import com.example.SecuritywithLeaners.Entity.*;
import com.example.SecuritywithLeaners.Service.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping( "api/admin")
@CrossOrigin
public class AdminServicesController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private TrialPermitService trialPermitService;
    @Autowired
    private VehicleTypeService vehicleType;
    @Autowired
    MedicalReportService medicalReportService;
    @Autowired
    AgreementService agreementService;
    @Autowired
    PackageService packageService;
    @Autowired
    ExtraSessionService extraSessionService;
    @Autowired
    PaymentService paymentService;
    @Autowired
    private VehicleTypeService vehicleTypeService;
    @Autowired
    private ExtraSessionNotInAgreementService extraSessionNotInAgreementService;
    @Autowired
    private VehicleService vechicleService;
    @Autowired
    private FuelTypeService fuelTypeService;
    @Autowired
    private VehicleLicenceService vehicleLicenceService;
    @Autowired
    private InsuranceTypeService insuranceTypeService;
    @Autowired
    private InsuranceService insuranceService;
    @Autowired
    private EmissionTestSevice emissionTestSevice;
    @Autowired
    private VehicleServiceOrRepairService vehicleServiceOrRepair;
    @Autowired
    private CommonItemsOrServiesOfferedByServiceService commonItemsOrServiesOfferedByService;
    @Autowired
    private TrainerService trainerService;
    @Autowired
    private TrainerDrivingLicenceService trainerDrivingLicenceService;

    @PostMapping("/registerStudent")
    public ResponseEntity registerStudent(@RequestBody Student studentDTO){

        System.out.println("request received");
        ResponseDTO responseDTO = adminService.saveStudent(studentDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @GetMapping("/getStudent")
    public ResponseEntity getAllStudentSize(){
        ResponseDTO responseDTO = adminService.getAllStudentSize();
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @GetMapping("/getStudent/{field}/{order}")
    public ResponseEntity getStudentWithOrder(@PathVariable String field,@PathVariable String order){
        ResponseDTO responseDTO = adminService.getStudentBySorting(field,order);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }

    @GetMapping("/getStudent/{field}/{order}/{pageSize}/{offset}")
    public ResponseEntity getStudentWithOrderAndPagination(@PathVariable String field,@PathVariable String order,@PathVariable int pageSize,@PathVariable int offset){
        ResponseDTO responseDTO = adminService.getStudentBySortingAndPagination(field,order,pageSize,offset);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @GetMapping("/getStudentBasic/{detail}")
    public ResponseEntity getStudentByDetail(@PathVariable String detail){
        System.out.println(detail);
        ResponseDTO responseDTO = adminService.getStudentByDetail(detail);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }

    @GetMapping("/getStudentByID/{stdID}")
    public ResponseEntity getStudentByID(@PathVariable String stdID){
        ResponseDTO responseDTO = adminService.getStudentByID(stdID);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/SaveTrialPermit")
    public ResponseEntity saveTrialPermit(@RequestBody TrailPermitDTO TrialPermitDTO){
        System.out.println("Hi..................."+TrialPermitDTO);
        ResponseDTO responseDTO = trialPermitService.SaveTrailPermit(TrialPermitDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @GetMapping("/getTrialPermit/{stdID}")
    public ResponseEntity getTrialPermit(@PathVariable String stdID){
        ResponseDTO responseDTO = trialPermitService.getTrialPermit(stdID);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/saveVehicleType")
    public ResponseEntity saveVehicleType(@RequestBody VehicleType vehicleType){
        System.out.print(vehicleType);
        ResponseDTO responseDTO = this.vehicleType.saveVehicleType(vehicleType);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @GetMapping("/getVehicleType")
    public ResponseEntity getVehicleAllType(){
        System.out.println("getVehicleType");
        ResponseDTO responseDTO = vehicleType.getVehicleType();
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/saveTrailPermitVehicle")
    public ResponseEntity savePermit(@RequestBody List<PermitAndVehicleTypeDTO> permitAndVehicleTypeDTO){
        System.out.println("+++++++++++++");
        ResponseDTO responseDTO = trialPermitService.saveTrailPermit(permitAndVehicleTypeDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/saveTrailPermitEfficient")
    public ResponseEntity SaveTP(@RequestBody TrialPermit1DTO trialPermit1DTO){
        System.out.println(trialPermit1DTO);
        ResponseDTO responseDTO= trialPermitService.saveTrailPermitEfficinetWay(trialPermit1DTO);
        return new ResponseEntity(responseDTO ,responseDTO.getStatus());
    }
    @PostMapping("/UpdateStudentData")
    public ResponseEntity UpdateStudentData(@RequestBody Student student){
        System.out.println(student);
        ResponseDTO responseDTO = adminService.updateStudent(student);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/UpdateTrialPermit")
    public ResponseEntity UpdateTrialPermit(@RequestBody TrialPermit1DTO trialPermitDTO){
        ResponseDTO responseDTO = trialPermitService.updateTrialPermit(trialPermitDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/UpdateVehicleType")
    public ResponseEntity UpdateVehicleType(@RequestBody VehicleType vehicleType){
        ResponseDTO responseDTO = this.vehicleType.updateVehicleType(vehicleType);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/saveMedicalReport")
    public ResponseEntity saveMedicalReport(@RequestBody MedicalDTO medicalReportDTO){
        ResponseDTO responseDTO = medicalReportService.saveMedicalReport(medicalReportDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @GetMapping("/checkMedicalExpired/{stdID}")
    public ResponseEntity checkMedicalExpired(@PathVariable String stdID){
        ResponseDTO responseDTO = medicalReportService.checkReportExpiration(stdID);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @GetMapping("/checkTrialPermitExpired/{stdID}")
    public ResponseEntity checkTrialPermitExpired(@PathVariable String stdID){
        ResponseDTO responseDTO = trialPermitService.checkTrialPermitExpired(stdID);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @GetMapping("/getMedicalReport/{stdID}")
    public ResponseEntity getMedicalReport(@PathVariable String stdID){
        ResponseDTO responseDTO = medicalReportService.getMedicalReport(stdID);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/updateMedicalReport")
    public ResponseEntity updateMedicalReport(@RequestBody MedicalDTO medicalReportDTO){
        ResponseDTO responseDTO = medicalReportService.updateMedicalReport(medicalReportDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/AddPackage")
    public ResponseEntity AddPackage(@RequestBody PackageDTO packageDTO){
        System.out.println(packageDTO);
        ResponseDTO responseDTO = packageService.savePackage(packageDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @GetMapping("/getAllPackage/{field}/{order}/{pageSize}/{offset}")
    public ResponseEntity getAllPackage(@PathVariable String field,@PathVariable String order,@PathVariable int pageSize,@PathVariable int offset){
        ResponseDTO responseDTO = packageService.getAllPackage(field,order,pageSize,offset);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/updatePackage")
    public ResponseEntity updatePackage(@RequestBody PackageDTO packageDTO){
        System.out.println(packageDTO);
        ResponseDTO responseDTO = packageService.updatePackage(packageDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @GetMapping("/getPackage/{letter}")
    public ResponseEntity getPackageByLetter(@PathVariable String letter){
        System.out.println(letter);
        ResponseDTO responseDTO = packageService.getPackageByLetter(letter);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @GetMapping("/getPackageByID/{packageID}")
    public ResponseEntity getPackageByID(@PathVariable String packageID){
        ResponseDTO responseDTO = packageService.getPackageByID(packageID);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/saveAgreement")
    public ResponseEntity saveAgreement(@RequestBody AgreementDTO agreementDTO){
        ResponseDTO responseDTO = agreementService.saveAgreement(agreementDTO);
        return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
    }
    @GetMapping("/checkCurrentAgreement/{stdID}")
    public ResponseEntity checkCurrentAgreement(@PathVariable String stdID){
        ResponseDTO responseDTO = agreementService.checkCurrentAgreement(stdID);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @GetMapping("/getAgreement/{stdID}")
    public ResponseEntity getAgreement(@PathVariable String stdID){
        ResponseDTO responseDTO = agreementService.getAgreement(stdID);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @GetMapping("/getPackagesForStudent/{stdID}/{order}/{pageSize}/{offset}")
    public ResponseEntity getAgreementsForStudent(@PathVariable String stdID,@PathVariable String order,@PathVariable int pageSize,@PathVariable int offset){
        ResponseDTO responseDTO = packageService.getPackagesForStudent(stdID,order,pageSize,offset);
        return new ResponseEntity(responseDTO,HttpStatus.ACCEPTED);
    }
    @PutMapping("/updateAgreementDiscount")
    public ResponseEntity updateAgreementDiscount(@RequestBody AgreementDTO agreementDTO){
        System.out.println(agreementDTO);
        ResponseDTO responseDTO = agreementService.updateAgreementDiscount(agreementDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PutMapping("/updateAgreementAndVehicle")
    public ResponseEntity updateAgreementAndVehicle(@RequestBody PermitAndVehicleTypeDTO permitAndVehicleTypeDTO){
        ResponseDTO responseDTO = agreementService.updateAgreementAndVehicle(permitAndVehicleTypeDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());

    }
    @DeleteMapping("/deleteAgreement/{stdID}/{packageID}")
    public ResponseEntity deleteAgreement(@PathVariable String stdID,@PathVariable String packageID){
        ResponseDTO responseDTO = agreementService.deleteAgreement(stdID,packageID);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PutMapping("/UpdateExtraSession")
    public ResponseEntity saveExtraSession(@RequestBody List<ExtraSessionDTO> extraSessionDTO){
        System.out.println(extraSessionDTO);
        ResponseDTO responseDTO = extraSessionService.UpdateExtraSession(extraSessionDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PutMapping("/UpdateTotalAmountToPay")
    public ResponseEntity updateTotalAmountToPay(@RequestBody AgreementDTO agreementDTO){
        System.out.println(agreementDTO.getTotalAmountToPay());
        ResponseDTO responseDTO = agreementService.upDateTotalAmountToPay(agreementDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/savePayment")
    public ResponseEntity savePayment(@RequestBody PaymentsDTO paymentsDTO){
        System.out.println(paymentsDTO);
        ResponseDTO responseDTO = paymentService.savePayments(paymentsDTO);
        return new ResponseEntity(responseDTO,HttpStatus.ACCEPTED);

    }
    @GetMapping("/getPayments/{stdID}/{packageID}")
    public ResponseEntity getPayments(@PathVariable String stdID,@PathVariable String packageID){
        ResponseDTO responseDTO = paymentService.getPayments(stdID,packageID);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @GetMapping("/getVehicleTypesForExtras/{stdID}")
    public ResponseEntity getExtraSession(@PathVariable String stdID){
        ResponseDTO responseDTO = extraSessionService.getVehicleTypesForExtraSession(stdID);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/saveExtraSession")
    public ResponseEntity addExtraSessions(@RequestBody List<ExtraSessionDTO> extraSessionDTO){
        System.out.println(extraSessionDTO);
        ResponseDTO responseDTO = extraSessionNotInAgreementService.saveExtraSession(extraSessionDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PutMapping("/updateExtraSessionNotInAgreement")
    public ResponseEntity updateExtraSessionNotInAgreement(@RequestBody List<ExtraSessionDTO> extraSessionDTO){
        System.out.println(extraSessionDTO);
        ResponseDTO responseDTO = extraSessionNotInAgreementService.updateExtraSession(extraSessionDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @GetMapping("/getExtraSessionNotINAgreement/{stdID}/{packageID}")
    public ResponseEntity getExtraSessionNotInAgreement(@PathVariable String stdID,@PathVariable String packageID){
        ResponseDTO responseDTO = extraSessionNotInAgreementService.getExtraSessionNotInAgreement(stdID,packageID);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/saveVehicle")
    public ResponseEntity saveVehicle(@RequestBody VehicleDTO vehicleDTO){
        System.out.println(vehicleDTO);
        ResponseDTO responseDTO = vechicleService.saveVehicle(vehicleDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/saveFuelType")
    public ResponseEntity saveFuelType(@RequestBody FuelTypeDTO fuelTypeDTO){
        ResponseDTO responseDTO = fuelTypeService.saveFuelType(fuelTypeDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @GetMapping("/getFuelTypes")
    public ResponseEntity getFuelTypes(){
        ResponseDTO responseDTO = fuelTypeService.getFuelTypes();
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @GetMapping("/getVehicles/{field}/{order}/{pageSize}/{offset}")
    public ResponseEntity getVehicles(@PathVariable String field,@PathVariable String order,@PathVariable int pageSize,@PathVariable int offset){
        ResponseDTO responseDTO = vechicleService.getVehicles(field,order,pageSize,offset);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PutMapping("/updateVehicleBook")
    public ResponseEntity updateVehicle(@RequestBody VehicleDTO vehicleDTO){
        ResponseDTO responseDTO = vechicleService.updateUrl(vehicleDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/saveVehicleLicense")
    public ResponseEntity saveVehicleLicense(@RequestBody VehicleLicenceDTO vehicleLicenseDTO){
        ResponseDTO responseDTO = vehicleLicenceService.saveVehicleLicense(vehicleLicenseDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/saveNewVehicleInsuranceType")
    public ResponseEntity saveNewVehicleInsuranceType(@RequestBody InsuranceTypeDTO insuranceTypeDTO){
        ResponseDTO responseDTO = insuranceTypeService.saveInsuranceType(insuranceTypeDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @GetMapping("/getInsuranceTypes")
    public ResponseEntity getInsuranceTypes(){
        ResponseDTO responseDTO = insuranceTypeService.getInsuranceTypes();
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/saveInsurance")
    public ResponseEntity saveInsurance(@RequestBody InsuranceDTO insuranceDTO){
        System.out.println(insuranceDTO);
        ResponseDTO responseDTO = insuranceService.saveInsurance(insuranceDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/saveEmissionTest")
    public ResponseEntity saveEmissionTest(@RequestBody EmissionTestDTO emissionTestDTO){
        ResponseDTO responseDTO = emissionTestSevice.saveEmissionTest(emissionTestDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/saveVehicleServiceOrRepair")
    public ResponseEntity saveVehicleServiceOrRepair(@RequestBody VehicleServiceORRepairDTO vehicleServiceORRepairDTO){
        ResponseDTO responseDTO = vehicleServiceOrRepair.saveVehicleServiceOrRepair(vehicleServiceORRepairDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/commonItemsOrServiceOfferdByService")
    public ResponseEntity saveCommonItemsOfferedByService(@RequestBody CommonItemsOrServiesOfferedByServiceDTO commonItemsOrServiesOfferedByServiceDTO){
        ResponseDTO responseDTO = commonItemsOrServiesOfferedByService.saveCommonItemsOrServiesOfferedByService(commonItemsOrServiesOfferedByServiceDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @GetMapping("/getCommonItemsOrServiceOfferdByService")
    public ResponseEntity getCommonItemsOfferedByService(){
        ResponseDTO responseDTO = commonItemsOrServiesOfferedByService.getCommonItemsOrServiesOfferedByService();
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PutMapping("/updateCommonItemsOrServiceOfferdByServiceEnd")
    public ResponseEntity updateCommonItemsOrServiceOfferdByServiceEnd(@RequestBody VehicleServiceORRepairDTO vehicleServiceORRepairDTO){
        ResponseDTO responseDTO = vehicleServiceOrRepair.updateVehicleServiceOrRepairEnd(vehicleServiceORRepairDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/saveTrainer")
    public ResponseEntity saveTrainer(@RequestBody TrainerDTO trainerDTO){
        System.out.println(trainerDTO);
        ResponseDTO responseDTO = trainerService.saveTrainer(trainerDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @GetMapping("/getAllTrainers/{field}/{order}/{pageSize}/{offset}")
    public ResponseEntity getAllTrainers(@PathVariable String field,@PathVariable String order,@PathVariable int pageSize,@PathVariable int offset){
        ResponseDTO responseDTO = trainerService.getAllTrainers(field,order,pageSize,offset);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }
    @PostMapping("/saveTrainerDrivingLicence")
    public ResponseEntity saveTrainerDrivingLicence(@RequestBody TrainerDrivingLicenceDTO trainerDrivingLicenceDTO){
        System.out.println(trainerDrivingLicenceDTO);
        ResponseDTO responseDTO = trainerDrivingLicenceService.saveTrainerDrivingLicence(trainerDrivingLicenceDTO);
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }


}
