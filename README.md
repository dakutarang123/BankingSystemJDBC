# 🏦 BankingSystemJDBC

A **console-based Banking System** built using **Java + JDBC + MySQL**.  
This project demonstrates how to connect Java applications with a database and perform basic banking operations such as **Create Account, Deposit, Withdraw, and Check Balance**.

---

## ✨ Features
- Create new bank accounts
- Deposit money into an account
- Withdraw money from an account
- Check account balance
- Data stored in **MySQL database** for persistence

---

## 🛠️ Technologies Used
- **Java** (Core + JDBC)
- **MySQL** (Database)
- **JDBC Driver** (MySQL Connector)
- **Console-based UI**

---

## 📂 Database Setup
Database: `bank`  
Table: `account`  

```sql
CREATE DATABASE bank;

USE bank;

CREATE TABLE account (
    acco_no INT PRIMARY KEY,
    name VARCHAR(100),
    balance DOUBLE
);
