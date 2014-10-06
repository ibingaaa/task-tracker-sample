CREATE DATABASE  IF NOT EXISTS `task-tracker-sample` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `task-tracker-sample`;
-- MySQL dump 10.13  Distrib 5.6.10, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: task-tracker-sample
-- ------------------------------------------------------
-- Server version	5.6.10-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_time` datetime NOT NULL,
  `text` text NOT NULL,
  `creator_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_comment_creator_idx` (`creator_id`),
  CONSTRAINT `fk_comment_creator` FOREIGN KEY (`creator_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,'2014-10-06 18:14:49','It\'s also happening for some non-Java files. It\'s difficult to determine what the commonality is.',1),(2,'2014-10-06 18:18:22','When I followed that sequence for a non-java file, after #3 and #4, the modified .txt file was showing as New but when I switched focus away and then back to IDEA, it updated to modified. That\'s not happening for .java files though.\n\nI tried this with 3 different files: Agent.java (normal java source), readme.txt (in a content root but not marked as source) and AutoTestAll.java (test java source).',2),(3,'2014-10-06 18:19:59','Suggestion for last paragraph: configurable option for reopening project?',2),(4,'2014-10-06 18:20:15','To add to this, focus is stolen from other applications, not just from other instances of Idea.',1),(5,'2014-10-06 18:20:30','This is a major annoyance for me, as for whatever reason, when it is launching, and during the time when the splash screen is shown, IDEA shows the \"Compacting Indices\" dialog a large number of times. Each time it does, it steals the focus away from whatever I am doing. This is infuriating. I am trying to go on with other tasks while IDEA takes it\'s sweet time to load, and to have. it. continually. steal. focus. from. that. other. task. is. very. annoying.',2),(6,'2014-10-06 18:21:08','The first version of IntelliJ IDEA was released in January 2001',1),(7,'2014-10-06 18:21:25','The first version of IntelliJ IDEA was released in January 2001',2),(8,'2014-10-06 18:22:07','Hibernate provides an SQL inspired language called Hibernate Query Language (HQL) which allows SQL-like queries to be written against Hibernate\'s data objects. Criteria Queries are provided as an object-oriented alternative to HQL. Criteria Query is used to modify the objects and provide the restriction for the objects.',2),(9,'2014-10-06 18:22:41','Hibernate provides transparent persistence for Plain Old Java Objects (POJOs). The only strict requirement for a persistent class is a no-argument constructor, not necessarily public. Proper behavior in some applications also requires special attention to the equals() and hashCode() methods.',1),(10,'2014-10-06 18:23:01','Collections of data objects are typically stored in Java collection objects such as Set and List. Java generics, introduced in Java 5, are supported. Hibernate can be configured to lazy load associated collections. Lazy loading is the default as of Hibernate 3.',1);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_comments`
--

DROP TABLE IF EXISTS `project_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_comments` (
  `project` bigint(20) NOT NULL,
  `comment` bigint(20) NOT NULL,
  UNIQUE KEY `comment_UNIQUE` (`comment`),
  KEY `fk_comment_project_idx` (`project`),
  CONSTRAINT `fk_project_comment` FOREIGN KEY (`comment`) REFERENCES `comments` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_project` FOREIGN KEY (`project`) REFERENCES `projects` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_comments`
--

LOCK TABLES `project_comments` WRITE;
/*!40000 ALTER TABLE `project_comments` DISABLE KEYS */;
INSERT INTO `project_comments` VALUES (2,8),(2,9),(2,10),(4,6),(4,7);
/*!40000 ALTER TABLE `project_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projects`
--

DROP TABLE IF EXISTS `projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projects` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_time` datetime NOT NULL,
  `description` text NOT NULL,
  `name` varchar(100) NOT NULL,
  `text` text NOT NULL,
  `creator_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `fk_project_creator_idx` (`creator_id`),
  CONSTRAINT `fk_project_creator` FOREIGN KEY (`creator_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projects`
--

LOCK TABLES `projects` WRITE;
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
INSERT INTO `projects` VALUES (1,'2014-10-05 18:24:57','The Spring Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications - on any kind of deployment platform. A key element of Spring is infrastructural support at the application level: Spring focuses on the \"plumbing\" of enterprise applications so that teams can focus on application-level business logic, without unnecessary ties to specific deployment environments.','Spring Framework','The Spring Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications - on any kind of deployment platform. A key element of Spring is infrastructural support at the application level: Spring focuses on the \"plumbing\" of enterprise applications so that teams can focus on application-level business logic, without unnecessary ties to specific deployment environments.',1),(2,'2014-10-06 18:03:53','Hibernate ORM enables developers to more easily write applications whose data outlives the application process. As an Object/Relational Mapping (ORM) framework, Hibernate is concerned with data persistence as it applies to relational databases (via JDBC). Unfamilar with the notion of ORM?','Hibernate ORM','Hibernate ORM enables developers to more easily write applications whose data outlives the application process. As an Object/Relational Mapping (ORM) framework, Hibernate is concerned with data persistence as it applies to relational databases (via JDBC). Unfamilar with the notion of ORM?',1),(3,'2014-10-06 18:04:47','Spring Security is a framework that focuses on providing both authentication and authorization to Java applications. Like all Spring projects, the real power of Spring Security is found in how easily it can be extended to meet custom requirements','Spring Security','Spring Security is a framework that focuses on providing both authentication and authorization to Java applications. Like all Spring projects, the real power of Spring Security is found in how easily it can be extended to meet custom requirements',1),(4,'2014-10-06 18:07:17','The Most Intelligent IDE for the Java Platform.\n\nOut of the box, IntelliJ IDEA provides a comprehensive feature set including tools and integrations with the most important modern technologies and frameworks for enterprise and web development with Java, Scala, Groovy and other languages.','IntelliJ IDEA','The Most Intelligent IDE for the Java Platform.\n\nOut of the box, IntelliJ IDEA provides a comprehensive feature set including tools and integrations with the most important modern technologies and frameworks for enterprise and web development with Java, Scala, Groovy and other languages.',1);
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_comments`
--

