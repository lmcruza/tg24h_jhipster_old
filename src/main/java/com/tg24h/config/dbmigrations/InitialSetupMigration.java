package com.tg24h.config.dbmigrations;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Creates the initial database setup
 */
@ChangeLog(order = "001")
public class InitialSetupMigration {

    private Map<String, String>[] authoritiesUser = new Map[]{new HashMap<>()};

    private Map<String, String>[] authoritiesAdminAndUser = new Map[]{new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()};

    private Map<String, String>[] authoritiesManager = new Map[]{new HashMap<>(), new HashMap<>(), new HashMap<>()};

    {
        authoritiesUser[0].put("_id", "ROLE_USER");
        authoritiesAdminAndUser[0].put("_id", "ROLE_USER");
        authoritiesAdminAndUser[1].put("_id", "ROLE_ADMIN");
        authoritiesAdminAndUser[2].put("_id", "ROLE_MANAGER");
        authoritiesAdminAndUser[3].put("_id", "ROLE_SUPERVISOR");
        authoritiesManager[0].put("_id", "ROLE_USER");
        authoritiesManager[1].put("_id", "ROLE_MANAGER");
        authoritiesManager[2].put("_id", "ROLE_SUPERVISOR");
    }

    @ChangeSet(order = "01", author = "initiator", id = "01-addAuthorities")
    public void addAuthorities(DB db) {
        DBCollection authorityCollection = db.getCollection("jhi_authority");
        authorityCollection.insert(
            BasicDBObjectBuilder.start()
                .add("_id", "ROLE_ADMIN")
                .get());
        authorityCollection.insert(
            BasicDBObjectBuilder.start()
                .add("_id", "ROLE_USER")
                .get());
        authorityCollection.insert(
            BasicDBObjectBuilder.start()
                .add("_id", "ROLE_MANAGER")
                .get());
        authorityCollection.insert(
            BasicDBObjectBuilder.start()
                .add("_id", "ROLE_SUPERVISOR")
                .get());
    }

    @ChangeSet(order = "02", author = "initiator", id = "02-addUsers")
    public void addUsers(DB db) {
        DBCollection usersCollection = db.getCollection("jhi_user");
        usersCollection.createIndex("login");
        usersCollection.createIndex("email");
        usersCollection.insert(BasicDBObjectBuilder.start()
            .add("_id", "user-0")
            .add("login", "system")
            .add("password", "$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG")
            .add("first_name", "")
            .add("last_name", "System")
            .add("email", "system@localhost")
            .add("activated", "true")
            .add("lang_key", "en")
            .add("created_by", "system")
            .add("created_date", new Date())
            .add("authorities", authoritiesAdminAndUser)
            .get()
        );
        usersCollection.insert(BasicDBObjectBuilder.start()
            .add("_id", "user-1")
            .add("login", "anonymousUser")
            .add("password", "$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO")
            .add("first_name", "Anonymous")
            .add("last_name", "User")
            .add("email", "anonymous@localhost")
            .add("activated", "true")
            .add("lang_key", "en")
            .add("created_by", "system")
            .add("created_date", new Date())
            .add("authorities", new Map[]{})
            .get()
        );
        usersCollection.insert(BasicDBObjectBuilder.start()
            .add("_id", "user-2")
            .add("login", "admin")
            .add("password", "$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC")
            .add("first_name", "admin")
            .add("last_name", "Administrator")
            .add("email", "admin@localhost")
            .add("activated", "true")
            .add("lang_key", "en")
            .add("created_by", "system")
            .add("created_date", new Date())
            .add("authorities", authoritiesAdminAndUser)
            .get()
        );
        usersCollection.insert(BasicDBObjectBuilder.start()
            .add("_id", "user-3")
            .add("login", "user")
            .add("password", "$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K")
            .add("first_name", "")
            .add("last_name", "User")
            .add("email", "user@localhost")
            .add("activated", "true")
            .add("lang_key", "en")
            .add("created_by", "system")
            .add("created_date", new Date())
            .add("authorities", authoritiesUser)
            .get()
        );
        usersCollection.insert(BasicDBObjectBuilder.start()
            .add("_id", "user-4")
            .add("login", "supervisor")
            .add("password", "$2a$10$n7KQBCLKZ0NI7AFnb8MGr.6xlFq5Nhr8BdbGD2DDCRQgEmvVGXlUy")
            .add("first_name", "")
            .add("last_name", "Supervisor")
            .add("email", "supervisor@localhost")
            .add("activated", "true")
            .add("lang_key", "en")
            .add("created_by", "system")
            .add("created_date", new Date())
            .add("authorities", authoritiesManager)
            .get()
        );
    }
    
    @ChangeSet(order = "03", author = "initiator", id = "03-addSocialUserConnection")
    public void addSocialUserConnection(DB db) {
        DBCollection socialUserConnectionCollection = db.getCollection("jhi_social_user_connection");
        socialUserConnectionCollection.createIndex(BasicDBObjectBuilder
                .start("user_id", 1)
                .add("provider_id", 1)
                .add("provider_user_id", 1)
                .get(),
            "user-prov-provusr-idx", true);
    }
    
    @ChangeSet(order = "04", author = "initiator", id = "04-addCompanies")
    public void addCompanies(DB db) {
        DBCollection companiesCollection = db.getCollection("company");
        companiesCollection.createIndex("name");
        companiesCollection.insert(BasicDBObjectBuilder.start()
            .add("_id", "company-1")
            .add("manager", "supervisor")
            .add("name", "TG24H")
            .add("description", "TG24H company description")
            .add("active", true)
            .add("email", "tg24h@tg24h.com")
            .add("phone_number", "91 123 45 67")
            .add("web", "www.tg24h.com")
            .get()
        );
    }
    
    @ChangeSet(order = "05", author = "initiator", id = "05-addCategories")
    public void addCategories(DB db) {
        DBCollection categoriesCollection = db.getCollection("category");
        categoriesCollection.createIndex("name");
        categoriesCollection.insert(BasicDBObjectBuilder.start()
                .add("_id", "category-1")
                .add("code", "01")
                .add("name", "particulares")
                .add("description", "Categoría para particulares")
                .get()
            );
        categoriesCollection.insert(BasicDBObjectBuilder.start()
                .add("_id", "category-2")
                .add("code", "02")
                .add("name", "autónomos")
                .add("description", "Categoría para autónomos")
                .get()
            );
        categoriesCollection.insert(BasicDBObjectBuilder.start()
                .add("_id", "category-3")
                .add("code", "03")
                .add("name", "empresas")
                .add("description", "Categoría para empresas")
                .get()
            );
    }
}
