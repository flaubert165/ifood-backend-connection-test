package infrastructure.repositories;

import infrastructure.DataSourceProvider;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public class PersistenceModule extends org.mybatis.guice.MyBatisModule {
    @Override
    protected void initialize() {
        environmentId("development");
        bindDataSourceProviderType(DataSourceProvider.class);
        bindTransactionFactoryType(JdbcTransactionFactory.class);
        addMapperClass(IAuthenticationRepository.class);
        addMapperClass(IUserRepository.class);
        addMapperClass(IUnavailabilityScheduleRepository.class);
    }
}
