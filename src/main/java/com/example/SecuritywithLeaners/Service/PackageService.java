package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.*;
import com.example.SecuritywithLeaners.Entity.*;
import com.example.SecuritywithLeaners.Entity.Package;
import com.example.SecuritywithLeaners.Repo.*;
import com.example.SecuritywithLeaners.Util.IDgenerator;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
private TrialPermitRepo trialPermitRepo;
@Autowired
private PermitAndVehicleTypeRepo permitAndVehicleTypeRepo;
@Autowired
private PackageAndVehicleTypeRepo packageAndVehicleTypeRepo;
@Autowired
private AuthenticationService authenticationService;

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
                    packageAndVehicleType.setAutoOrManual(packageAndVehicleTypeDTO.getAutoOrManual());
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
    public ResponseDTO getAllPackage(String field,String order,int pageSize,int offset){
        ResponseDTO responseDTO = new ResponseDTO();
        PackageDTO packageDTO = new PackageDTO();
        try{
            List<Package> packages = packageRepo.findAll(PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.valueOf(order), field))).toList();
            List<PackageDTO> packageDTOList = new ArrayList<>();
            for(Package packData : packages){
                PackageDTO packageData = new PackageDTO();
                packageData.setPackageID(packData.getPackageID());
                packageData.setPackageName(packData.getPackageName());
                packageData.setDescription(packData.getDescription());
                packageData.setPackagePrice(packData.getPackagePrice());
                List<PackageAndVehicleTypeDTO> packageAndVehicleTypeDTOList =  new ArrayList<>();
                for(PackageAndVehicleType packageAndVehicleType : packData.getPackageAndVehicleType()){
                    PackageAndVehicleTypeDTO packageAndVehicleTypeDTO = new PackageAndVehicleTypeDTO();
                    packageAndVehicleTypeDTO.setPackageID(packageAndVehicleType.getPackageAndVehicleTypeID().getPackageID().getPackageID());
                    packageAndVehicleTypeDTO.setTypeID(packageAndVehicleType.getPackageAndVehicleTypeID().getTypeID().getTypeID());
                    packageAndVehicleTypeDTO.setLessons(packageAndVehicleType.getLessons());
                    packageAndVehicleTypeDTO.setAutoOrManual(packageAndVehicleType.getAutoOrManual());
                    packageAndVehicleTypeDTO.setEngineCapacity(packageAndVehicleType.getPackageAndVehicleTypeID().getTypeID().getEngineCapacity());
                    packageAndVehicleTypeDTO.setTypeName(packageAndVehicleType.getPackageAndVehicleTypeID().getTypeID().getTypeName());
                    packageAndVehicleTypeDTOList.add(packageAndVehicleTypeDTO);
                }
                packageData.setPackageAndVehicleType(packageAndVehicleTypeDTOList);
                packageDTOList.add(packageData);
            }
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setStatus(HttpStatus.ACCEPTED);
            responseDTO.setMessage("Success");
            responseDTO.setContent(packageDTOList);
        }catch (Exception e){
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            responseDTO.setMessage("Failed to get all packages");
            responseDTO.setContent(null);
        }
        return responseDTO;
    }
    public ResponseDTO updatePackage(PackageDTO packageDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            if(packageRepo.existsById(packageDTO.getPackageID())){
                if(packageRepo.ExistBypackageName(packageDTO.getPackageName())==0){
                    responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
                    responseDTO.setMessage("Package Name already not exists");
                    responseDTO.setCode(varList.RSP_DUPLICATED);
                    responseDTO.setContent(null);
                    return responseDTO;
                }
                Package packageData = new Package();
                packageData.setPackageName(packageDTO.getPackageName());
                packageData.setPackagePrice(packageDTO.getPackagePrice());
                packageData.setDescription(packageDTO.getDescription());
                packageData.setPackageID(packageDTO.getPackageID());
                packageRepo.saveAndFlush(packageData);
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setStatus(HttpStatus.ACCEPTED);
                responseDTO.setMessage("Package updated successfully");
            }else{
                responseDTO.setCode(varList.RSP_NO_DATA_FOUND);
                responseDTO.setStatus(HttpStatus.NOT_FOUND);
                responseDTO.setMessage("Package not found");
                responseDTO.setContent(null);
            }
        }catch (Exception e){
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            responseDTO.setMessage("Failed to update package");
            responseDTO.setContent(null);
        }
        return responseDTO;
    }
    public ResponseDTO getPackageByLetter(String letter){
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            List<Package> packageDetailsDTO = packageRepo.findByPackageName(letter);
            List<PackageDetailsDTO> packDetails =  packageDetailsDTO.stream().map(pack -> modelMapper.map(pack, PackageDetailsDTO.class)).toList();
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setStatus(HttpStatus.ACCEPTED);
            responseDTO.setContent(packDetails);
        }catch (Exception e){
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            responseDTO.setMessage("Failed to get package");
            responseDTO.setContent(null);
        }
        return responseDTO;
    }
    public ResponseDTO getPackageByID(String packageID){
        ResponseDTO responseDTO = new ResponseDTO();
        PackageDTO packageDTO = new PackageDTO();
        try{
            Package packageData = packageRepo.findById(packageID).get();
            packageDTO.setPackageID(packageData.getPackageID());
            packageDTO.setPackageName(packageData.getPackageName());
            packageDTO.setDescription(packageData.getDescription());
            packageDTO.setPackagePrice(packageData.getPackagePrice());
            List<PackageAndVehicleTypeDTO> packageAndVehicleTypeDTOList =  new ArrayList<>();
            for(PackageAndVehicleType packTypeAndVehicle : packageData.getPackageAndVehicleType()){
                PackageAndVehicleTypeDTO packageAndVehicleTypeDTO = new PackageAndVehicleTypeDTO();
                packageAndVehicleTypeDTO.setPackageID(packTypeAndVehicle.getPackageAndVehicleTypeID().getPackageID().getPackageID());
                packageAndVehicleTypeDTO.setTypeID(packTypeAndVehicle.getPackageAndVehicleTypeID().getTypeID().getTypeID());
                packageAndVehicleTypeDTO.setLessons(packTypeAndVehicle.getLessons());
                packageAndVehicleTypeDTO.setAutoOrManual(packTypeAndVehicle.getAutoOrManual());
                packageAndVehicleTypeDTO.setEngineCapacity(packTypeAndVehicle.getPackageAndVehicleTypeID().getTypeID().getEngineCapacity());
                packageAndVehicleTypeDTO.setTypeName(packTypeAndVehicle.getPackageAndVehicleTypeID().getTypeID().getTypeName());
                packageAndVehicleTypeDTOList.add(packageAndVehicleTypeDTO);
            }
            packageDTO.setPackageAndVehicleType(packageAndVehicleTypeDTOList);
            responseDTO.setContent(packageDTO);
            responseDTO.setStatus(HttpStatus.ACCEPTED);
        }catch (Exception e){
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            responseDTO.setMessage("Failed to get package");
            responseDTO.setContent(null);
        }
        return responseDTO;
    }
    public ResponseDTO getPackagesForStudent(String stdID,String order,int pageSize,int offset){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            String trialPermitID = trialPermitRepo.findByStdID(stdID);
            List<String> selectedTypes =permitAndVehicleTypeRepo.findSelectedTypeBySerialNo(trialPermitID);
            List<String> packIDs = packageRepo.findPackageByFilter(selectedTypes,pageSize,offset);
            System.out.println(order);
            List<Package> packages = packageRepo.findAllByIdSorted(packIDs,order);
            List<PackageDTO> packageDTOList = new ArrayList<>();
            for(Package packData : packages){
                PackageDTO packageData = new PackageDTO();
                packageData.setPackageID(packData.getPackageID());
                packageData.setPackageName(packData.getPackageName());
                packageData.setDescription(packData.getDescription());
                packageData.setPackagePrice(packData.getPackagePrice());
                List<PackageAndVehicleTypeDTO> packageAndVehicleTypeDTOList =  new ArrayList<>();
                for(PackageAndVehicleType packageAndVehicleType : packData.getPackageAndVehicleType()){
                    PackageAndVehicleTypeDTO packageAndVehicleTypeDTO = new PackageAndVehicleTypeDTO();
                    packageAndVehicleTypeDTO.setPackageID(packageAndVehicleType.getPackageAndVehicleTypeID().getPackageID().getPackageID());
                    packageAndVehicleTypeDTO.setTypeID(packageAndVehicleType.getPackageAndVehicleTypeID().getTypeID().getTypeID());
                    packageAndVehicleTypeDTO.setLessons(packageAndVehicleType.getLessons());
                    packageAndVehicleTypeDTO.setAutoOrManual(packageAndVehicleType.getAutoOrManual());
                    packageAndVehicleTypeDTO.setEngineCapacity(packageAndVehicleType.getPackageAndVehicleTypeID().getTypeID().getEngineCapacity());
                    packageAndVehicleTypeDTO.setTypeName(packageAndVehicleType.getPackageAndVehicleTypeID().getTypeID().getTypeName());
                    packageAndVehicleTypeDTOList.add(packageAndVehicleTypeDTO);
                }
                packageData.setPackageAndVehicleType(packageAndVehicleTypeDTOList);
                packageDTOList.add(packageData);
            }
            responseDTO.setContent(packageDTOList);
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setStatus(HttpStatus.ACCEPTED);
            responseDTO.setMessage("Success");
        }catch (Exception e){
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            responseDTO.setMessage("Failed to get packages");
            responseDTO.setContent(null);
        }
        return responseDTO;
    }

}
