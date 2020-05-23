/*
 *  Author: Steven Balagtas
 *  This script will create the EBDM database inside your mysql/ mariadb database server.
 *
 *  To execute use the following command in your command line: "mysql -u root -p < createEBDMdb.sql"
 *  Or if executing from within the mysql command line use: "source 'full path to the script'"
 */

CREATE DATABASE IF NOT EXISTS EBDM;