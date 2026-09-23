package com.example.carpark;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// fresh context each test so the in-memory state doesn't leak
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ParkingControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void parkThenBillFlow() throws Exception {
        mvc.perform(post("/parking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"vehicleReg\":\"AB12 CDE\",\"vehicleType\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.spaceNumber", is(1)))
                .andExpect(jsonPath("$.timeIn", notNullValue()));

        mvc.perform(post("/parking/bill")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"vehicleReg\":\"AB12 CDE\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleReg", is("AB12 CDE")))
                .andExpect(jsonPath("$.vehicleCharge", notNullValue()));
    }

    @Test
    void badVehicleTypeGives400() throws Exception {
        mvc.perform(post("/parking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"vehicleReg\":\"BAD001\",\"vehicleType\":9}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void billingACarThatIsntThereGives404() throws Exception {
        mvc.perform(post("/parking/bill")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"vehicleReg\":\"GHOST1\"}"))
                .andExpect(status().isNotFound());
    }
}
