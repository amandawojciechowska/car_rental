package cap.s42academy.controller;

import cap.s42academy.exceptions.CarNotFoundException;
import cap.s42academy.model.Car;
import cap.s42academy.service.impl.CarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static cap.s42academy.SampleTestDataFactory.car1;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    @Mock
    private CarServiceImpl carServiceMock;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        CarController controller = new CarController(carServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    void shouldReturnCar_whenGettingCarById() throws Exception {
        //given
        //when
        Car car = car1();
        long carId = 1L;
        when(carServiceMock.getCar(carId)).thenReturn(Optional.of(car));

        //then
        mockMvc.perform(get("/cars/{carId}", carId))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value(car.getBrand()));
    }

    @Test
    void shouldReturnNotFound_whenGettingCarById_thatDoesNotExist() throws Exception {
        //given
        //when
        long carId = 1L;
        when(carServiceMock.getCar(carId)).thenReturn(Optional.empty());

        //then
        mockMvc.perform(get("/cars/{carId}", carId))
                .andDo(log())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnCreatedCar_whenCreatingCar() throws Exception {
        //given
        //when
        Car car = car1();
        when(carServiceMock.addCar(car)).thenReturn(car);
        String requestBody = """
                {
                  "brand": "%s",
                  "model": "%s",
                  "yearOfProduction": "%s",
                  "licencePlate": "%s",
                  "dailyRate": "%s"
                }
                """.formatted(car.getBrand(), car.getModel(), car.getYearOfProduction(), car.getLicencePlate(), car.getDailyRate());

        //then
        mockMvc.perform(post("/cars").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(log())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.brand").value(car.getBrand()))
                .andExpect(jsonPath("$.model").value(car.getModel()))
                .andExpect(jsonPath("$.yearOfProduction").value(car.getYearOfProduction()))
                .andExpect(jsonPath("$.licencePlate").value(car.getLicencePlate()));
    }

    @Test
    void shouldReturnBadRequest_whenCreatingCar_withEmptyRequestBody() throws Exception {
        //given
        //when
        String requestBody = "{}";

        //then
        mockMvc.perform(post("/cars").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(log())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnCar_whenFindingCarByLicencePlate() throws Exception {
        //given
        //when
        Car car = car1();
        String licencePlate = "ONYXD123";
        when(carServiceMock.getCarByLicencePlate(licencePlate)).thenReturn(Optional.of(car));

        //then
        mockMvc.perform(get("/cars?licencePlate={licencePlate}", licencePlate))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value(car.getBrand()));
    }

    @Test
    void shouldReturnEmptyOptional_whenFindingCarByLicencePlate_thatDoesNotExist() throws Exception {
        //given
        //when
        Car car = car1();
        String licencePlate = "DWX29373";
        when(carServiceMock.getCarByLicencePlate(licencePlate)).thenReturn(Optional.empty());

        //then
        mockMvc.perform(get("/cars?licencePlate={licencePlate}", licencePlate))
                .andDo(log())
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteCar_whenDeletingCarById() throws Exception {
        //given
        //when
        long carId = 1L;
        willDoNothing().given(carServiceMock)
                .deleteCar(carId);

        //then
        mockMvc.perform(delete("/cars/{carId}", carId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFound_whenDeleteCar_andCarDoesNotExist() throws Exception {
        //given
        //when
        //then
        doThrow(CarNotFoundException.class).when(carServiceMock)
                .deleteCar(1L);
        mockMvc.perform(delete("/cars/{carsId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isNotFound());
    }

}