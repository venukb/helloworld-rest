package com.venu.rest.res;

import com.venu.model.employee.Employee;

import java.text.DateFormat;
import java.time.Instant;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.v3.oas.annotations.tags.Tag;

@Path("/employee")
@Tag(name = "Employee API", description = "Create/List/Delete Employee ")
@Singleton
public class EmployeeHandler {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getEmployeeById(@QueryParam("id") String id) {
        return Employee.builder().age(20).name("VK").build();
    }

}
