package com.example.student_management.mapper;

import com.example.student_management.dto.*;
import com.example.student_management.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public class StudentMapper {

    // DTO → Entity
    public static Student toEntity(StudentRequestDTO dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setAge(dto.getAge());

        if (dto.getMarks() != null) {
            List<Mark> marks = dto.getMarks().stream()
                    .map(markDTO -> {
                        Mark mark = new Mark();
                        mark.setSubject(markDTO.getSubject());
                        mark.setScore(markDTO.getScore());
                        mark.setStudent(student);
                        return mark;
                    })
                    .collect(Collectors.toList());

            student.setMarks(marks);
        }
        return student;
    }

    // Entity → DTO
    public static StudentResponseDTO toDTO(Student student) {
        StudentResponseDTO dto = new StudentResponseDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setAge(student.getAge());

        if (student.getMarks() != null) {
            List<MarkDTO> marks = student.getMarks().stream()
                    .map(mark -> {
                        MarkDTO markDTO = new MarkDTO();
                        markDTO.setSubject(mark.getSubject());
                        markDTO.setScore(mark.getScore());
                        return markDTO;
                    })
                    .collect(Collectors.toList());

            dto.setMarks(marks);
        }
        return dto;
    }
}
