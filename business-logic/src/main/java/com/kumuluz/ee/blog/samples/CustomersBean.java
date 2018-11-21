package com.kumuluz.ee.blog.samples;


import com.kumuluz.ee.blog.samples.models.Customer;
import com.kumuluz.ee.blog.samples.models.Order;
import com.kumuluz.ee.blog.samples.config.RestProperties;
import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Zrno CustomersBean je namenjeno upravljanju entitete Customers
 *
 * @since 1.0.0
 * @author Zvone Gazvoda
 */
@RequestScoped
public class CustomersBean {

    private Logger log = LogManager.getLogger(CustomersBean.class.getName());

    @Inject
    private RestProperties restProperties;

    @Inject
    private EntityManager em;

    @Inject
    private CustomersBean customersBean;

    private Client httpClient;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
    }


    public List<Customer> getCustomers() {

        TypedQuery<Customer> query = em.createNamedQuery("Customer.getAll", Customer.class);

        return query.getResultList();

    }

    /**
     * Metoda pridobi seznam strank, ki ustrezajo filtrom. Metoda stranke pridobi iz podatkovne baze.
     *
     * @since 1.0.0
     * @author Zvone Gazvoda
     * @param uriInfo 	Objekt, ki vsebuje vrednosti filtrov.
     * @return Seznam objektov strank.
     */
    public List<Customer> getCustomersFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, Customer.class, queryParameters);
    }

    /**
     * Metoda pridobi stranko, ki ustreza identifikatorju.
     *
     * @author Zvone Gazvoda
     * @param customerId  Identifikator stranke.
     * @throws NotFoundException Napaka v primeru, da stranka ne obstaja.
     * @return Objekt stranke.
     */
    public Customer getCustomer(String customerId) {

        Customer customer = em.find(Customer.class, customerId);

        if (customer == null) {
            throw new NotFoundException();
        }

        return customer;
    }

    /**
     * Metoda prejme objekt Customer in ustvari novo entiteto.
     *
     * @author Zvone Gazvoda
     * @param customer Objekt, ki vsebuje podatke stranke.
     * @return Novo kreirana entiteta stranke.
     */
    public Customer createCustomer(Customer customer) {

        try {
            beginTx();
            em.persist(customer);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return customer;
    }

    /**
     * Metoda omogoča posodabljanje obstoječe stranke.
     *
     * @author Zvone Gazvoda
     * @param customer Objekt, ki vsebuje podatke stranke.
     * @param customerId Identifikator obstoječe stranke.
     * @return Posodobljena stranka.
     */
    public Customer putCustomer(String customerId, Customer customer) {

        Customer c = em.find(Customer.class, customerId);

        if (c == null) {
            return null;
        }

        try {
            beginTx();
            customer.setId(c.getId());
            customer = em.merge(customer);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return customer;
    }

    /**
     * Metoda omogoča odstranjevanje strank..
     *
     * @author Zvone Gazvoda
     * @param customerId Identifikator stranke za brisanje.
     * @return Status brisanja.
     */
    public boolean deleteCustomer(String customerId) {

        Customer customer = em.find(Customer.class, customerId);

        if (customer != null) {
            try {
                beginTx();
                em.remove(customer);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        } else
            return false;

        return true;
    }


    public List<Order> getOrdersFallback(String customerId) {
        return new ArrayList<>();
    }


    private void beginTx() {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
    }

    private void commitTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().commit();
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
    }

    public void loadOrder(Integer n) {

    }
}
