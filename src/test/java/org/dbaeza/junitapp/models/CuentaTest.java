package org.dbaeza.junitapp.models;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;


class CuentaTest {

    @Test
    void testNombreCuenta(){
        Cuenta cuenta = new Cuenta("Daniela", new BigDecimal("102890.54"));
        //cuenta.setPersona("Daniela");
        String esperado = "Daniela";
        String real = cuenta.getPersona();
        Assertions.assertEquals(esperado, real);
        Assertions.assertTrue(real.equals("Daniela"));

    }

    @Test
    void testSaldoCuenta() {
        Cuenta cuenta = new Cuenta("Daniela", new BigDecimal("1000.541"));
        Assertions.assertEquals(1000.541, cuenta.getSaldo().doubleValue());
        Assertions.assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        Assertions.assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0); //es lo mismo de arriba
    }


}