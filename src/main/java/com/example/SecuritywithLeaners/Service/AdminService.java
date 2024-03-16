package com.example.SecuritywithLeaners.Service;

import com.example.SecuritywithLeaners.DTO.ResponseDTO;
import com.example.SecuritywithLeaners.DTO.StudentBasicDTO;
import com.example.SecuritywithLeaners.DTO.StudentDTO;
import com.example.SecuritywithLeaners.Entity.Student;
import com.example.SecuritywithLeaners.Repo.StudentRepo;
import com.example.SecuritywithLeaners.Util.CalculateAge;
import com.example.SecuritywithLeaners.Util.IDgenerator;
import com.example.SecuritywithLeaners.Util.varList;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@Transactional
public class AdminService {
    @Autowired
    IDgenerator iDgenerator;
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CalculateAge calculateAge;

    private StudentDTO studentDTO;
    public ResponseDTO saveStudent(Student studentDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        StudentBasicDTO studentBasicDTO = new StudentBasicDTO();

        try {
            Student existingStudent = studentRepo.Available(studentDTO.getEmail(), studentDTO.getTelephone(), studentDTO.getNic());
            if (existingStudent != null) {
                this.studentDTO = modelMapper.map(existingStudent, StudentDTO.class);
                studentBasicDTO.setFname(existingStudent.getFname());
                studentBasicDTO.setLname(existingStudent.getLname());
                studentBasicDTO.setNic(existingStudent.getNic());
                studentBasicDTO.setStdID(existingStudent.getStdID());
                studentBasicDTO.setTelephone(existingStudent.getTelephone());
                responseDTO.setCode(varList.RSP_DUPLICATED);
                responseDTO.setMessage("Registered");
                responseDTO.setStatus(HttpStatus.ACCEPTED);
                responseDTO.setContent(studentBasicDTO);
            } else {
                studentDTO.setStdID(iDgenerator.getMaxStudentID());
                System.out.println(studentDTO);
                studentRepo.save(modelMapper.map(studentDTO, Student.class));
                System.out.println("Student saved");
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setMessage("Success");
                studentBasicDTO.setNic(studentDTO.getNic());
                studentBasicDTO.setStdID(studentDTO.getStdID());
                studentBasicDTO.setFname(studentDTO.getFname());
                studentBasicDTO.setLname(studentDTO.getLname());
                studentBasicDTO.setTelephone(studentDTO.getTelephone());
                responseDTO.setStatus(HttpStatus.ACCEPTED);
                responseDTO.setContent(studentBasicDTO);

            }
        } catch (Exception e) {
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setMessage("An error occurred: " + e.getMessage());
            responseDTO.setContent(null);
        }
        return responseDTO;
    }
    public ResponseDTO getAllStudentSize() {
        ResponseDTO responseDTO = new ResponseDTO();
        StudentBasicDTO studentBasicDTO = new StudentBasicDTO();
        try {
            List<Student> studentList = studentRepo.findAll();
            responseDTO.setRecordCount(studentList.size());
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setMessage("Success");
            responseDTO.setStatus(HttpStatus.ACCEPTED);
            responseDTO.setContent(studentList);
        } catch (Exception e) {
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
            responseDTO.setMessage("An error occurred: " + e.getMessage());
            responseDTO.setContent(null);
        }
        return responseDTO;
    }
    public ResponseDTO getStudentBySorting(String field, String order) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {

            log.info("Start getStudentBySorting method");
            List<Student> studentList = studentRepo.findAll(Sort.by(Sort.Direction.valueOf(order), field));
            List<StudentDTO> studentDTOList = studentList.stream().map(student -> modelMapper.map(student, StudentDTO.class)).toList();
            //log.info("Student list: {}", studentDTOList);
            responseDTO.setRecordCount(studentList.size());
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setMessage("Success");
            responseDTO.setStatus(HttpStatus.ACCEPTED);
            responseDTO.setContent(studentDTOList);


            log.info("End getStudentBySorting method");
        } catch (Exception e) {
            log.error("Error in getStudentBySorting method: {}", e.getMessage(), e);
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setMessage("An error occurred: " + e.getMessage());
            responseDTO.setContent(null);
        }
        return responseDTO;
    }

    public ResponseDTO getStudentBySortingAndPagination(String field, String order, int pageSize, int offset) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            Page<Student> studentDataPage = studentRepo.findAll(PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.valueOf(order), field)));
            List<StudentDTO> studentDTOList = studentDataPage.stream().map(student -> {//use Stream to convert the list of student to list of studentDTO
                StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);
                int age = calculateAge.CalculateAgeINT(student.getDateOfBirth().toString());
                studentDTO.setAge(age);
                return studentDTO;
            }).toList();
            log.info("Student data page: {}", studentDataPage);
            responseDTO.setContent(studentDTOList);
            responseDTO.setRecordCount(studentDataPage.getSize());
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setMessage("Success");
            responseDTO.setStatus(HttpStatus.ACCEPTED);
        }

        catch (Exception e) {
            log.error("Error in getStudentBySortingAndPagination method: {}", e.getMessage(), e);
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setMessage("An error occurred: " + e.getMessage());
            responseDTO.setContent(null);
        }

        return responseDTO;
    }
    public ResponseDTO getStudentByDetail(String detail) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<Student> studentList = studentRepo.findByDetail(detail);
            List<StudentBasicDTO> studentBasicDTOList = studentList.stream().map(student -> modelMapper.map(student, StudentBasicDTO.class)).toList();
            responseDTO.setRecordCount(studentList.size());
            responseDTO.setCode(varList.RSP_SUCCES);
            responseDTO.setMessage("Success");
            responseDTO.setStatus(HttpStatus.ACCEPTED);
            responseDTO.setContent(studentBasicDTOList);
        } catch (Exception e) {
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setMessage("An error occurred: " + e.getMessage());
            responseDTO.setContent(null);
        }
        return responseDTO;
    }

    public ResponseDTO getStudentByID(String stdID) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Optional<Student> student = studentRepo.findById(stdID);
            if (student.isPresent()) {
                StudentDTO studentDTO = modelMapper.map(student.get(), StudentDTO.class);
                int age = calculateAge.CalculateAgeINT(student.get().getDateOfBirth().toString());
                studentDTO.setAge(age);
                System.out.println(age);
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setMessage("Success");
                responseDTO.setStatus(HttpStatus.ACCEPTED);
                responseDTO.setContent(studentDTO);
            } else {
                responseDTO.setCode(varList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Student not found");
                responseDTO.setStatus(HttpStatus.NOT_FOUND);
                responseDTO.setContent(null);
            }
        } catch (Exception e) {
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setMessage("An error occurred: " + e.getMessage());
            responseDTO.setContent(null);
        }
        return responseDTO;
    }




}
