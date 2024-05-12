package org.example.seminarFour;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.cfg.Configuration;

public class Connector {
    private final String DATABASE = "seminarFour1.cfg.xml";
    private final SessionFactory SESION;
    private final Configuration CONFIGURATION;

    public Connector() {
        this.CONFIGURATION = new Configuration().configure(DATABASE);
        this.SESION = CONFIGURATION.buildSessionFactory();
    }
    public Session getSession(){
        return SESION.openSession();
    }
}
