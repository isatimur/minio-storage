package modules;

import com.google.inject.AbstractModule;
import config.MinioConfig;
import services.MinioObjectServiceImpl;

/**
 * LinkingModule. Created at 7/27/2018 11:40 AM by @author Timur Isachenko
 * @isatimur | † be patient and test it! †
 */
public class LinkingModule extends AbstractModule {

    @Override
    protected void configure() {

        // Подключение всех необходимых сервисов, DAO и job-ов по образу:
        bind(MinioObjectServiceImpl.class).asEagerSingleton();

        bind(MinioConfig.class).asEagerSingleton();

        bind(MinioClientInjection.class).asEagerSingleton();
    }

}
