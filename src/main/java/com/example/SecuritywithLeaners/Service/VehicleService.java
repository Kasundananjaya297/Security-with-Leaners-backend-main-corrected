package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.*;
import com.example.SecuritywithLeaners.Entity.EmissionTest;
import com.example.SecuritywithLeaners.Entity.Insurance;
import com.example.SecuritywithLeaners.Entity.Vehicle;
import com.example.SecuritywithLeaners.Entity.VehicleLicense;
import com.example.SecuritywithLeaners.Repo.VehicleRepo;
import com.example.SecuritywithLeaners.Util.CalculateAge;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class VehicleService {
    @Autowired
    VehicleRepo vehicleRepo;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CalculateAge calculateAge;
    public ResponseDTO saveVehicle(VehicleDTO vehicleDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            if(!vehicleRepo.existsById(vehicleDTO.getRegistrationNo())){
                Vehicle vehicle = modelMapper.map(vehicleDTO, Vehicle.class);
                vehicleRepo.save(vehicle);
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setMessage("Success");
                responseDTO.setStatus(HttpStatus.ACCEPTED);
                //System.out.println("Vehicle saved");
            }
            else {
                responseDTO.setCode(varList.RSP_DUPLICATED);
                responseDTO.setMessage("Vehicle Already Exists");
                responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
            }
        }catch (Exception e){
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setMessage("Failed");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        }
        return responseDTO;
    }
    public ResponseDTO getVehicles(String field,String order,int pageSize,int offset){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Page<Vehicle> vehicles = vehicleRepo.findAll(PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.valueOf(order), field)));
            List<VehicleDTO> vehicleDTOS = new ArrayList<>();
            for(Vehicle vehicle:vehicles){
                VehicleDTO vehicleDTO = new VehicleDTO();
                vehicleDTO.setRegistrationNo(vehicle.getRegistrationNo());
                vehicleDTO.setMake(vehicle.getMake());
                vehicleDTO.setColor(vehicle.getColor());
                vehicleDTO.setPassengerCapacity(vehicle.getPassengerCapacity());
                vehicleDTO.setFuelType(vehicle.getFuelType().getFuelType());
                vehicleDTO.setCylinderCapacity(vehicle.getCylinderCapacity());
                vehicleDTO.setUrlOfBook(vehicle.getUrlOfBook());
                vehicleDTO.setTypeID(vehicle.getTypeID().getTypeID());
                vehicleDTO.setAutoOrManual(vehicle.getAutoOrManual());
                vehicleDTO.setVehicleClass(vehicle.getTypeID().getTypeName());
                vehicleDTO.setVehiclePhoto(vehicle.getVehiclePhoto());
                vehicleDTO.setModal(vehicle.getModal());
                vehicleDTO.setDateOfRegistration(vehicle.getDateOfRegistration());
                vehicleDTOS.add(vehicleDTO);
                List<VehicleLicenceDTO> vehicleLicenceDTOS = new ArrayList<>();
                for(VehicleLicense vehicleLicense:vehicle.getVehicleLicenses().stream().sorted(Comparator.comparing(VehicleLicense::getExpiryDate).reversed()).collect(Collectors.toList())){
                    VehicleLicenceDTO vehicleLicenceDTO = new VehicleLicenceDTO();
                    vehicleLicenceDTO.setLicenseNo(vehicleLicense.getLicenseNo());
                    vehicleLicenceDTO.setAnnualFee(vehicleLicense.getAnnualFee());
                    vehicleLicenceDTO.setArrearsFee(vehicleLicense.getArrearsFee());
                    vehicleLicenceDTO.setFinesPaid(vehicleLicense.getFinesPaid());
                    vehicleLicenceDTO.setIssuedDate(vehicleLicense.getIssuedDate());
                    vehicleLicenceDTO.setStartDate(vehicleLicense.getStartDate());
                    vehicleLicenceDTO.setExpiryDate(vehicleLicense.getExpiryDate());
                    vehicleLicenceDTO.setLicenseLink(vehicleLicense.getLicenseLink());
                    vehicleLicenceDTO.setRegistrationNo(vehicleLicense.getVehicle().getRegistrationNo());
                    int months = calculateAge.CalculateMonths(vehicleLicense.getExpiryDate().toString());
                    int days = (calculateAge.calculateDays(vehicleLicense.getExpiryDate().toString()));
                    vehicleLicenceDTO.setValidDays(Math.max(days,0));
                    vehicleLicenceDTO.setValidMonths(Math.max(months,0));
                    vehicleLicenceDTOS.add(vehicleLicenceDTO);
                }
                vehicleDTO.setLicenses(vehicleLicenceDTOS);
                List<InsuranceDTO> insuranceDTOS = new ArrayList<>();
                for(Insurance insurance:vehicle.getInsurances().stream().sorted(Comparator.comparing(Insurance::getEndDate).reversed()).collect(Collectors.toList())){
                    InsuranceDTO insuranceDTO = new InsuranceDTO();
                    insuranceDTO.setCertificateNo(insurance.getCertificateNo());
                    insuranceDTO.setStartDate(insurance.getStartDate());
                    insuranceDTO.setEndDate(insurance.getEndDate());
                    insuranceDTO.setIssuedDate(insurance.getIssuedDate());
                    insuranceDTO.setInsuranceLink(insurance.getInsuranceLink());
                    insuranceDTO.setInsuranceCompany(insurance.getInsuranceCompany());
                    insuranceDTO.setAnnualFee(insurance.getAnnualFee());
                    insuranceDTO.setInsuranceType(insurance.getInsuranceType().getType());
                    insuranceDTO.setRegistrationNo(insurance.getVehicle().getRegistrationNo());
                    int months = calculateAge.CalculateMonths(insurance.getEndDate().toString());
                    int days = (calculateAge.calculateDays(insurance.getEndDate().toString()));
                    insuranceDTO.setValidDays(Math.max(days,0));
                    insuranceDTO.setValidMonths(Math.max(months,0));
                    insuranceDTOS.add(insuranceDTO);
                }
                vehicleDTO.setInsurances(insuranceDTOS);
                List<EmissionTestDTO>  emissionTestDTOS = new ArrayList<>();
                for(EmissionTest emissionTest : vehicle.getEmissionTests().stream().sorted(Comparator.comparing(EmissionTest::getExpiryDate).reversed()).collect(Collectors.toList())){
                    EmissionTestDTO emissionTestDTO = new EmissionTestDTO();
                    emissionTestDTO.setSerialNo(emissionTest.getSerialNo());
                    emissionTestDTO.setRegistrationNo(emissionTest.getVehicle().getRegistrationNo());
                    emissionTestDTO.setIssuedDate(emissionTest.getIssuedDate());
                    emissionTestDTO.setExpiryDate(emissionTest.getExpiryDate());
                    emissionTestDTO.setUrlOfDocument(emissionTest.getUrlOfDocument());
                    emissionTestDTO.setEmissionTestFee(emissionTest.getEmissionTestFee());
                    int months = calculateAge.CalculateMonths(emissionTest.getExpiryDate().toString());
                    int days = (calculateAge.calculateDays(emissionTest.getExpiryDate().toString()));
                    emissionTestDTO.setValidDays(Math.max(days,0));
                    emissionTestDTO.setValidMonths(Math.max(months,0));
                    emissionTestDTOS.add(emissionTestDTO);
                }
                vehicleDTO.setEmissionTests(emissionTestDTOS);
            }
            responseDTO.setMessage("Vehicles Fetched Successfully");
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setStatus(HttpStatus.ACCEPTED);
            responseDTO.setContent(vehicleDTOS);

        }catch (Exception e){
            responseDTO.setMessage("Vehicles Fetch Failed");
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setContent(null);
        }
        return responseDTO;
    }
    public ResponseDTO updateUrl( VehicleDTO vehicleDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            vehicleRepo.updateUrl(vehicleDTO.getRegistrationNo(),vehicleDTO.getUrlOfBook());
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setMessage("Success");
            responseDTO.setStatus(HttpStatus.ACCEPTED);
        }catch (Exception e){
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setMessage("Failed");
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        }
        return responseDTO;
    }

}
