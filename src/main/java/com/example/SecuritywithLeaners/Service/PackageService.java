package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.PackageAndVehicleTypeDTO;
import com.example.SecuritywithLeaners.DTO.PackageDTO;
import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.Entity.Package;
import com.example.SecuritywithLeaners.Entity.PackageAndVehicleType;
import com.example.SecuritywithLeaners.Entity.PackageAndVehicleTypeID;
import com.example.SecuritywithLeaners.Entity.VehicleType;
import com.example.SecuritywithLeaners.Repo.PackageAndVehicleTypeRepo;
import com.example.SecuritywithLeaners.Repo.PackageRepo;
import com.example.SecuritywithLeaners.Repo.VehicleTypeRepo;
import com.example.SecuritywithLeaners.Util.IDgenerator;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@Transactional
public class PackageService {
@Autowired
private PackageRepo packageRepo;
@Autowired
private IDgenerator idGenerator;
@Autowired
private ModelMapper modelMapper;
@Autowired
private VehicleTypeRepo vehicleTypeRepo;
@Autowired
private PackageAndVehicleTypeRepo packageAndVehicleTypeRepo;

    public ResponseDTO savePackage(PackageDTO packageDTO){
        ResponseDTO responseDTO = new ResponseDTO();



        try {
            if(!packageRepo.existsById(packageDTO.getPackageID())){
                if(packageRepo.ExistBypackageName(packageDTO.getPackageName())>0){
                    responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
                    responseDTO.setMessage("Package Name already exists");
                    responseDTO.setCode(varList.RSP_DUPLICATED);
                    responseDTO.setContent(null);
                    return responseDTO;
                }
                String PackID = idGenerator.generatePackageID();
                Package packageData = new Package();
                PackageAndVehicleTypeID packageAndVehicleTypeID = new PackageAndVehicleTypeID();
                PackageAndVehicleType packageAndVehicleType = new PackageAndVehicleType();
                packageData.setPackageName(packageDTO.getPackageName());
                packageData.setPackagePrice(packageDTO.getPackagePrice());
                packageData.setDescription(packageDTO.getDescription());
                packageData.setPackageID(PackID);
                packageRepo.save(packageData);
                for(PackageAndVehicleTypeDTO packageAndVehicleTypeDTO : packageDTO.getPackageAndVehicleType()){
                    packageAndVehicleTypeID.setPackageID(packageRepo.findById(PackID).get());
                    packageAndVehicleTypeID.setTypeID(vehicleTypeRepo.findById(packageAndVehicleTypeDTO.getTypeID()).get());
                    packageAndVehicleType.setPackageAndVehicleTypeID(packageAndVehicleTypeID);
                    packageAndVehicleType.setLessons(packageAndVehicleTypeDTO.getLessons());
                    packageAndVehicleTypeRepo.save(packageAndVehicleType);
                }
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setStatus(HttpStatus.ACCEPTED);
                responseDTO.setMessage("Package saved successfully");

            }else{
                responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
                responseDTO.setMessage("Package ID already exists");
                responseDTO.setCode(varList.RSP_DUPLICATED);
                responseDTO.setContent(null);
            }

        }catch (Exception e){
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setMessage("Failed to save package");
            responseDTO.setContent(null);
        }
        return responseDTO;
    }

}
