package com.example.springhibernatecrud.dao;


import com.example.springhibernatecrud.models.Persona;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;

@Component
public class PersonDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Transactional(readOnly = true)
    //внутри метода будет открыта транзакция. в скобках пишется только для тех классов откуда мы только читаем
    public List<Persona> index() throws SQLException { // в спринге мы не открывает транзакции сами
        Session session = sessionFactory.openSession();
        //Далее обычный hibernate код
        return session.createQuery("SELECT p from Persona p", Persona.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Persona show(int id) {
        Session session = sessionFactory.openSession();
        return session.get(Persona.class, id);
    }

    @Transactional
    public void save(Persona persona) throws SQLException {
        Session session = sessionFactory.openSession();
        session.save(persona);
    }

    @Transactional
    public void update(int id, Persona persona) {
        Session session = sessionFactory.openSession();
        Persona personToBeUpdated = session.get(Persona.class,id);
        personToBeUpdated.setName(persona.getName());
        personToBeUpdated.setAge(persona.getAge());
        personToBeUpdated.setEmail(persona.getEmail());
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        session.remove(session.get(Persona.class,id));
    }
}