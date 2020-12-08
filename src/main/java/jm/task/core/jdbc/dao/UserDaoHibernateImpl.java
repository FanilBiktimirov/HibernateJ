package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class UserDaoHibernateImpl extends Util implements UserDao {

    Transaction transaction = null;
    Connection connection = getConnection();


    public UserDaoHibernateImpl() {

    }

    public void createUsersTable() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (id INTEGER AUTO_INCREMENT PRIMARY KEY, \n" +
                "    name VARCHAR(30), \n" +
                "    lastName VARCHAR(30),\n" +
                "    age INTEGER\n" +
                ");";
        try (PreparedStatement preparedStatement = connection.prepareStatement(createUsersTable)) {
            preparedStatement.execute();
            System.out.println("Таблица в базе данных успешно создана.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String drop = "DROP TABLE IF EXISTS users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(drop)) {
            preparedStatement.execute();
            System.out.println("Таблица успешно удалена.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Session session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
                throw ex;
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            Session session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(session.load(User.class, id));
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
                throw ex;
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            Session session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            List<User> result = session.createQuery("from User").list();
            session.getTransaction().commit();
            return result;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
                throw ex;
            }
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        try {
            Session session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
                throw ex;
            }
        }
    }
}
