package com.demik.tocsv.Service;

import com.demik.tocsv.Model.Student;
import com.demik.tocsv.Repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceImpl implements StudentService{

    private StudentRepo studentRepo;

    @Autowired
    public ServiceImpl(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public List<Student> getStudents() {
        return studentRepo.findAll();
    }

    @Override
    public void saveStudent(Student student) {

        studentRepo.save(student);
    }

    @Override
    public void deleteStudent(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        Optional<Student> student = studentRepo.findById(id);
        if (student.isEmpty()) {
            throw new IllegalArgumentException("Student does not exist in database");
        }
        System.out.println("Deleting student with ID: " + id);  // Debug log
        studentRepo.deleteById(id);

        // Reset auto-increment value after deletion
        resetAutoIncrement();
    }

    // Method to reset AUTO_INCREMENT value
    private void resetAutoIncrement() {
        studentRepo.resetAutoIncrement(); // Assuming resetAutoIncrement is a method in your repo
    }



    @Override
    public Student getStudentById(Integer id) {
        return null;
    }
}
