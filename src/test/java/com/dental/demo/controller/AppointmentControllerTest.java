package com.dental.demo.controller;

import com.dental.demo.Pojo.Appointment;
import com.dental.demo.Service.AppointmentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.mockito.MockitoAnnotations;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class AppointmentControllerTest
{

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AppointmentService appointmentService;

    private ObjectMapper mapper = new ObjectMapper();

    private Appointment appointment = new Appointment();

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        AppointmentController appointmentController = new AppointmentController(appointmentService);

        mockMvc = MockMvcBuilders.standaloneSetup(appointmentController)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void createAppointment()
    {
        try
        {
            Appointment obj = new Appointment();

            Mockito.when(appointmentService.createAppointment(Mockito.any(Appointment.class))).thenReturn(obj);

            String jsonContent = "{\"startTime\":\"2019-09-07 18:30 EDT\",\"endTime\":\"2019-09-07 19:00 EDT\",\"patient_Id\":4,\"dentist_Id\":12}";

            RequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:8080/dentalAppointments/").accept(MediaType.APPLICATION_JSON).content(jsonContent).contentType(MediaType.APPLICATION_JSON);

            MvcResult result = mockMvc.perform(requestBuilder).andReturn();

            MockHttpServletResponse response = result.getResponse();

            //System.out.println("____response____::" + response.getStatus());

            //System.out.println("_______HTTP___::" + HttpStatus.CREATED.value());

            //assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void findAllAppointment()
    {
    }

    @Test
    public void findAppointmentById()
    {
        try
        {
            Appointment obj = new Appointment();

            Mockito.when(appointmentService.findAppointmentById(Mockito.anyLong())).thenReturn(Optional.of(obj));

            RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:8080/dentalAppointments/1").accept(MediaType.APPLICATION_JSON);

            MvcResult result = mockMvc.perform(requestBuilder).andReturn();

            System.out.println("_____" + result.getResponse().getContentAsString());

            String expected = "{\"id\":null,\"startTime\":\"3800-02-01 05:00 UTC\",\"endTime\":\"3800-02-01 05:00 UTC\",\"patient_Id\":null,\"dentist_Id\":null}";

            JSONAssert.assertEquals(expected, result.getResponse()
                    .getContentAsString(), false);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}