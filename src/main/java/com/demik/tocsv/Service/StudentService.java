package com.demik.tocsv.Service;

import com.demik.tocsv.Model.Student;

import java.util.List;

public interface StudentService {
    public List<Student> getStudents();
    public void saveStudent(Student student);
    public void deleteStudent(Integer id);
    public Student getStudentById(Integer id);
}
