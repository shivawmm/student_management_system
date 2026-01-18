package com.example.student_management.controller;

import com.example.student_management.entity.Student;
import com.example.student_management.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Student createStudent(@RequestBody Student student) {
        return repository.save(student);
    }

    @Operation(summary = "Create multiple students")
    @PostMapping("/bulk")
    public List<Student> createStudents(@RequestBody List<Student> students) {
        return repository.saveAll(students);
    }


    // ================= GET ALL =================
    @Operation(summary = "Get all students")
    @GetMapping
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    // ================= GET BY ID =================
    @Operation(summary = "Get student by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ================= PUT =================
    @Operation(summary = "Update student by ID")
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student updatedStudent) {

        return repository.findById(id)
                .map(student -> {
                    student.setName(updatedStudent.getName());
                    student.setAge(updatedStudent.getAge());
                    student.setMarks(updatedStudent.getMarks());
                    return ResponseEntity.ok(repository.save(student));
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
