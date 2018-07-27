package modules;

import com.google.inject.name.Names;
import mappers.StorageMapper;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import play.db.Database;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.sql.DataSource;

/**
 * BatisModule. Created at 7/27/2018 11:40 AM by @author Timur Isachenko
 * @isatimur | † be patient and test it! †
 */
public class BatisModule extends org.mybatis.guice.MyBatisModule {

    @Override
    protected void initialize() {
        environmentId("development");
        bindConstant().annotatedWith(
                Names.named("mybatis.configuration.failFast")).
                to(true);
        bindDataSourceProviderType(PlayDataSourceProvider.class); // это подключение датасорса, который заинжектен play-ем
        bindTransactionFactoryType(JdbcTransactionFactory.class);

        // Подключение пакета с мапперами
        addMapperClasses(StorageMapper.class.getPackage().getName());

        // Подключение отдельного маппера
//        addMapperClass(RateMapper.class);

    }

    @Singleton
    public static class PlayDataSourceProvider implements Provider<DataSource> {
        final Database db;

        // сюда инжектится датасорс из конфига
        @Inject
        public PlayDataSourceProvider(final Database db) {
            this.db = db;
        }


        @Override
        public DataSource get() {
            return db.getDataSource();
        }
    }

}
