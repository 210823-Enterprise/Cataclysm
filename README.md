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

To-do list: [`for future iterations`]
* Mapping of join columns inside of entities.    
* Implement of aggregate functions.  
* Allow ORM to build table based on Annotations in Entities.  
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

  ### Configuration
    -#### Create a config Class/Method to prepare your tables
    ```
    Configuration cfg = new Configuration();
		cfg.addAnnotatedClass(User.class);
		cfg.addAnnotatedClass(Vehicle.class);
		
		cfg.init();
    ```
  

  ### User API  
  
## ============NOTHING BELOW THIS HAS BEEN CHANGED============
  
  - #### `public static Something getInstance()`  
     - returns the singleton instance of the class. It is the starting point to calling any of the below methods.  
  - #### `public HashMap<Class<?>, HashSet<Object>> getCache()`  
     - returns the cache as a HashMap.  
  - #### `public boolean addClass(final Class<?> clazz)`  
     - Adds a class to the ORM. This is the method to use to declare a Class is an object inside of the database.  
  - #### `public boolean UpdateObjectInDB(final Object obj,final String update_columns)`  
     - Updates the given object in the databse. Update columns is a comma seperated lsit fo all columns in the onject which need to be updated  
  - #### `public boolean removeObjectFromDB(final Object obj)`  
     - Removes the given object from the database.  
  - #### `public boolean addObjectToDB(final Object obj)`  
     - Adds the given object to the database.  
  - #### `public Optional<List<Object>> getListObjectFromDB(final Class <?> clazz, final String columns, final String conditions)`  
  - #### `public Optional<List<Object>> getListObjectFromDB(final Class <?> clazz, final String columns, final String conditions,final String operators)`  
  - #### `public Optional<List<Object>> getListObjectFromDB(final Class<?> clazz)`  
     - Gets a list of all objects in the database which match the included search criteria  
        - columns - comma seperated list of columns to search by.  
        - conditions - coma seperated list the values the columns should match to.  
        - operators - comma seperated list of operators to apply to columns (AND/OR) in order that they should be applied.  
  - #### `public void beginCommit()`  
     - begin databse commit.  
  - #### `public void Rollback()`  
     - Rollback to previous commit.  
  - #### `public void Rollback(final String name)`  
     - Rollback to previous commit with given name.  
  - #### `public void setSavepoint(final String name)`  
     - Set a savepoint with the given name.  
  - #### `public void ReleaseSavepoint(final String name)`  
     - Release the savepoint with the given name.  
  - #### `public void enableAutoCommit()`  
     - Enable auto commits on the database.  
  - #### `public void setTransaction()`  
     - Start a transaction block.  
  - #### `public void addAllFromDBToCache(final Class<?> clazz)`  
     - Adds all objects currently in the databse of the given clas type to the cache.  



## License

This project uses the following license: [GNU Public License 3.0](https://www.gnu.org/licenses/gpl-3.0.en.html).
