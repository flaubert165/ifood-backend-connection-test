package infrastructure;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import play.db.Database;

import javax.sql.DataSource;

@Singleton
public class DataSourceProvider implements Provider<DataSource> {
    private final Database database;
    @Inject
    public DataSourceProvider(final Database database) {
        this.database = database;
    }

    @Override
    public DataSource get() {
        return database.getDataSource();
    }
}
