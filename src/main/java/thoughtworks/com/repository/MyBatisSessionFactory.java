package thoughtworks.com.repository;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisSessionFactory {
    private SqlSessionFactory sqlSessionFactory;

    public MyBatisSessionFactory() throws IOException {
        InputStream resourceAsStream = null;
        resourceAsStream = Resources.getResourceAsStream("mybatis.xml");

        sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
    }

    public SqlSession getSqlSession() {
        return this.sqlSessionFactory.openSession();
    }
}
