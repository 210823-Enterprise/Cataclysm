# Cataclysm

## Project Description
A java based ORM for simplifying connecting to and from an SQL database without the need for SQL or connection management. 

## Technologies Used

* PostgreSQL - version 42.2.12  
* Java - version 8.0  
* Apache commons - version 2.1  

## Features

List of features ready and TODOs for future development  
* Easy to use and straightforward user API.  
* No need for SQL, HQL, or any databse specific language.  
* Straightforward and simple Annotation based for ease of use. 
* Easy configuration
* Built in cache

To-do list: [`for future iterations`]
* Mapping of join columns inside of entities.     
* Join queries 
* Replace Hibernate as #1 Java ORM
* etc...

## Getting Started  
Currently project must be included as local dependency. to do so:
```shell
  git clone https://github.com/210823-Enterprise/team1-project1.git
  cd team1-project1
  mvn install
```
Next, place the following inside your project pom.xml file:
```XML
  <dependency>
    <groupId>com.revature</groupId>
    <artifactId>project-1-team1</artifactId>
    <version>1.0-SNAPSHOT</version>
  </dependency>

```


Finally, inside your project structure you need a application.proprties file. 
 (typically located src/main/resources/)
 ``` 
  dbUrl=path/to/database
  username=username/of/database
  password=password/of/database  
  ```
  
## Usage  
  ### Annotating classes  
  All classes which represent objects in database must be annotated.
   - #### @Entity(tableName = "table_name)  
      - Indicates that this class is associated with table 'table_name'  
   - #### @Column(columnName = "column_name)  
      - Indicates that the Annotated field is a column in the table with the name 'column_name'  
   - #### @Id(columnName = "id", isSerial=true)  
      - Indicates that the anotated field is a Primary Key with the name 'id' and serial as the type.  
   - #### @ForeignKey(columnName = "user_id", tableReference = "user_table", columnReference = "id")  
      - Indicates that the anotated field is a Foreign Key with the name 'user_id' and a reference to table "user_table" and column "id". 
   - #### @Nullable(isNullable = false) 
      - Indicates that the annotated field is set to NULL.
   - #### @Unique(isUnique = true) 
      - Indicates that the annotated field is set to UNIQUE.

  ### Configuration Class/Method
  #### Run these commands at the start of the app.
  ```
  Configuration cfg = new Configuration();
  cfg.addAnnotatedClass(User.class);
  cfg.addAnnotatedClass(Vehicle.class);
  cfg.init();
  ```

  ### User API  
  
  - #### `Cataclysm cc = new Cataclysm();`  
     - Create a new instance of Cataclysm which allows API usage  
  - #### `public HashMap<String, HashSet<Object>> getCache()`  
     - returns the cache as a HashMap.  
  - #### `public int insert(Object obj)`  
     - Inserts the given object in the databse.   
  - #### `public int update(Object obj)`  
     - Updates the given object in the databse. Update columns is a comma seperated lsit fo all columns in the onject which need to be updated  
  - #### `public boolean deleteById(Class clazz, int id)`  
     - Removes the given object from the database given it's class and ID.  
  - #### `public boolean dropTable(Class clazz)`  
     - Will drop a table given it's class.  
  - #### `public <T> Object selectRowWithId(Class clazz, int id)`  
  - #### `public <T> List<T> selectAllFromTable(Class clazz)`
  - #### `boolean enableAutoCommit(Connection conn)` 
     - set auto commit to true.  
  - #### `boolean diableAutoCommit(Connection conn)` 
     - set auto commit to false. 
  - #### `boolean commitChanges(Connection conn)` 
     - commit the changes in a transaction. 
  - #### `boolean abortChanges(Connection conn)` 
     - will rollback any changes made during a transaction. 
  - #### `boolean returnToSavepoint(Connection conn, String name)` 
     - return to a set savepoint in a transaction 
  - #### `boolean setSavepoint(Connection conn, String name)` 
     - create a save point within a transaction 
  - #### `boolean releaseSavepoint(Connection conn, String name)` 
     - release a given savepoint 

## Custom SQL Statements

Your SQL statements are all covered through the our CustomSQLStatement builder. You are able to create complex statements by stating the type of statement you want to create and inputting the variables involved. An example would be:

```java
Select select = new Select("user_table")
				.column("username")
				.column("password")
				.where("id = 1");
        
        cataclysm.customSelect(select, User.class);
```


This produces the SQL string `SELECT username, password FROM user_table WHERE age = 25
`. 

You are able to do so with SELECT, CREATE, INSERT, UPDATE and DELETE statements.

You are able to take advantage of the following methods after creating the corresponding objects to insert:

  - #### `public boolean customCreate(Create create)`
  - #### `public int customInsert(Insert insert)`
  - #### `public <T> Object customSelect(Select select, Class clazz)`
  - #### `public boolean customUpdate(Update update) `
  - #### `public boolean customDelete(Delete delete) `
