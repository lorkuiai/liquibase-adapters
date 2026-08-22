package com.luokuiai.liquibase.kingbase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import liquibase.CatalogAndSchema;
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

        assertEquals("kingbase", catalogAndSchema.getCatalogName());
        assertEquals("public", catalogAndSchema.getSchemaName());
        assertTrue(database.supportsSchemas());
        assertTrue(database.supportsCatalogs());
        assertFalse(database.supportsDDLInTransaction());
        assertTrue(database.supports(Schema.class));
        assertFalse(database.supportsCatalogInObjectName(Table.class));
        assertEquals("databasechangelog",
                database.getDatabaseChangeLogTableName());
        assertEquals("databasechangeloglock",
                database.getDatabaseChangeLogLockTableName());
    }
}
