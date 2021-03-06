/*
 * This file is generated by jOOQ.
 */
package com.bank.jooq.tables;


import com.bank.jooq.Indexes;
import com.bank.jooq.Keys;
import com.bank.jooq.Public;
import com.bank.jooq.tables.records.TransferdetailsRecord;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row6;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Transferdetails extends TableImpl<TransferdetailsRecord> {

    private static final long serialVersionUID = -415609152;

    /**
     * The reference instance of <code>PUBLIC.TRANSFERDETAILS</code>
     */
    public static final Transferdetails TRANSFERDETAILS = new Transferdetails();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TransferdetailsRecord> getRecordType() {
        return TransferdetailsRecord.class;
    }

    /**
     * The column <code>PUBLIC.TRANSFERDETAILS.ID</code>.
     */
    public final TableField<TransferdetailsRecord, UUID> ID = createField(DSL.name("ID"), org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.TRANSFERDETAILS.RECEIVER_ID</code>.
     */
    public final TableField<TransferdetailsRecord, UUID> RECEIVER_ID = createField(DSL.name("RECEIVER_ID"), org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.TRANSFERDETAILS.SENDER_ID</code>.
     */
    public final TableField<TransferdetailsRecord, UUID> SENDER_ID = createField(DSL.name("SENDER_ID"), org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.TRANSFERDETAILS.AMOUNT</code>.
     */
    public final TableField<TransferdetailsRecord, BigDecimal> AMOUNT = createField(DSL.name("AMOUNT"), org.jooq.impl.SQLDataType.DECIMAL.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.TRANSFERDETAILS.STATUS</code>.
     */
    public final TableField<TransferdetailsRecord, String> STATUS = createField(DSL.name("STATUS"), org.jooq.impl.SQLDataType.VARCHAR(2147483647).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.TRANSFERDETAILS.CREATED_ON</code>.
     */
    public final TableField<TransferdetailsRecord, OffsetDateTime> CREATED_ON = createField(DSL.name("CREATED_ON"), org.jooq.impl.SQLDataType.TIMESTAMPWITHTIMEZONE.precision(6).nullable(false), this, "");

    /**
     * Create a <code>PUBLIC.TRANSFERDETAILS</code> table reference
     */
    public Transferdetails() {
        this(DSL.name("TRANSFERDETAILS"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.TRANSFERDETAILS</code> table reference
     */
    public Transferdetails(String alias) {
        this(DSL.name(alias), TRANSFERDETAILS);
    }

    /**
     * Create an aliased <code>PUBLIC.TRANSFERDETAILS</code> table reference
     */
    public Transferdetails(Name alias) {
        this(alias, TRANSFERDETAILS);
    }

    private Transferdetails(Name alias, Table<TransferdetailsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Transferdetails(Name alias, Table<TransferdetailsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Transferdetails(Table<O> child, ForeignKey<O, TransferdetailsRecord> key) {
        super(child, key, TRANSFERDETAILS);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.PRIMARY_KEY_9, Indexes.TRANSFERDETAILS_RECEIVER_ID_FK_INDEX_9, Indexes.TRANSFERDETAILS_SENDER_ID_FK_INDEX_9);
    }

    @Override
    public UniqueKey<TransferdetailsRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_9;
    }

    @Override
    public List<UniqueKey<TransferdetailsRecord>> getKeys() {
        return Arrays.<UniqueKey<TransferdetailsRecord>>asList(Keys.CONSTRAINT_9);
    }

    @Override
    public List<ForeignKey<TransferdetailsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TransferdetailsRecord, ?>>asList(Keys.TRANSFERDETAILS_RECEIVER_ID_FK, Keys.TRANSFERDETAILS_SENDER_ID_FK);
    }

    public Account transferdetailsReceiverIdFk() {
        return new Account(this, Keys.TRANSFERDETAILS_RECEIVER_ID_FK);
    }

    public Account transferdetailsSenderIdFk() {
        return new Account(this, Keys.TRANSFERDETAILS_SENDER_ID_FK);
    }

    @Override
    public Transferdetails as(String alias) {
        return new Transferdetails(DSL.name(alias), this);
    }

    @Override
    public Transferdetails as(Name alias) {
        return new Transferdetails(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Transferdetails rename(String name) {
        return new Transferdetails(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Transferdetails rename(Name name) {
        return new Transferdetails(name, null);
    }

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row6<UUID, UUID, UUID, BigDecimal, String, OffsetDateTime> fieldsRow() {
        return (Row6) super.fieldsRow();
    }
}
