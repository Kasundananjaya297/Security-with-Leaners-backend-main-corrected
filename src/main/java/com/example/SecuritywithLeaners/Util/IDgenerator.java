package com.example.SecuritywithLeaners.Util;

import com.example.SecuritywithLeaners.Repo.StudentRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.Integer.parseInt;

@Transactional
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Service
public class IDgenerator {
    private String DefaultID = "CN-5000";
    @Autowired
    private StudentRepo studentRepo;



private String getSeparateStringPart(String ID){
    return ID.replaceAll("[\\d]","");

}
private String getSeperateNumbericPart(String ID){
    return ID.replaceAll("[^\\d]","");
}

public String NextUserID(String ID){
    String NewID =getSeparateStringPart(ID) + (parseInt(getSeperateNumbericPart(ID))+1);
    return NewID;
}
public String getMaxStudentID() {
        String maxEmployeeId = studentRepo.getMStdID();
        if(!(maxEmployeeId == null)){
            System.out.println(maxEmployeeId);
            return NextUserID(maxEmployeeId.toString());
        }
        else{
            return NextUserID(DefaultID);
        }
    }
}
