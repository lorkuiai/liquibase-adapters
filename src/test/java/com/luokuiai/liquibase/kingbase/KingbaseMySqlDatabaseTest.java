package com.luokuiai.liquibase.kingbase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import liquibase.CatalogAndSchema;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;
import org.junit.jupiter.api.Test;

class KingbaseMySqlDatabaseTest {
    @Test
    void returnsKingbaseDriverForKingbaseUrl() {
        KingbaseMySqlDatabase database = new KingbaseMySqlDatabase();

        assertEquals(KingbaseSupport.DRIVER_CLASS,
                database.getDefaultDriver("jdbc:kingbase8://localhost:54321/test"));
    }

    @Test
    void exposesKingbaseMySqlShortName() {
        KingbaseMySqlDatabase database = new KingbaseMySqlDatabase();

        assertEquals("kingbase-mysql", database.getShortName());
    }

    @Test
    void mapsJdbcMetadataToKingbaseSchema() {
        KingbaseMySqlDatabase database = new KingbaseMySqlDatabase();

        CatalogAndSchema catalogAndSchema = database.getSchemaFromJdbcInfo(
                "kingbase", "public");

        assertNull(catalogAndSchema.getCatalogName());
        assertNull(catalogAndSchema.getSchemaName());
        assertFalse(database.supportsSchemas());
        assertFalse(database.supportsCatalogs());
        assertNull(database.getConnectionCatalogName());
        assertFalse(database.supportsDDLInTransaction());
        assertFalse(database.supports(Catalog.class));
        assertFalse(database.supports(Schema.class));
        assertFalse(database.supportsCatalogInObjectName(Table.class));
        assertEquals("databasechangelog",
                database.getDatabaseChangeLogTableName());
        assertEquals("databasechangeloglock",
                database.getDatabaseChangeLogLockTableName());
    }
}
