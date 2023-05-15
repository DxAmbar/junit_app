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
        assertNotNull(real, () -> "La cuenta no puede ser nula"); // todos los assert pueden recibir un último argumento string para un mensaje
        Assertions.assertEquals(esperado, real, () ->" El nombre de la cuenta no es el que se esperaba"); //si se deja solo el último argumento literal como string, se genera el mensaje si o si (no se muestra, pero usa memoria)
        Assertions.assertTrue(real.equals("Daniela"), () -> "Nombre de la cuenta debe ser real a la real"); //() -> captura el error para mostrar el mensaje

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

    @Test
    void testTransferirDineroCuenta() {
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Daniela", new BigDecimal("1500.8989"));
        Banco banco = new Banco();
        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta1.getSaldo().toPlainString());
    }

    @Test
    void testRelacionBancoCuentas() {
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Daniela", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);

        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        assertAll(  //con assertAll podemos obtener el detalle de cada fallo en la prueba
                () -> assertEquals("1000.8989", cuenta2.getSaldo().toPlainString()), //separar con , los asserts
                () -> assertEquals("3000", cuenta1.getSaldo().toPlainString()),
                () -> assertEquals(2,banco.getCuentas().size()),
                () -> assertEquals("Banco del Estado", cuenta1.getBanco().getNombre()),
                () -> assertEquals("Daniela", banco.getCuentas().stream()
                        .filter(c -> c.getPersona().equals("Daniela"))
                        .findFirst()
                        .get().getPersona()),
                () -> assertTrue(banco.getCuentas().stream()
                        /*.filter(c -> c.getPersona().equals("Daniela"))
                        .findFirst().isPresent());
                        otra forma:*/
                        .anyMatch(c -> c.getPersona().equals("Daniela")))

        );

    }

}