package com.example.SecuritywithLeaners;

import com.example.SecuritywithLeaners.Util.IDgenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SecurityWithLeanersApplicationTests {

	@Autowired
	IDgenerator iDgenerator;

	@Test
	void testGenerateAndPrintID() {
		// Assuming IDgenerator has a method like generateID()
		String generatedID = iDgenerator.getMaxStudentID();

		// Print the generated ID
		System.out.println("Generated ID: " + generatedID);

		// Add your assertions to verify the generated ID
		assertNotNull(generatedID);
		// Add more assertions as needed based on your ID generation logic
	}
}
