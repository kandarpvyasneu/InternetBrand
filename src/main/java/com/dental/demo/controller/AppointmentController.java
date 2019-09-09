package com.dental.demo.controller;

import com.dental.demo.Exception.AppointmentException;
import com.dental.demo.Pojo.Appointment;
import com.dental.demo.Service.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class AppointmentController
{

    private AppointmentService appointmentService;

    private final static Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    public AppointmentController(AppointmentService appointmentService)
    {
        this.appointmentService = appointmentService;
    }

    @PostMapping(value = "/dentalAppointments")
    public ResponseEntity<Object> createAppointment(@Valid @RequestBody Appointment appointment)
    {
        logger.info("-Entered into the createAppointment() controller method-");

        Appointment newAppointment = null;

        try
        {
            newAppointment = appointmentService.createAppointment(appointment);
        }
        catch (AppointmentException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        logger.info("-Exited from the createAppointment() controller method-");

        return ResponseEntity.ok().body((Appointment) newAppointment);
    }


    @GetMapping("/dentalAppointments/{Id}")
    public ResponseEntity<Object> findAppointmentById(@PathVariable("Id") Long Id)
    {
        logger.info("-Entered into the findAppointmentById() controller method-");

        Optional<Appointment> currentAppointment = null;

        try
        {
            currentAppointment = appointmentService.findAppointmentById(Id);
        }
        catch (NumberFormatException q)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Number can't be string");
        }
        catch (AppointmentException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


        logger.info("-Exited from the findAppointmentById() controller method-");

        return ResponseEntity.ok(currentAppointment);
    }
}
