package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.ItemORDoneDTO;
import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.DTO.VehicleServiceORRepairDTO;
import com.example.SecuritywithLeaners.Entity.CommonItemsOrServiesOfferedByService;
import com.example.SecuritywithLeaners.Entity.ItemsORDone;
import com.example.SecuritywithLeaners.Entity.Vehicle;
import com.example.SecuritywithLeaners.Entity.VehicleServicesAndRepair;
import com.example.SecuritywithLeaners.Repo.ServiceOrRepairRepo;
import com.example.SecuritywithLeaners.Repo.VehicleRepo;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class VehicleServiceOrRepairService {
    @Autowired
    private ServiceOrRepairRepo serviceOrRepairRepo;
    @Autowired
    private VehicleRepo vehicleRepo;
    @Autowired
    private ModelMapper modelMapper;
    public ResponseDTO saveVehicleServiceOrRepair(VehicleServiceORRepairDTO vehicleServiceORRepairDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try{
                VehicleServicesAndRepair vehicleServicesAndRepair = modelMapper.map(vehicleServiceORRepairDTO, VehicleServicesAndRepair.class);
                serviceOrRepairRepo.save(vehicleServicesAndRepair);
                vehicleRepo.updateStatus(vehicleServiceORRepairDTO.getRegistrationNo(),"Service/Repair");
                 responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setMessage("Success");
                responseDTO.setStatus(HttpStatus.ACCEPTED);


    }catch (Exception e){
        responseDTO.setCode(varList.RSP_FAIL);
        responseDTO.setMessage("Failed");
        responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        responseDTO.setContent(null);
        }
        return responseDTO;
    }
    public ResponseDTO updateVehicleServiceOrRepairEnd(VehicleServiceORRepairDTO vehicleServiceORRepairDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        double totalAmount = 0;
        try {
            Vehicle vehicle = new Vehicle();
            vehicle.setRegistrationNo(vehicleServiceORRepairDTO.getRegistrationNo());

            VehicleServicesAndRepair vehicleServicesAndRepair = new VehicleServicesAndRepair();
            vehicleServicesAndRepair.setRepairCenter(vehicleServiceORRepairDTO.getRepairCenter());
            vehicleServicesAndRepair.setServicedDate(vehicleServiceORRepairDTO.getServicedDate());
            vehicleServicesAndRepair.setId(vehicleServiceORRepairDTO.getId());
            vehicleServicesAndRepair.setContactNumber(vehicleServiceORRepairDTO.getContactNumber());
            vehicleServicesAndRepair.setInvoiceUrl(vehicleServiceORRepairDTO.getInvoiceUrl());
            vehicleServicesAndRepair.setInvoiceNo(vehicleServiceORRepairDTO.getInvoiceNo());
            vehicleServicesAndRepair.setMilage(vehicleServiceORRepairDTO.getMilage());
            vehicleServicesAndRepair.setReturnTime(vehicleServiceORRepairDTO.getReturnTime());
            vehicleServicesAndRepair.setVehicle(vehicle);
            vehicleServicesAndRepair.setServicedDate(vehicleServiceORRepairDTO.getServicedDate());
            vehicleServicesAndRepair.setReturnDate(vehicleServiceORRepairDTO.getReturnDate());
            vehicleServicesAndRepair.setServicedTime(vehicleServiceORRepairDTO.getServicedTime());
            List<ItemsORDone> itemsORDones = new ArrayList<>();
            for (ItemORDoneDTO itemORDoneDTO : vehicleServiceORRepairDTO.getItemsORDones()) {
                ItemsORDone itemsORDone = new ItemsORDone();
                VehicleServicesAndRepair vehicleServicesAndRepair1 = new VehicleServicesAndRepair();
                totalAmount += itemORDoneDTO.getTotalAmount();
                vehicleServicesAndRepair1.setId(vehicleServiceORRepairDTO.getId());

                CommonItemsOrServiesOfferedByService commonItemsOrServiesOfferedByService = new CommonItemsOrServiesOfferedByService();
                commonItemsOrServiesOfferedByService.setItemID(itemORDoneDTO.getItemID());
                itemsORDone.setItemID(commonItemsOrServiesOfferedByService);
                itemsORDone.setTotalAmount(itemORDoneDTO.getTotalAmount());
                itemsORDone.setVehicleServicesAndRepair(vehicleServicesAndRepair1);
                itemsORDones.add(itemsORDone);
            }
            vehicleServicesAndRepair.setItemsORDones(itemsORDones);
            vehicleServicesAndRepair.setTotalAmountForService(totalAmount);
            serviceOrRepairRepo.saveAndFlush(vehicleServicesAndRepair);
            vehicleRepo.updateStatus(vehicleServiceORRepairDTO.getRegistrationNo(),"Available");

            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setMessage("Success");
            responseDTO.setStatus(HttpStatus.ACCEPTED);

        }catch (Exception e){
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setMessage("Failed");
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            responseDTO.setContent(null);
        }
        return responseDTO;

    }

}
