package com.example.crud.service;


import com.example.crud.model.Message;
import com.example.crud.model.Student;
import com.example.crud.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student getStudent(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student getStudentByUserName(String userName) {
        return studentRepository.findByUserName(userName);
    }


    public Message addStudent(Student student) {
        Message message = isUserNameExist(student);
        if (message.isFlag()) {

            //String publicUserID = utils.generateUserId(30);
            //userEntity.setUserId(publicUserID);

            student.setPassword(bCryptPasswordEncoder.encode(student.getPassword()));

            studentRepository.save(student);
            message.setMessage("Student added successfully");
        }
        return message;
    }

    public Message deleteStudent(long id) {
        Message message = new Message();
        if (studentRepository.findById(id).orElse(null) != null) {
            studentRepository.deleteById(id);
            message.setMessage("Student Deleted Successfully");
            message.setFlag(true);
        } else {
            message.setMessage("Student does not exist");
            message.setFlag(false);
        }
        return message;
    }

    public Student updateStudent(Student student) {
        if (isUserExist(student)) {
            return studentRepository.save(student);
        } else {
            return null;
        }
    }

   /* public Student isAuthenticated(String userName, String password) {
        Student student = null;
        Student existingStudent = studentRepository.findByUserName(userName);
        if (existingStudent != null) {
            if (existingStudent.getPassword().equals(password)) {
                student = existingStudent;
            } else {
                student = null;
            }
        }
        return student;

    }*/

    public Message isUserNameExist(Student student) {
        Student existingStudent = studentRepository.findByUserName(student.getUserName());
        Message message = new Message();
        if (existingStudent != null) {
            message.setMessage("User Name Already Exists");
            message.setFlag(false);
        } else {
            message.setMessage("User Name does Not Exist");
            message.setFlag(true);
        }
        return message;
    }

    public boolean isUserExist(Student student) {

        return studentRepository.existsById(student.getId());

    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Student student = studentRepository.findByUserName(userName);
        if (student == null) throw new UsernameNotFoundException(userName);
        return new User(student.getUserName(), student.getPassword(), new ArrayList<>());
    }
}
