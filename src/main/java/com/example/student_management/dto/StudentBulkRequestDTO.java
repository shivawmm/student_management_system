package com.example.student_management.dto;

import jakarta.validation.constraints.Size;
import java.util.List;

public class StudentBulkRequestDTO {

    @Size(max = 20, message = "Maximum 20 students allowed in bulk request")
    private List<StudentRequestDTO> students;

    public List<StudentRequestDTO> getStudents() {
        return students;
    }

    public void setStudents(List<StudentRequestDTO> students) {
        this.students = students;
    }
}
