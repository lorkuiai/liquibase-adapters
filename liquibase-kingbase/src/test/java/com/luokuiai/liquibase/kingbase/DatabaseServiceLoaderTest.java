package com.luokuiai.liquibase.kingbase;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ServiceLoader;

import liquibase.database.Database;
import org.junit.jupiter.api.Test;

class DatabaseServiceLoaderTest {
    @Test
    void registersKingbaseDatabaseImplementations() {
        ServiceLoader<Database> databases = ServiceLoader.load(Database.class);

        assertTrue(contains(databases, KingbasePostgresDatabase.class));
        assertTrue(contains(databases, KingbaseMySqlDatabase.class));
    }

    private boolean contains(ServiceLoader<Database> databases,
            Class<? extends Database> expectedType) {
        for (Database database : databases) {
            if (expectedType.isInstance(database)) {
                return true;
            }
        }
        return false;
    }
}