DROP TABLE IF EXISTS `task_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_comments` (
  `task` bigint(20) NOT NULL,
  `comment` bigint(20) NOT NULL,
  UNIQUE KEY `comment_UNIQUE` (`comment`),
  KEY `fk_comment_task_idx` (`task`),
  CONSTRAINT `fk_task_comment` FOREIGN KEY (`comment`) REFERENCES `comments` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_task` FOREIGN KEY (`task`) REFERENCES `tasks` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_comments`
--

LOCK TABLES `task_comments` WRITE;
/*!40000 ALTER TABLE `task_comments` DISABLE KEYS */;
INSERT INTO `task_comments` VALUES (8,1),(8,2),(10,3),(10,4),(10,5);
/*!40000 ALTER TABLE `task_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tasks`
--

DROP TABLE IF EXISTS `tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tasks` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_time` datetime NOT NULL,
  `description` text NOT NULL,
  `name` varchar(100) NOT NULL,
  `status` varchar(45) NOT NULL DEFAULT 'FREE',
  `creator_id` bigint(20) NOT NULL,
  `developer_id` bigint(20) DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_task_creator_idx` (`creator_id`),
  KEY `fk_task_developer_idx` (`developer_id`),
  KEY `fk_task_project_idx` (`project_id`),
  CONSTRAINT `fk_task_project` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_task_creator` FOREIGN KEY (`creator_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_task_developer` FOREIGN KEY (`developer_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasks`
--

LOCK TABLES `tasks` WRITE;
/*!40000 ALTER TABLE `tasks` DISABLE KEYS */;
INSERT INTO `tasks` VALUES (1,'2014-10-05 18:31:54','When using Spring with transactional data sources, such as Hibernate+JDBC, it\'s very convenient to use declarative transaction support through @Transactional & friends.\nHowever what a lot of people need sooner or later is the \"OpenSessionInView\" functionality. That is, users don\'t want to, or in many cases cannot (easily), finish all database operations within the scope of the method, but rather need to keep a transaction open in the view.\nThe drawback of OpenSessionInView however is that TransactionDefinitions from the annotated methods will not be honoured (as far as I can see, that is). Also, this functionality is not limited to Hibernate itself, I think this is something generic that many transactional implementations will need at some point of time.\nWhat I\'m proposing is to create another transaction definition attribute \"closeSessionAfterView\" or \"keepSessionOpenInView\", and build support for that into AbstractPlatformTransactionManager. Users could register a filter (or maybe Spring could do that in DispatcherServlet automatically) that will not open or configure it\'s own transaction, but rather tell the TransactionManager that it will handle transaction completion. The request is processed as usual, if a transaction is required it\'s opened and configured as usual, but it will not be closed (rolled back/committed) - this is handled by the CloseSessionAfterViewFilter.\nThe advantage is that sessions will be declared & configured where they are required, and users can opt-out of the session-in-view thing on a per method basis. This increases flexibility and locality of concerns. A generic implementation in the transaction support would be beneficial to many underlying transaction managers.\nThis needs some thought on how to handle nested transactions.\nIt\'s possible to implement something like this today - I did for a particular TransactionManager. Basically you implement a filter that tells the transaction manager it will handle transaction rollback/commit. On regular rollback or commit, you just mark the transaction as rollback only (or not), and then continue as usual. Once the view is done and execution reaches the filter, it does the actual closing of the transaction. Problem is that this is somewhat hacky, there is no support for method level configuration (e.g. @Transactional(keepSessionOpenInView=true)), and this should probably be a generic component for SpringTX.','Need generic CloseSessionAfterView / KeepSessionOpenInView functionality','FREE',1,NULL,1),(2,'2014-10-05 18:38:04','Since there are several <spring-form:select> on the page, the rendering is very very slow. As I researched, if I remove the \"exhaustiveCompare\" and \"exhaustiveCollectionCompare\" methods, it will much faster. While, not everyone need exhaustive compare. So I suggest change this class configurable.\n1. Change the methods to instance methods (remove \"static\")\n2. Config it as a bean in application context (e.g. like \"messageSource\"). The default config may enable \"exhaust compare\", while we can change to disable \"exhaust compare\".','The SelectedValueComparator is too slow','DONE',1,2,1),(3,'2014-10-05 20:57:00','The current conversation resolver is another extension point for the conversation manager to easily change the default behavior of storing the currently used conversation id.\nIn a web environment, the current conversation id would most likely be bound to the current window / tab (see SPR-6417) and hence be based on the window management. This makes it possible to run different conversations per browser window and isolating them from each other by default.\nIn a unit-test or batch environment the current conversation could be bound to the current thread to make conversations be available in a non-web environment as well.\nThe current conversation id is stored in a ThreadLocal and available through a ConversationContextHolder which itself will use the conversation resolver to determine the current conversation id. In a web environment, this resolver could be done by using the a window scoped bean to hold the current conversation id or directly do it by combining the current window id and the conversation id. See LocaleResolver and LocaleContextHolder to apply the same pattern for the ConversationContext and ConversationContextHolder.','Current Conversation Resolver','WORK',2,2,1),(4,'2014-10-06 18:09:42','I just ran into a minor issue with the list() function.\nI have a projection and in this case I want to transform the list to a map.\nSo, I\'m not manipulating the tuple, I\'m just converting the resultlist. I found that this is not possible because transformList is ignored in case of a projection.\nThis seems arbitrary to me and doesn\'t make sense to me.','ResultTransformer ignores transformList on tuples','WORK',1,2,2),(5,'2014-10-06 18:10:21','We are using Hibernate 4.x to achieve multi-tenancy with separate DATABASE per tenant.\nWe are using the increment strategy provided by Hibernate for ID generation.\nThe ID generation doesn\'t happen specific to a tenant.\nIs there any way we can restrict the ID generation specific to a tenant, instead of getting from a common cache.\nAnother issue I have seen is on Server re-start.\nAfter server restart, if the first SQL operation happens for Tenant 1, then Hibernate starts caching the IDs referenced from the Ids from Tenant 1 database.\nThis can create conflicts, if the subsequent operation is performed on other Tenant and it already has the Id value provided by Hibernate cache.\nI could have used Sequences for generating the IDs, but MYSQL doesn\'t support Sequences.\nIs there any way to avoid getting the IDs from the common cache?','Hibernate 4.x Multi-tenancy ID generation issues with MYSQL','FREE',1,NULL,2),(6,'2014-10-06 18:11:13','The existing PerfTest should be extended with a test for running queries. There also should be a way to run the test in a multi-threaded manner and log CRUD ops / Query ops per second.','Extend PerfTest to cover queries as well','FREE',1,NULL,2),(7,'2014-10-06 18:13:17','It should be possible to set up infrastructure for running a service (or service method) with a particular SecurityContext. This could be done either to satisfy internal security constraints within the application or to allow the propagation of credentials for invoking a remote service (e.g. s3), which would also require protocol-specific integration. This would thus be a superset of the existing run-as functionality which would be superseded and removed from the security interceptor. It would also supersede functionality like the WebSphere2SpringSecurityPropagationInterceptor.','More flexible run-as authentication and security context propagation support','FREE',1,NULL,3),(8,'2014-10-06 18:14:18','I\'ve started having a weird problem over the last few days. I have a project in IDEA 13.1.4 under Git version control. When I edit a file, IDEA marks it as new in the editor tab (green filename) and doesn\'t show change markers for the edits I have made. Additionally, it doesn\'t show up in the Local Changes window until I click the Refresh button, after which IDEA marks the file as modified in the editor tab (blue filename) and the change markers appear. If I make some more changes to the file, it immediately changes back to being marked as a new file without change markers.\n \nThis wasn\'t happening until recently. And it only happens for this specific project (which is quite large). Other smaller projects don\'t exhibit the same behavior. I\'ve tried invalidating caches and restarting but that hasn\'t helped. I don\'t want to have to reclone my repo because I have quite a few local branches, but I will if I have no other choice.','Git change markers disappear when editing file','WORK',1,2,4),(9,'2014-10-06 18:15:52','Currently only \"licensed To\" is displayed, which makes it hard to choose between several assets with the same value of this property.\nWe need to show subscription/expiration end date if available and license restrictions (also if available)','Better descriptions for available assets in \"Choose Asset panel\"','DONE',1,2,4),(10,'2014-10-06 18:19:37','What steps will reproduce the problem?\n1. Open project A.\n2. Open project B (in new window).\n3. Try to keep working on project A while B opens.\n\nWhat is the expected result?\nI don\'t mind if new window tries to open in background or on top. Thats all, no more focus playing.\n\nWhat happens instead?\nWindow opens on top, and re-focuses itself 4, 5 or 6 times again while opening project, scanning, and all that stuff.\n\nI want to add that perfect behavior will be open in background. Then users may disquss about focusing itself once it\'s fully loaded or do nothing. I think that this issue should stay impartial about that and wait for feedback.','Don\'t repeatedly steal focus on project opening','FREE',1,NULL,4);
/*!40000 ALTER TABLE `tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(100) NOT NULL,
  `name` varchar(150) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'man','Alexey','$2a$10$IHuvsorddsKy3MX8zpwQGe6ZzEHPkIchUJ4ZWh5IYc51rC3/qDQ7u','Manager'),(2,'dev','Sergey','$2a$10$i9/H7zTGVScghNayGyaQpOjNvmK8dV73f/o753HYN/EmSm97sngaO','Developer');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-10-06 18:23:56
