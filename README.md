# Online library

16. Система Библиотека. Создайте Каталог, по которому можно искать по:
• Автору (одному из группы).

• Названию книги или её фрагменте.
• Одному из ключевых слов книги (атрибут книги).
Каталог книг заполняет Администратор, добавляя и изменяя/удаляя их.
Каждая книга должна иметь адрес (место на полке) или читателя. Читатель
чтобы взять книгу регистрируется, оставляя э-мейл и номер телефона. Книга
может быть взята у Администратора в библиотеке на время не более месяца,
только в случае если книга доступна в библиотеке. Администратор должен
иметь страницу где отражаются взятые книги и читатели, которые
пользуются книгой.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

To run this project you will need:

```
MySQL Server 5.7
Tomcat 8.5.28
JDK 8
```
You can download them from:

```
https://dev.mysql.com/downloads/
https://tomcat.apache.org/security-8.html
http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
```

### Installing

To build up project first create needed db and tables:

```
CREATE DATABASE library;
```
Book Table:
```
CREATE TABLE `book` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT,
   `name` varchar(45) NOT NULL,
   `content` longblob,
   `page_count` int(11) NOT NULL,
   `isbn` varchar(100) NOT NULL,
   `genre_id` bigint(20) NOT NULL,
   `author_id` bigint(20) NOT NULL,
   `publish_year` bigint(20) NOT NULL,
   `publisher_id` bigint(20) NOT NULL,
   `image` longblob,
   `user_name` varchar(45) DEFAULT NULL,
   `take_date` timestamp(1) NULL DEFAULT NULL,
   `expire_date` timestamp(1) NULL DEFAULT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `id_UNIQUE` (`id`),
   UNIQUE KEY `isbn_UNIQUE` (`isbn`),
   KEY `fk_author_idx` (`author_id`),
   KEY `fk_genre_idx` (`genre_id`),
   KEY `fk_publisher_idx` (`publisher_id`),
   KEY `fk_user_idx` (`user_name`),
   CONSTRAINT `fk_author` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`) ON UPDATE CASCADE,
   CONSTRAINT `fk_genre` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`id`) ON UPDATE CASCADE,
   CONSTRAINT `fk_publisher` FOREIGN KEY (`publisher_id`) REFERENCES `publisher` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
   CONSTRAINT `fk_user` FOREIGN KEY (`user_name`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
 ) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8
```
Author Table:

```
CREATE TABLE `author` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT,
   `name` varchar(300) NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `author_fio_uindex` (`name`)
 ) ENGINE=InnoDB AUTO_INCREMENT=153 DEFAULT CHARSET=utf8
 ```
 
 Genre Table:
 
  ```
 CREATE TABLE `genre` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT,
   `name` varchar(100) NOT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=158 DEFAULT CHARSET=utf8
 ```
 
Users table:
 ```
CREATE TABLE `users` (
   `id` varchar(15) NOT NULL,
   `user_pass` varchar(45) NOT NULL,
   `email` varchar(45) DEFAULT NULL,
   `phone_number` varchar(45) DEFAULT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
 ```
 And user roles table:
  ```
 CREATE TABLE `user_roles` (
   `id` varchar(15) NOT NULL,
   `role_name` varchar(15) NOT NULL,
   PRIMARY KEY (`id`,`role_name`),
   CONSTRAINT `fk_users` FOREIGN KEY (`id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
  ```
  
  Then go to root project folder and build up project with maven command:
    ```
    mvn package  
    ```
 
## Running the tests

To run test use:
    ```
    mvn test  
    ```

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management
