package com.example.crud.controller;

import com.example.crud.model.Message;
import com.example.crud.model.Student;
import com.example.crud.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("getStudents")
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("getStudent/{id}")
    public Student getStudent(@PathVariable(value = "id") long id) {
        return studentService.getStudent(id);
    }

    @PostMapping("addStudent")
    public Message addStudent(@RequestBody Student student) {
        System.out.println("student.name" + student.getFirstName());
        return studentService.addStudent(student);
    }

    @PutMapping("updateStudent")
    public Student updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("deleteStudent/{id}")
    public Message deleteStudent(@PathVariable(value = "id") long id) {
        return studentService.deleteStudent(id);
    }
}
