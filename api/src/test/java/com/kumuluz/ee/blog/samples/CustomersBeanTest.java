package com.kumuluz.ee.blog.samples;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static junit.framework.TestCase.assertEquals;


@RunWith(Arquillian.class)
public class CustomersBeanTest {

    @Inject
    private CustomersBean customerBean;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(CustomersBean.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void testGetAllCustomers() {

        assertEquals(3, customerBean.getCustomers().size());
    }

}
