/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package KaraokeApp.service;

import KaraokeApp.model.Conn;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author 84374
 */
public class BuildBase {
    

    public BuildBase() {
        try {
            Connection conn = DriverManager.getConnection(Conn.getInstance().url);
            String DB ="CREATE DATABASE KARAOKEDATA;GO\n" +
                       "USE Arms2";
            Statement stmt = conn.createStatement();
            String TYPEROOM ="CREATE TABLE TYPEROOM(\n" +
                            "	 NAMETYPE VARCHAR(50),\n" +
                            "	 PRICEONHOUR VARCHAR(20),\n" +
                            "	 PRIMARY KEY(NAMETYPE)\n" +
                            "	)";
            String ROOMNO ="CREATE TABLE ROOMNO(\n" +
                            "	NUMORDER INT IDENTITY(1,1) NOT NULL,\n" +
                            "	ROOMNO VARCHAR(50),\n" +
                            "	TYPEROOM VARCHAR(50),\n" +
                            "	PRIMARY KEY(ROOMNO),\n" +
                            "	FOREIGN KEY(TYPEROOM) REFERENCES dbo.TYPEROOM(NAMETYPE)\n" +
                            ")";
            String DISHOFFOOD ="CREATE TABLE DISHOFFOOD(\n" +
                                "	NUMORDER INT IDENTITY(1,1) NOT NULL,\n" +
                                "    IDFOOD AS 'F'+CAST(NUMORDER AS VARCHAR(30)) PERSISTED NOT NULL,\n" +
                                "	NAMEOFDISH VARCHAR(50),\n" +
                                "	UNIT VARCHAR(20),\n" +
                                "	UNITPRICE VARCHAR(20),\n" +
                                "	PRIMARY KEY(IDFOOD)\n" +
                                ")";
            String CUSTOMER ="CREATE TABLE CUSTOMER(\n" +
                            "	  NUMORDER INT IDENTITY(1,1) NOT NULL,\n" +
                            "	  FULLNAME VARCHAR(100),\n" +
                            "	  IDBILL AS 'HD'+CAST(NUMORDER AS VARCHAR(30)) PERSISTED NOT NULL,\n" +
                            "	  DOB VARCHAR(20),\n" +
                            "	  GENDER VARCHAR(20),\n" +
                            "	  TYPEROOM VARCHAR(50),\n" +
                            "	  ROOMNO VARCHAR(50),\n" +
                            "	  HOURIN VARCHAR(5),\n" +
                            "	  MINUTEIN VARCHAR(5),\n" +
                            "	  HOUROUT VARCHAR(5),\n" +
                            "	  MINUTEOUT VARCHAR(5),\n" +
                            "	  DATEIN VARCHAR(20),\n" +
                            "	  MONEYTOOK VARCHAR(20),\n" +
                            "	  PRIMARY KEY(IDBILL))";
            String FOODCONSUMED ="CREATE TABLE FOODCONSUMED(\n" +
                                "NUMORDER INT IDENTITY(1,1) NOT NULL,\n" +
                                "	  IDBILL VARCHAR(32),\n" +
                                "	  IDFOOD VARCHAR(31),\n" +
                                "	  AMOUNT VARCHAR(10),\n" +
                                "	  FOREIGN KEY(IDBILL) REFERENCES CUSTOMER(IDBILL),\n" +
                                "	  FOREIGN KEY(IDFOOD) REFERENCES dbo.DISHOFFOOD(IDFOOD) );";
            
            String insertToTypeRoom ="INSERT INTO dbo.TYPEROOM\n" +
                                     "VALUES	\n" +
                                     "('PHONG VIP','1000000.0'),\n" +
                                     "('PHONG TRUNG','700000.0'),\n" +
                                     "('PHONG THUONG','500000.0');";
            String insertToRoomNo="INSERT INTO dbo.ROOMNO\n" +
                                  "VALUES\n" +
                                  "('PHONG 1','PHONG VIP'),\n" +
                                  "('PHONG 2','PHONG TRUNG'),\n" +
                                  "('PHONG 3','PHONG THUONG'),\n" +
                                  "('PHONG 4','PHONG TRUNG'),\n" +
                                  "('PHONG 5','PHONG TRUNG'),\n" +
                                  "('PHONG 6','PHONG TRUNG'),\n" +
                                  "('PHONG 7','PHONG THUONG'),\n" +
                                  "('PHONG 8','PHONG TRUNG'),\n" +
                                  "('PHONG 9','PHONG VIP'),\n" +
                                  "('PHONG 10','PHONG VIP');";
            String insertToMenu = "INSERT INTO dbo.DISHOFFOOD\n" +
                                  "VALUES\n" +
                                  "('Snack','Pack','20000.0'),\n" +
                                  "('Fruit','Dish','40000.0'),\n" +
                                  "('Beer','Bottle','30000.0'),\n" +
                                  "('Coca','Bottle','25000.0');";
            stmt.executeUpdate(DB+TYPEROOM+ROOMNO+DISHOFFOOD+CUSTOMER+FOODCONSUMED+insertToTypeRoom+insertToRoomNo+insertToMenu);
            stmt.close();
            System.out.println("Build dataBase success !");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("data already to use");
        }
        
    }
}
