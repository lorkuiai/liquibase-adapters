package com.luokuiai.liquibase.kingbase;

import java.util.Locale;

import liquibase.CatalogAndSchema;
import liquibase.database.DatabaseConnection;
import liquibase.database.core.MySQLDatabase;
import liquibase.exception.DatabaseException;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Schema;

/**
 * KingbaseES adapter for MySQL compatibility mode.
 *
 * <p>This class only participates in auto-detection when
 * {@code -Dliquibase.kingbase.compatMode=mysql} is set. Without that explicit
 * mode, Kingbase defaults to the PostgreSQL-compatible adapter.</p>
 */
public class KingbaseMySqlDatabase extends MySQLDatabase {
    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection connection)
            throws DatabaseException {
        return KingbaseSupport.isMySqlMode()
                && KingbaseSupport.isKingbaseConnection(connection);
    }

    @Override
    public String getShortName() {
        return "kingbase-mysql";
    }

    @Override
    protected String getDefaultDatabaseProductName() {
        return "KingbaseES";
    }

    @Override
    public String getDefaultDriver(String url) {
        if (KingbaseSupport.isKingbaseUrl(url)) {
            return KingbaseSupport.DRIVER_CLASS;
        }
        return super.getDefaultDriver(url);
    }

    @Override
    public Integer getDefaultPort() {
        return KingbaseSupport.DEFAULT_PORT;
    }

    @Override
    public boolean supportsSchemas() {
        return false;
    }

    @Override
    public boolean supportsCatalogs() {
        return false;
    }

    @Override
    protected String getConnectionCatalogName() {
        return null;
    }

    @Override
    public boolean supportsDDLInTransaction() {
        return false;
    }

    @Override
    public boolean supports(Class<? extends DatabaseObject> object) {
        if (Catalog.class.isAssignableFrom(object)
                || Schema.class.isAssignableFrom(object)) {
            return false;
        }
        return super.supports(object);
    }

    @Override
    public boolean supportsCatalogInObjectName(
            Class<? extends DatabaseObject> type) {
        return false;
    }

    @Override
    public CatalogAndSchema getSchemaFromJdbcInfo(String rawCatalogName,
            String rawSchemaName) {
        return new CatalogAndSchema(null, null);
    }

    @Override
    public String getDatabaseChangeLogTableName() {
        return super.getDatabaseChangeLogTableName().toLowerCase(Locale.ROOT);
    }

    @Override
    public String getDatabaseChangeLogLockTableName() {
        return super.getDatabaseChangeLogLockTableName()
                .toLowerCase(Locale.ROOT);
    }
}
