package com.dental.demo.Service;

import com.dental.demo.Exception.AppointmentException;
import com.dental.demo.Repository.AppointmentRepository;
import com.dental.demo.Pojo.Appointment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class AppointmentService
{

    @Autowired
    private AppointmentRepository appointmentRepository;

    private final static Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    public Appointment createAppointment(Appointment appointment) throws AppointmentException
    {
        logger.info("-Entered into the createAppointment() service method-");

        if (appointment == null || appointment.getEndTime() == null || appointment.getPatient_Id() < 0 || appointment.getPatient_Id() == null || appointment.getDentist_Id() == null || appointment.getDentist_Id() < 0 || appointment.getStartTime() == null)
        {
            throw new AppointmentException(404, "Bad request");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm z");

        Date d = new Date();

        String q = sdf.format(d);
        long diff = appointment.getEndTime().getTime() - appointment.getStartTime().getTime();

        long diffSeconds = diff / 1000;
        try
        {
            Date w = sdf.parse(q);


            if (appointment.getStartTime().compareTo(w) <= 0 || appointment.getEndTime().compareTo(w) <= 0 || diffSeconds <= 0)
            {
                throw new AppointmentException(403, "startTime and endTime must both be valid times, and in the future");
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }


        if (diffSeconds < 1800)
        {
            throw new AppointmentException(402, "A dental appointment should be at least 30 minutes");
        }

        List<Appointment> appointmentList = appointmentRepository.findAppointmentByDentist_Id(appointment.getDentist_Id());

        if (appointmentList != null)
        {
            for (Appointment obj : appointmentList)
            {
                if (obj.getStartTime().compareTo(appointment.getStartTime()) == 0)
                {
                    throw new AppointmentException(401, "Two appointments for the same dentist can't start at the same time");
                }
            }
        }

        Appointment newAppointment = appointmentRepository.save(appointment);

        if (newAppointment == null)
        {
            throw new AppointmentException(400, "Appointment is already exist.");
        }

        logger.info("-Exited from the createAppointment() service method-");

        return newAppointment;
    }

    public List<Appointment> findAppointmentByDentist_Id(Integer dentist_Id)
    {
        logger.info("-Entered into the findAllAppointment() service method-");

        List<Appointment> list = appointmentRepository.findAppointmentByDentist_Id(dentist_Id);

        logger.info("-Exited from the findAllAppointment() service method-");

        return list;

    }

    public Optional<Appointment> findAppointmentById(Long Id) throws AppointmentException, NumberFormatException
    {
        logger.info("-Entered into the findAppointmentById() service method-");

        if (Id < 0)
            throw new AppointmentException(406, "Id can not be negative");

        Optional<Appointment> curr = appointmentRepository.findById(Id);

        if (!curr.isPresent())
            throw new AppointmentException(407, "No record found");

        logger.info("-Exited from the findAppointmentById() service method-");

        return curr;
    }

}
