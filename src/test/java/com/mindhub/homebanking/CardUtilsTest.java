package com.mindhub.homebanking;

import com.mindhub.homebanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CardUtilsTest {
    @Test
    public void getCvvNumberTest() {
        String stringNumber = CardUtils.createCvvNumber();
        try{
            Integer number = Integer.parseInt(stringNumber);

            assertTrue(number > 0 && number < 1000);
        } catch (NumberFormatException e) {
            System.out.println("El numero devuelto es nulo!");
            assertTrue(false);
        } catch (Exception e1) {
            assertTrue(false);
        }
    }
}
