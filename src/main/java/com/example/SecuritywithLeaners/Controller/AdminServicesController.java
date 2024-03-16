package com.example.SecuritywithLeaners.Controller;


import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.DTO.StudentDTO;
import com.example.SecuritywithLeaners.DTO.TrailPermitDTO;
import com.example.SecuritywithLeaners.Entity.Student;
import com.example.SecuritywithLeaners.Entity.TrialPermit;
import com.example.SecuritywithLeaners.Entity.VehicleType;
import com.example.SecuritywithLeaners.Service.AdminService;
import com.example.SecuritywithLeaners.Service.TrialPermitService;
import com.example.SecuritywithLeaners.Service.VehicleTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
