package com.example.SecuritywithLeaners.Controller;


import com.example.SecuritywithLeaners.DTO.*;
import com.example.SecuritywithLeaners.Entity.*;
import com.example.SecuritywithLeaners.Entity.Package;
import com.example.SecuritywithLeaners.Entity.Views.View;
import com.example.SecuritywithLeaners.Service.*;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
//        System.out.println(trialPermit1DTO);
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
        return new ResponseEntity(responseDTO,responseDTO.getStatus());
    }

}
