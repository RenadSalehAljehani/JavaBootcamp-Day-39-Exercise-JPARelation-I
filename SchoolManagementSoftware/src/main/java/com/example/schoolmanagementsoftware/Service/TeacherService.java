package com.example.schoolmanagementsoftware.Service;

import com.example.schoolmanagementsoftware.Api.ApiException;
import com.example.schoolmanagementsoftware.Model.Address;
import com.example.schoolmanagementsoftware.Model.Teacher;
import com.example.schoolmanagementsoftware.Repository.AddressRepository;
import com.example.schoolmanagementsoftware.Repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final AddressRepository addressRepository;

    // CRUD
    // 1.GET
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    //2.POST
    public void addTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    //3.UPDATE
    public void updateTeacher(Integer id, Teacher teacher) {
        Teacher oldTeacher = teacherRepository.findTeacherById(id);
        if (oldTeacher == null) {
            throw new ApiException("Teacher Not Found.");
        }
        oldTeacher.setName(teacher.getName());
        oldTeacher.setAge(teacher.getAge());
        oldTeacher.setEmail(teacher.getEmail());
        oldTeacher.setSalary(teacher.getSalary());
        teacherRepository.save(oldTeacher);
    }

    //4.DELETE
    public void deleteTeacher(Integer id) {
        Teacher oldTeacher = teacherRepository.findTeacherById(id);
        if (oldTeacher == null) {
            throw new ApiException("Teacher Not Found.");
        }

        Address address = addressRepository.findAddressById(oldTeacher.getId());
        if (address == null) {
            throw new ApiException("Address Not Found.");
        }
        oldTeacher.setAddress(null);
        addressRepository.delete(address);
        teacherRepository.delete(oldTeacher);
    }

    // 4.Extra Endpoint
    // An endpoint that takes teacher id and return All teacher details
    public Teacher getTeacherDetails(Integer id) {
        Address address = addressRepository.findAddressById(id);
        Teacher teacher = teacherRepository.findTeacherById(id);
        if (address == null && teacher == null) {
            throw new ApiException("Address & Teacher Not Found.");
        }
        if (address == null) {
            throw new ApiException("Address Not Found.");
        }
        if (teacher == null) {
            throw new ApiException("Teacher Not Found.");
        }
        teacher.setAddress(address);
        teacherRepository.save(teacher);
        return teacher;
    }
}