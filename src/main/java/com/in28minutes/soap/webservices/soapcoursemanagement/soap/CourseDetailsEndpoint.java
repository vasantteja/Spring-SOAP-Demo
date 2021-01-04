package com.in28minutes.soap.webservices.soapcoursemanagement.soap;

import com.in28minutes.courses.*;
import com.in28minutes.soap.webservices.soapcoursemanagement.bean.Course;
import com.in28minutes.soap.webservices.soapcoursemanagement.exception.CourseNotFoundException;
import com.in28minutes.soap.webservices.soapcoursemanagement.service.CourseDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class CourseDetailsEndpoint {


    @Autowired
    CourseDetailsService courseDetailsService;

    @PayloadRoot(namespace = "http://in28minutes.com/courses", localPart =  "GetCourseDetailsRequest")
    @ResponsePayload
    public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request){
        Course course = courseDetailsService.findById(request.getId());

        if (course == null)
            throw new CourseNotFoundException("Invalid Course Id " + request.getId());

        return mapCourseDetails(course);
    }


    private GetCourseDetailsResponse mapCourseDetails(Course course) {
        GetCourseDetailsResponse response = new GetCourseDetailsResponse();
        response.setCourseDetails(mapCourse(course));
        return response;
    }

    private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses) {
        GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
        for (Course course : courses) {
            CourseDetails mapCourse = mapCourse(course);
            response.getCourseDetails().add(mapCourse);
        }
        return response;
    }

    private CourseDetails mapCourse(Course course) {
        CourseDetails courseDetails = new CourseDetails();

        courseDetails.setId(course.getId());

        courseDetails.setName(course.getName());

        courseDetails.setDescription(course.getDescription());
        return courseDetails;
    }

    @PayloadRoot(namespace = "http://in28minutes.com/courses", localPart = "GetAllCourseDetailsRequest")
    @ResponsePayload
    public GetAllCourseDetailsResponse processAllCourseDetailsRequest(
            @RequestPayload GetAllCourseDetailsRequest request) {

        List<Course> courses = courseDetailsService.findAll();

        return mapAllCourseDetails(courses);
    }
    @PayloadRoot(namespace = "http://in28minutes.com/courses", localPart = "DeleteCourseDetailsRequest")
    @ResponsePayload
    public DeleteCourseDetailsResponse deleteCourseDetailsRequest(@RequestPayload DeleteCourseDetailsRequest request) {

        CourseDetailsService.Status status = courseDetailsService.deleteById(request.getId());

        DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
        response.setStatus(mapStatus(status));

        return response;
    }

    private com.in28minutes.courses.Status mapStatus(CourseDetailsService.Status status) {
        if (status.equals(com.in28minutes.courses.Status.FAILURE))
            return com.in28minutes.courses.Status.FAILURE;
        return com.in28minutes.courses.Status.SUCCESS;
    }


}
