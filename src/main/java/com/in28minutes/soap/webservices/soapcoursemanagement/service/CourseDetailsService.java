package com.in28minutes.soap.webservices.soapcoursemanagement.service;

import com.in28minutes.soap.webservices.soapcoursemanagement.bean.Course;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class CourseDetailsService {

    private static List<Course> courses = new ArrayList<>();

    public enum Status {
        SUCCESS, FAILURE;
    }


    static {
        Course course1 = new Course(1, "Spring", "10 Steps");
        courses.add(course1);

        Course course2 = new Course(2, "Spring MVC", "10 Examples");
        courses.add(course2);

        Course course3 = new Course(3, "Spring Boot", "6K Students");
        courses.add(course3);

        Course course4 = new Course(4, "Maven", "Most popular maven course on internet!");
        courses.add(course4);
    }

    public Course findById(int id){
        Course course = courses.stream().filter(x -> x.getId() == id).findAny()
                .orElse(null);
        return course;


    }

    public List<Course> findAll(){
        return courses;
    }

    public Status deleteById(int id) {
        Iterator<Course> iterator = courses.iterator();
        while (iterator.hasNext()) {
            Course course = iterator.next();
            if (course.getId() == id) {
                iterator.remove();
                return Status.SUCCESS;
            }
        }
        return Status.FAILURE;
    }


}
