package com.kumuluz.ee.blog.samples;

import com.kumuluz.ee.blog.samples.api.v1.utils.RestUtils;
import com.kumuluz.ee.blog.samples.models.Customer;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

public class RestUtilsTest {

    @Test
    public void testIsTrue() {

        RestUtils utils = new RestUtils();

        Customer c1 = new Customer();
        c1.setAddress("Dunajska cesta 15, 1000 Ljubljana");
        c1.setFirstName("Janez");
        c1.setLastName("Novak");
        c1.setDateOfBirth(new Date());

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -160);

        Date d = cal.getTime();

        Customer c2 = new Customer();
        c2.setAddress("Dunajska cesta 15, 1000 Ljubljana");
        c2.setFirstName("Janez");
        c2.setDateOfBirth(d);

        Customer c3 = new Customer();
        c3.setAddress("Dunajska cesta 15, 1000 Ljubljana");
        c3.setFirstName("Janez");
        c3.setDateOfBirth(d);

        Customer c4 = new Customer();
        c4.setAddress("Dunajska cesta 15, 1000 Ljubljana");
        c4.setLastName("Novak");
        c4.setDateOfBirth(d);

        Customer c5 = new Customer();
        c5.setAddress("Dunajska cesta 15, 1000 Ljubljana");
        c5.setLastName("Novak");

        Customer c6 = new Customer();
        c6.setLastName("Novak");

        assertEquals(true, utils.isCustomerValid(c1));
        assertEquals(false, utils.isCustomerValid(c2));
        assertEquals(false, utils.isCustomerValid(c3));
        assertEquals(false, utils.isCustomerValid(c4));
        assertEquals(false, utils.isCustomerValid(c5));
        assertEquals(false, utils.isCustomerValid(c6));
    }

}