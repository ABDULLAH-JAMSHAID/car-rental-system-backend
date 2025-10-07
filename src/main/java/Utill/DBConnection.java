package Utill;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import java.util.Properties;

public class DBConnection {

    private static HikariDataSource db;

    static {
        try {
            Properties props = new Properties();
            props.load(DBConnection.class.getClassLoader().getResourceAsStream("resources.properties"));

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));
            config.setDriverClassName("org.postgresql.Driver"); //  important line
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setPoolName("AppHikariCP");

            db = new HikariDataSource(config);
            System.out.println("Database connection pool initialized.");

        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static DataSource getDataSource() {
        return db;
    }
}
