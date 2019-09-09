package com.dental.demo.Repository;

import com.dental.demo.Pojo.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>
{

    @Query(value = "SELECT * FROM Appointment WHERE dentist_Id = ?1", nativeQuery = true)
    List<Appointment> findAppointmentByDentist_Id(Integer dentist_Id);
}
