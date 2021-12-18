package com.example.studentsapp.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM Student")
    List<Student> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Student... students);

    @Delete
    void deleteStudent(Student student);

    @Query("SELECT * FROM Student WHERE id =:id ")
    Student getStudentById(String id);

    @Update(entity = Student.class)
    void updateStudent(Student student);

    @Query("UPDATE Student SET id =:updated_id WHERE id =:current_id")
    void updateId(String current_id, String updated_id);

}
