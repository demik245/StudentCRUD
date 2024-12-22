package com.demik.tocsv.Repository;

import com.demik.tocsv.Model.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Student s WHERE s.id = :id")
    void deleteById(Integer id);

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE student AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}
