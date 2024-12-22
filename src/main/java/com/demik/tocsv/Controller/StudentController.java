package com.demik.tocsv.Controller;

import com.demik.tocsv.Model.Student;
import com.demik.tocsv.Service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public String getStudents(Model theModel){

        List<Student> theStudents = studentService.getStudents();

        theModel.addAttribute("students", theStudents);

        return "student-list";
    }

    @GetMapping("/addStudent")
    public String addStudent(Model theModel){

        Student theStudent = new Student();

        theModel.addAttribute("student", theStudent);

        return "student-add";
    }

    @PostMapping("/saveStudent")
    public String processForm(
            @Valid
            @ModelAttribute("student") Student theStudent,
            BindingResult theBindingResult
    ) {


        System.out.println("Last name:|" + theStudent.getLastName() + "|");

        System.out.println("Binding results: " + theBindingResult.toString());

        System.out.println("\n\n\n\n");

        if (theBindingResult.hasErrors()) {
            return "student-add";
        }
        else {
            try {
                theStudent.setEmail(theStudent.generateEmail());
            }
            catch (Exception e) {
                theBindingResult.rejectValue("email", "email.invalid", e.getMessage());
            }
            studentService.saveStudent(theStudent);
            return "redirect:/student/students";
        }
    }

    @DeleteMapping("/deleteStudent")
    public ResponseEntity<String> deleteStudent(@RequestParam("studentId") Integer theId) {
        try {
            studentService.deleteStudent(theId);
            return ResponseEntity.ok("Student deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

//    @DeleteMapping("/employees/{employeeId}")
//    public String deleteEmployee(@PathVariable int employeeId) {
//
//        Employee tempEmployee = employeeService.findById(employeeId);
//
//        if (tempEmployee == null) {
//            throw new RuntimeException("Could not find employee " + employeeId);
//        }
//
//        employeeService.deleteById(employeeId);
//
//        return "Deleted employee id - " + employeeId ;
//    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(Student.class, stringTrimmerEditor);
    }

}