package com.springboot.export_data.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.lowagie.text.DocumentException;
import com.springboot.export_data.entity.Student;
import com.springboot.export_data.service.StudentService;
import com.springboot.export_data.util.ExcelGenerator;
import com.springboot.export_data.util.PdfGenerator;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class StudentController {
	@Autowired
	private StudentService studentService;

	@GetMapping("/excel")
	public void exportIntoExcelFile(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=student" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<Student> listOfStudents = studentService.getTheListStudent();
		ExcelGenerator generator = new ExcelGenerator(listOfStudents);
		generator.generateExcelFile(response);
	}

	// added to export as PDF

	@GetMapping("/excel/PDF")
	public void generatePdfFile(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
		String currentDateTime = dateFormat.format(new Date());
		String headerkey = "Content-Disposition";
		String headervalue = "attachment; filename=student" + currentDateTime + ".pdf";
		response.setHeader(headerkey, headervalue);
		List<Student> listofStudents = studentService.getTheListStudent();
		PdfGenerator generator = new PdfGenerator();
		generator.generate(listofStudents, response);
	}
}
