Task tracker application run instruction:

1. Create new `task-tracker-sample` database on your mysql instance.
2. Import src/main/resource/db/migration/task-tracker-sample.sql into database.
3. Open src/main/webapp/WEB-INF/applicationContext.xml
4. Update properties `jdbcUrl`, `user`, `password` in `dataSource` bean with your database credentionals.
5. Run "mvn clean tomcat7:run" from task-tracker-sample folder. This will start tomcat with deployed application.
6. You can access ui application module at http://localhost:8080/task-tracker/