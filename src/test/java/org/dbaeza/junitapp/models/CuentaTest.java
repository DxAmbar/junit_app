package org.dbaeza.junitapp.models;


import org.dbaeza.junitapp.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*; //para hacer assertions sin necesidad de usar Assertions al principio


class CuentaTest {

    @Test
    void testNombreCuenta(){
        Cuenta cuenta = new Cuenta("Daniela", new BigDecimal("102890.54"));
        //cuenta.setPersona("Daniela");
        String esperado = "Daniela";
        String real = cuenta.getPersona();
        assertNotNull(real);
        Assertions.assertEquals(esperado, real);
        Assertions.assertTrue(real.equals("Daniela"));

    }

    @Test
    void testSaldoCuenta() {
        Cuenta cuenta = new Cuenta("Daniela", new BigDecimal("1000.541"));
        assertNotNull(cuenta.getSaldo());
        Assertions.assertEquals(1000.541, cuenta.getSaldo().doubleValue());
        Assertions.assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        Assertions.assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0); //es lo mismo de arriba
    }

    @Test
    void testReferenciaCuenta() {
       Cuenta cuenta = new Cuenta("John Doe", new BigDecimal("8900.9997"));
       Cuenta cuenta2 = new Cuenta("John Doe", new BigDecimal("8900.9997"));

       //Assertions.assertNotEquals(cuenta2, cuenta);
       Assertions.assertEquals(cuenta2, cuenta);
    }

    @Test
    void testDebitoCuenta() {
        Cuenta cuenta = new Cuenta("Daniela", new BigDecimal("1000.451"));
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900.451", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testCreditoCuenta() {
        Cuenta cuenta = new Cuenta("Daniela", new BigDecimal("1000.451"));
        cuenta.credito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1100, cuenta.getSaldo().intValue());
        assertEquals("1100.451", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testDineroInsuficienteExceptionCuenta() {
        Cuenta cuenta = new Cuenta("Daniela", new BigDecimal("1000.451"));
        Exception exception = assertThrows(DineroInsuficienteException.class, ()-> {
            cuenta.debito(new BigDecimal(1500));
        } );
        String actual = exception.getMessage();
        String esperado = "Dinero Insuficiente";
        assertEquals(esperado, actual);
    }
}