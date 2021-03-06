/*
 * This file is generated by jOOQ.
 */
package com.bank.jooq.tables.daos;


import com.bank.jooq.tables.Account;
import com.bank.jooq.tables.records.AccountRecord;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


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
public class AccountDao extends DAOImpl<AccountRecord, com.bank.jooq.tables.pojos.Account, UUID> {

    /**
     * Create a new AccountDao without any configuration
     */
    public AccountDao() {
        super(Account.ACCOUNT, com.bank.jooq.tables.pojos.Account.class);
    }

    /**
     * Create a new AccountDao with an attached configuration
     */
    public AccountDao(Configuration configuration) {
        super(Account.ACCOUNT, com.bank.jooq.tables.pojos.Account.class, configuration);
    }

    @Override
    public UUID getId(com.bank.jooq.tables.pojos.Account object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>ID BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.bank.jooq.tables.pojos.Account> fetchRangeOfId(UUID lowerInclusive, UUID upperInclusive) {
        return fetchRange(Account.ACCOUNT.ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>ID IN (values)</code>
     */
    public List<com.bank.jooq.tables.pojos.Account> fetchById(UUID... values) {
        return fetch(Account.ACCOUNT.ID, values);
    }

    /**
     * Fetch a unique record that has <code>ID = value</code>
     */
    public com.bank.jooq.tables.pojos.Account fetchOneById(UUID value) {
        return fetchOne(Account.ACCOUNT.ID, value);
    }

    /**
     * Fetch records that have <code>NAME BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.bank.jooq.tables.pojos.Account> fetchRangeOfName(String lowerInclusive, String upperInclusive) {
        return fetchRange(Account.ACCOUNT.NAME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>NAME IN (values)</code>
     */
    public List<com.bank.jooq.tables.pojos.Account> fetchByName(String... values) {
        return fetch(Account.ACCOUNT.NAME, values);
    }

    /**
     * Fetch records that have <code>BALANCE BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.bank.jooq.tables.pojos.Account> fetchRangeOfBalance(BigDecimal lowerInclusive, BigDecimal upperInclusive) {
        return fetchRange(Account.ACCOUNT.BALANCE, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>BALANCE IN (values)</code>
     */
    public List<com.bank.jooq.tables.pojos.Account> fetchByBalance(BigDecimal... values) {
        return fetch(Account.ACCOUNT.BALANCE, values);
    }
}
