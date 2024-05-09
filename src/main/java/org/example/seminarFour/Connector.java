package org.example.seminarFour;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.DriverManager;

public class Connector {
    private final StandardServiceRegistry REGISTRY;
    private final SessionFactory SESION;

    public Connector() {
        REGISTRY = new StandardServiceRegistryBuilder()
                .configure("seminarFour.cfg.xml")
                .build();
        this.SESION = new MetadataSources()
                .buildMetadata(REGISTRY)
                .buildSessionFactory();
    }
    public Session getSession(){
        return SESION.openSession();
    }
}
