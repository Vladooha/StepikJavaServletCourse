package com.vladooha.backend;

import com.vladooha.backend.dataSets.UserDataSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

public class DatabaseService {
    private Configuration cfg;
    private SessionFactory sessionFactory;

    public void setMysqlConnection() {
        cfg = new Configuration();
        cfg.addAnnotatedClass(UserDataSet.class);

        cfg.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        cfg.setProperty("hibernate.show_sql", "true");
        cfg.setProperty("hibernate.hbm2ddl.auto", "update");

        cfg.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/stepik_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        //?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=MSK
        cfg.setProperty("hibernate.connection.username", "vladooha");
        cfg.setProperty("hibernate.connection.password", "080199dO");

        sessionFactory = cfg.buildSessionFactory();
    }

    public <T> long save(T dataSet) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        long result = (Long) session.save(dataSet);
        transaction.commit();
        session.close();

        return result;
    }

    public <T> T load(Class<T> dataSetClass, long id) {
        Session session = sessionFactory.openSession();
        T dataSet = session.load(dataSetClass, id);
        session.close();

        return dataSet;
    }

    public <T, K extends Object> T loadByField(Class<T> dataSetClass, String field, K value) {
        Session session = sessionFactory.openSession();

        /// TODO Learn new HQL
//        T dataSet = session.getCriteriaBuilder().createQuery(dataSetClass).
//                select();

        /// TODO Fix deprecated method
        T dataSet = (T) session.createCriteria(dataSetClass).add(Restrictions.eq(field, value)).uniqueResult();
        session.close();

        return dataSet;
    }
}