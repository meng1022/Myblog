# Myblog - A personal blog
A self-developed personal blog to show the world my thoughts and ideas while learning and doing projects.

## Table of Contents
* [Technologies](#technologies)
* [Launch](#launch)
* [Examples](#examples)
* [Stage](#stage)

## Technologies
* For the front-end, I use HTML as pages, and BootStrap providing outlooks.

* For back-end, I choose Spring as fundemental framework.

* As for database, ORM framework: hibernate; Persistence framework: Mybatis; Connection pool: HikariCP; Database: Mysql.

## Launch
### 1. Prepare Database, you need to run the db.sql in the repository to generate the database table structures. Administrator has a userid of value 1. Change the database username and password to your owns in src/main/resources/jdbc.properties

### 2. Import the project as Maven project, run maven install to install necessary dependencies.

### 3.Run AppConfig to start the application. The application will run on localhost:8080/user/ by default, you can change the port at AppConfig.main()

## Examples

### 1. This is the homepage
![homepage](./img/homepage.png)

### 2. This is the article list
![articles](./img/articles.png)

### 3. This is one of the project page
![module](./img/projects.png)


### More pages will be found when you run our application

## Stage
* This blog is under development, most of the basic functions have been implemented.

* Some features is being developed now, like commenting, sending email, and direct messages within the blog.