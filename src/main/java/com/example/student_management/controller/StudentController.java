package com.example.student_management.controller;

import com.example.student_management.dto.StudentRequestDTO;
import com.example.student_management.dto.StudentResponseDTO;
import com.example.student_management.entity.Student;
import com.example.student_management.mapper.StudentMapper;
import com.example.student_management.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Student API", description = "CRUD operations for Student Management")
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    // ================= POST =================
    @Operation(summary = "Create a new student")
    @PostMapping
    public StudentResponseDTO createStudent(@RequestBody StudentRequestDTO dto) {
        Student student = StudentMapper.toEntity(dto);
        Student saved = repository.save(student);
        return StudentMapper.toDTO(saved);
    }

    @Operation(summary = "Create multiple students")
    @PostMapping("/bulk")
    public List<StudentResponseDTO> createStudentsBulk(
            @RequestBody List<StudentRequestDTO> studentsDto) {

        List<Student> students = studentsDto.stream()
                .map(StudentMapper::toEntity)
                .toList();

        List<Student> savedStudents = repository.saveAll(students);

        return savedStudents.stream()
                .map(StudentMapper::toDTO)
                .toList();
    }

    // ================= GET ALL =================
    @Operation(summary = "Get all students")
    @GetMapping
    public List<StudentResponseDTO> getAllStudents() {
        return repository.findAll()
                .stream()
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());
    }


    // ================= GET BY ID =================
    @Operation(summary = "Get student by ID")
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(student -> ResponseEntity.ok(StudentMapper.toDTO(student)))
                .orElse(ResponseEntity.notFound().build());
    }


    // ================= PUT =================
    @Operation(summary = "Update student by ID")
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(
            @PathVariable Long id,
            @RequestBody StudentRequestDTO dto) {

        return repository.findById(id)
                .map(existing -> {
                    existing.setName(dto.getName());
                    existing.setAge(dto.getAge());
                    existing.setMarks(
                            StudentMapper.toEntity(dto).getMarks()
                    );
                    Student saved = repository.save(existing);
                    return ResponseEntity.ok(StudentMapper.toDTO(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    // ================= DELETE =================
    @Operation(summary = "Delete student by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
