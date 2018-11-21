package com.kumuluz.ee.blog.samples.api.v1.utils;

import com.kumuluz.ee.blog.samples.models.Customer;

import java.util.Calendar;
import java.util.Date;

public class RestUtils {

    public boolean isCustomerValid(Customer customer) {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -150);
        Date d = cal.getTime();

        return customer.getAddress() != null && customer.getDateOfBirth() != null && customer.getDateOfBirth().after(d) && customer
                .getFirstName() != null && customer.getLastName() != null;
    }

}
