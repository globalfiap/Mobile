package com.projeto.mobileglobal.database

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DatabaseConnection {
    private const val URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl"
    private const val USER = "rm552692"
    private const val PASSWORD = "200899"

    fun connect(): Connection? {
        return try {
            // Carregar o driver JDBC
            Class.forName("oracle.jdbc.OracleDriver")
            DriverManager.getConnection(URL, USER, PASSWORD)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            null
        } catch (e: SQLException) {
            e.printStackTrace()
            null
        }
    }
}