package springbook.user.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.user.domain.User;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoFactory.class)
class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
    }

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        User user = new User("gyumee", "sungchul", "springno1");

        userDao.add(user);

        User user2 = userDao.get(user.getId());

        assertEquals(user.getName(), user2.getName());
        assertEquals(user.getPassword(), user2.getPassword());

        assertEquals(userDao.getCount(), 1);
    }

    @Test
    public void count() throws SQLException, ClassNotFoundException {
        User user1 = new User("gyumee", "123", "springno1");
        User user2 = new User("leegw700", "456", "springno2");
        User user3 = new User("bumjin", "789", "springno3");

        assertEquals(userDao.getCount(), 0);
        userDao.add(user1);
        assertEquals(userDao.getCount(), 1);
        userDao.add(user2);
        assertEquals(userDao.getCount(), 2);
        userDao.add(user3);
        assertEquals(userDao.getCount(), 3);
    }

    @Test
    public void exception() throws SQLException, ClassNotFoundException {
        assertEquals(userDao.getCount(), 0);

        assertThrows(SQLException.class, () -> userDao.get("unknown"));
    }
}