package com.example.myapplication;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CarInfoTest {

    private CarInfo carInfo;

    @Before
    public void setUp() {
        carInfo = new CarInfo("Tesla Model S", "Electric Sedan", "Electric", "https://www.tesla.com/models");
    }

    @Test
    public void testGetName() {
        assertEquals("Tesla Model S", carInfo.getName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Electric Sedan", carInfo.getDescription());
    }

    @Test
    public void testGetType() {
        assertEquals("Electric", carInfo.getType());
    }

    @Test
    public void testGetLink() {
        assertEquals("https://www.tesla.com/models", carInfo.link);
    }
}

