package com.dental.demo.Pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.TimeZone;

@Entity
@Table(name = "appointment")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Appointment
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm z")
    private Date startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm z")
    private Date endTime;

    private Integer patient_Id;

    private Integer dentist_Id;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Date getStartTime()
    {
        if (startTime == null)
        {
            return new Date(1900, 01, 01);
        }
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getEndTime()
    {
        if (endTime == null)
        {
            return new Date(1900, 01, 01);
        }
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public Integer getPatient_Id()
    {
        return patient_Id;
    }

    public void setPatient_Id(Integer patient_Id)
    {
        this.patient_Id = patient_Id;
    }

    public Integer getDentist_Id()
    {
        return dentist_Id;
    }

    public void setDentist_Id(Integer dentist_Id)
    {
        this.dentist_Id = dentist_Id;
    }
}
