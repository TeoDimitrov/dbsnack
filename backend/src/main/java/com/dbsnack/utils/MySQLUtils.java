package com.dbsnack.utils;

public class MySQLUtils {

    public static final String URL = "jdbc:mysql://localhost:3306/?useSSL=false&autoReconnect=true&allowMultiQueries=true";

    public static final String USER = "judge";

    public static final String PASSWORD = "1234";


    public static final String getCreateDatabaseStatement(String databaseName) {
        return "CREATE DATABASE IF NOT EXISTS " + databaseName;
    }

    public static final String getSelectDatabaseStatement(String databaseName) {
        return "USE " + databaseName + ";";
    }

    public static final String getCreateTablesStatement() {
        return
                "CREATE TABLE companies(\n" +
                        "id int primary key auto_increment,\n" +
                        "name varchar(50),\n" +
                        "reg_number varchar(12)\n" +
                        ");\n" +
                        "CREATE TABLE cookies(\n" +
                        "id int primary key auto_increment,\n" +
                        "name varchar(50),\n" +
                        "calories int,\n" +
                        "price decimal(4,2),\n" +
                        "is_vegan bit,\n" +
                        "is_produced bit,\n" +
                        "company_id int,\n" +
                        "foreign key (company_id) references companies(id)\n" +
                        ");\n" +
                        "CREATE TABLE ingredients(\n" +
                        "id int primary key auto_increment,\n" +
                        "name varchar(50),\n" +
                        "price decimal(4,2),\n" +
                        "measurement varchar(50)\n" +
                        ");\n" +
                        "CREATE TABLE cookies_ingredients(\n" +
                        "cookie_id int,\n" +
                        "ingredient_id int,\n" +
                        "quantity int,\n" +
                        "primary key(cookie_id, ingredient_id),\n" +
                        "foreign key(cookie_id) references cookies(id),\n" +
                        "foreign key(ingredient_id) references ingredients(id)\n" +
                        ");\n" +
                        "INSERT INTO companies(name, reg_number)\n" +
                        "VALUES\n" +
                        "('Cookie Monsters', '1D2R4GR004CM'),\n" +
                        "('King Cookies', '6F5Q9BN981KC'),\n" +
                        "('Sugar Lords', '3E1H4XP620SL'),\n" +
                        "('Vegan Mania', '8T6W0KS654VM'),\n" +
                        "('Cocoa Treats', '2K2V5JL593CT');\n" +
                        "INSERT INTO cookies(name, calories, price, is_vegan, is_produced, company_id)\n" +
                        "VALUES \n" +
                        "('Minty Frog', 51, 1.22, 0, 1, 1),\n" +
                        "('Chocolate Heaven', 62, 1.51, 0, 1, 1),\n" +
                        "('Vanilla Spice', 46, 1.34, 0, 1, 1),\n" +
                        "('Mega Honey', 57, 1.72, 0, 1, 1),\n" +
                        "('Strawberry Princess', 50, 1.16, 0, 0, 1),\n" +
                        "('Love Peach', 36, 1.10, 0, 1, 2),\n" +
                        "('Cocoa Crumb', 25, 0.95, 0, 1, 2),\n" +
                        "('Crazy Sour', 33, 1.05, 0, 1, 2),\n" +
                        "('Sweet Apple', 44, 1.28, 0, 0, 2),\n" +
                        "('Ultra Cookie', 60, 1.45, 0, 1, 3),\n" +
                        "('Vanilla Cream', 67, 1.32, 0, 1, 3),\n" +
                        "('Banana Love', 70, 1.57, 0, 1, 3),\n" +
                        "('Tasty Dark', 77, 1.77, 0, 1, 3),\n" +
                        "('Fresh Nuts', 20, 2.17, 1, 1, 4),\n" +
                        "('Bio Pleasure', 22, 2.37, 1, 1, 4),\n" +
                        "('Summer Fruits', 21, 2.42, 1, 1, 4),\n" +
                        "('Nature Choco', 24, 2.24, 1, 0, 4),\n" +
                        "('Small Berries', 32, 1.34, 1, 1, 5),\n" +
                        "('Dark Friend', 40, 1.32, 0, 1, 5),\n" +
                        "('Light Friend', 50, 1.42, 0, 1, 5),\n" +
                        "('Smooth White', 44, 1.02, 0, 1, 5),\n" +
                        "('Bad Company', 52, 1.42, 0, 0, 5),\n" +
                        "('Main Cream', 48, 1.32, 0, 0, NULL),\n" +
                        "('Mango Lord', 32, 1.61, 0, 0, NULL),\n" +
                        "('Ginger Bliss', 35, 1.46, 1, 0, NULL),\n" +
                        "('Colorado Girl', 66, 1.38, 0, 0, NULL);\n" +
                        "\n" +
                        "INSERT INTO ingredients(name, price, measurement)\n" +
                        "VALUES\n" +
                        "('Mint', 0.22, '2 grams'),\n" +
                        "('Apple', 0.27, '20 grams'),\n" +
                        "('Strawberry', 0.42, '18 grams'),\n" +
                        "('Peach', 0.32, '23 grams'),\n" +
                        "('Blueberry', 0.42, '18 grams'),\n" +
                        "('Grapefruit', 0.33, '20 grams'),\n" +
                        "('Nuts', 0.55, '10 grams'),\n" +
                        "('Grape', 0.27, '15 grams'),\n" +
                        "('Banana', 0.23, '19 grams'),\n" +
                        "('Vanilla', 0.02, '5 grams'),\n" +
                        "('Light Chocolate', 0.22, '26 grams'),\n" +
                        "('Dark Chocolate', 0.25, '26 grams'),\n" +
                        "('White Chocolate', 0.23, '26 grams'),\n" +
                        "('Flour', 0.10, '40 grams'),\n" +
                        "('Honey', 0.23, '10 grams'),\n" +
                        "('Sugar', 0.16, '24 grams'),\n" +
                        "('Salt', 0.05, '5 grams'),\n" +
                        "('Milk', 0.11, '20 milliliters');\n" +
                        "INSERT INTO cookies_ingredients(cookie_id, ingredient_id, quantity)\n" +
                        "VALUES\n" +
                        "(1, 14, 1),\n" +
                        "(1, 12, 2),\n" +
                        "(1, 1, 3),\n" +
                        "(2, 14, 1),\n" +
                        "(2, 11, 1),\n" +
                        "(2, 12, 1),\n" +
                        "(2, 13, 2),\n" +
                        "(3, 14, 1),\n" +
                        "(3, 6, 2),\n" +
                        "(3, 10, 3),\n" +
                        "(3, 17, 2),\n" +
                        "(4, 14, 1),\n" +
                        "(4, 15, 4),\n" +
                        "(4, 16, 4),\n" +
                        "(5, 14, 1),\n" +
                        "(5, 5, 2),\n" +
                        "(5, 9, 1),\n" +
                        "(5, 18, 1),\n" +
                        "(6, 14, 1),\n" +
                        "(6, 4, 3),\n" +
                        "(6, 16, 2),\n" +
                        "(6, 10, 1),\n" +
                        "(7, 14, 1),\n" +
                        "(7, 16, 1),\n" +
                        "(7, 7, 1),\n" +
                        "(7, 1, 1),\n" +
                        "(8, 14, 1),\n" +
                        "(8, 6, 3),\n" +
                        "(8, 17, 1),\n" +
                        "(9, 14, 1),\n" +
                        "(9, 2, 4),\n" +
                        "(9, 11, 2),\n" +
                        "(9, 16, 1),\n" +
                        "(10, 14, 2),\n" +
                        "(10, 13, 5),\n" +
                        "(11, 14, 1),\n" +
                        "(11, 10, 3),\n" +
                        "(11, 9, 2),\n" +
                        "(12, 14, 1),\n" +
                        "(12, 10, 4),\n" +
                        "(12, 9, 1),\n" +
                        "(13, 14, 1),\n" +
                        "(13, 12, 3),\n" +
                        "(13, 16, 3),\n" +
                        "(14, 14, 1),\n" +
                        "(14, 2, 1),\n" +
                        "(14, 4, 1),\n" +
                        "(14, 7, 2),\n" +
                        "(15, 14, 1),\n" +
                        "(15, 1, 2),\n" +
                        "(15, 2, 3),\n" +
                        "(15, 3, 3),\n" +
                        "(16, 14, 1),\n" +
                        "(16, 3, 1),\n" +
                        "(16, 4, 2),\n" +
                        "(16, 5, 1),\n" +
                        "(17, 14, 1),\n" +
                        "(17, 11, 1),\n" +
                        "(17, 12, 1),\n" +
                        "(17, 13, 1),\n" +
                        "(18, 14, 1),\n" +
                        "(18, 3, 3),\n" +
                        "(18, 5, 3),\n" +
                        "(18, 18, 1),\n" +
                        "(19, 14, 1),\n" +
                        "(19, 12, 5),\n" +
                        "(20, 14, 1),\n" +
                        "(20, 11, 5),\n" +
                        "(21, 14, 1),\n" +
                        "(21, 13, 4),\n" +
                        "(21, 18, 1),\n" +
                        "(22, 14, 1),\n" +
                        "(22, 6, 4),\n" +
                        "(22, 15, 2),\n" +
                        "(22, 9, 1);";
    }

    public static final String getCreateUserStatement(String username) {
        return "CREATE USER IF NOT EXISTS " +
                "'" + username + "'" +
                "@'localhost';";
    }


    public static final String getGrantUserPrivilegesStatement(String username, String databaseName) {
        return "GRANT SELECT, INSERT, UPDATE, DELETE, UPDATE, CREATE, DROP " +
                "ON " + databaseName + ".* " +
                "TO '" + username + "'@'localhost';";
    }

    public static final String getSchemaMetaDataStatement() {
        return "SELECT TABLE_NAME, COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS\n" +
                "WHERE TABLE_SCHEMA = (SELECT DATABASE())";
    }

    public static final String getDropDatabaseStatement(String databaseName) {
        return "DROP DATABASE IF EXISTS " + databaseName;
    }

    public static final String getDropCredentialsStatement(String username) {
        return "DROP USER IF EXISTS '" + username + "'@'localhost'";
    }
}
