package com.alex.gordon.car.controller;

import com.alex.gordon.car.CarRepairApplication;
import com.alex.gordon.car.repo.AppointmentEntity;
import com.alex.gordon.car.repo.AppointmentRepo;
import com.alex.gordon.car.util.JsonConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * <p> Controller test class.</p>
 *
 * @author Alex Gordon
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarRepairApplication.class)
@WebAppConfiguration
public class AppointmentControllerTest {

    @MockBean
    private AppointmentRepo repo;
    @Autowired
    private WebApplicationContext webContext;
    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext)
            .build();
    }

    /**
     * Test create function - positive scenario
     *
     * @throws Exception - error
     */
    @Test
    public void addAppointment_positive() throws Exception {
        //Given
        AppointmentEntity entityExpected = getTestEntity();
        entityExpected.setId("testEntityID");
        //When
        doReturn(entityExpected).when(repo).save(any(AppointmentEntity.class));
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
            .post("/appointments")
            .content(JsonConverter.asJsonString(entityExpected))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));
        //Then
        verify(repo, times(1)).save(any(AppointmentEntity.class));
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated());
        MvcResult mvcResult = resultActions.andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        AppointmentEntity entityActual = JsonConverter.asEntity(content);
        assertEquals(entityExpected, entityActual);
    }

    /**
     * Test create function - negative scenario - one required field is blank!
     *
     * @throws Exception - error
     */
    @Test
    public void addTimesheet_negative() throws Exception {
        //Given
        AppointmentEntity entityExpected = getTestEntity();
        entityExpected.setId("testEntityID");
        //missing client field"
        entityExpected.setClient("");
        //When
        doReturn(entityExpected).when(repo).save(entityExpected);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
            .post("/appointments")
            .content(JsonConverter.asJsonString(entityExpected))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));
        //Then
        verify(repo, times(0)).save(entityExpected);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private AppointmentEntity getTestEntity() throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(AppointmentControllerTest.class.getResourceAsStream("/TestEntity.json")));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        String json = sb.toString();
        return JsonConverter.asEntity(json);
    }

}
