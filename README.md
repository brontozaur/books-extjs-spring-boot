# Books Manager

This application was originally started as a tutorial for web applications, using:

 * servlets
 * standalone tomcat environment
 * hibernate
 * extJS for UI

The initial source code was hosted in defunct Google Code. The goal of the application was to create a web interface 
for a books manager application, allowing CRUD operations for:

 * books
 * editors
 * categories
 * publishing houses

However, the source code quickly became something else, i didn't think about it very much: a learning platform for some
cool technologies. I wanted to have more features, to adapt more standards and technologies in this app (regardless of 
their utility for this project), to be an incubator of cool stuffs. After migration from Google Code to GitHub, some 
development and maintenance was done in GitHub as well, but nothing fancy. Starting from this year, the back-end was
completely redesigned and rewritten to integrate more features.

# Screenshots

 * Main perspective:
 
 ![Books manager overview](http://i64.tinypic.com/2lduk94.png)
 
 * Book form:
 
 ![Book form](http://i63.tinypic.com/sm5yj8.png)

Currently, some of the cool technologies implemented in this application are:

#Back-end
    
* Maven
* Spring boot
* Hibernate
* Logback
* Spring profiles
* REST API via Jackson
* Apache commons
* Spring data
* Image uploads
* Multi platform compatibility (tested on Windows, MacOS and Ubuntu)
* Travis CI integration
    
#Front-end
  
* ExtJS 4
* Multipart form requests
* JQuery integration
* MVC architecture
* Grids, trees, forms, sorting, filtering
* CRUD operations
* Basic form validation
* Image upload and delete uploaded file
    
<i>Future plans</i>
  
* :thumbsup: :frog: ~~Fix the left tree display~~
* :thumbsup: :frog: ~~Fix the wrong label for left tree view mode~~
* :thumbsup: ~~Cleanup the imports in pom.xml~~
* :frog: Fix the books grid pagination
* Introduce ExtJS menus for all grids
* Info area redesign, and upload image display on left side, not center
* Spring security with login form
* :thumbsup: ~~Creation of an error page and custom error handling using a custom ErrorController~~
* Return a ResponseEntity for all REST requests, for error and also for success
* File import for books, authors, etc
* Export to .csv, .doc, .xls, .pdf
* Form validation using vtypes
* :thumbsup: ~~Improvements for error handling forms to parse the response~~
* :thumbsup: :frog: ~~Fix inactive grid buttons after modify/delete operations~~
* :thumbsup: ~~Send request to persistence when editing a grid record to check it's validity~~
* Filter to display image-only files on upload
* Junit tests
* Selenium tests
* Quartz scheduler to clean-up the upload folder
* Create a new tab containing a tree (files, directory listing?) to play around with more complicated tree structures
* Reload left tree when new book is created
* Create info area for authors, categories, publishing houses, to display the associated books
* :thumbsup: ~~Display only items with records in the left tree (e.g. authors with books)~~
* Create filtering for authors with books only, etc
* :frog: Fix the books grid filtering to take into account current tree selection
* :thumbsup: :frog: ~~Fix upload is lost when editing + save directly bug~~
* :frog: Fix author save failed when using dataNasterii field value
* :thumbsup: ~~Book cover uploads should not create files on server~~
* To convert image upload API into a document upload API, with items displayed on a new tab, based on their type. Server file explorer!
    
<i>More future plans :-) </i>
  
* To port the UI to angular JS
* Create posility to choose your front-end
* Mobile testing
* Live application!
* Have more fun with this code ;-)

#Maven dependency tree :date: 2016.02.02
 
 --- maven-dependency-plugin:2.10:tree (default-cli) @ booksManager ---   
 com.popa.books:booksManager:war:1.0-SNAPSHOT   
 +- commons-lang:commons-lang:jar:2.4:compile   
 +- commons-io:commons-io:jar:2.4:compile   
 +- org.springframework.boot:spring-boot-autoconfigure:jar:1.3.1.RELEASE:compile   
 |  \- org.springframework.boot:spring-boot:jar:1.3.1.RELEASE:compile   
 +- org.springframework.boot:spring-boot-starter-data-jpa:jar:1.3.1.RELEASE:compile   
 |  +- org.springframework.boot:spring-boot-starter:jar:1.3.1.RELEASE:compile   
 |  |  +- org.springframework.boot:spring-boot-starter-logging:jar:1.3.1.RELEASE:compile   
 |  |  |  +- ch.qos.logback:logback-classic:jar:1.1.3:compile   
 |  |  |  |  \- ch.qos.logback:logback-core:jar:1.1.3:compile   
 |  |  |  +- org.slf4j:jul-to-slf4j:jar:1.7.13:compile   
 |  |  |  \- org.slf4j:log4j-over-slf4j:jar:1.7.13:compile   
 |  |  \- org.yaml:snakeyaml:jar:1.16:runtime   
 |  +- org.springframework.boot:spring-boot-starter-aop:jar:1.3.1.RELEASE:compile   
 |  |  \- org.aspectj:aspectjweaver:jar:1.8.7:compile   
 |  +- org.springframework.boot:spring-boot-starter-jdbc:jar:1.3.1.RELEASE:compile   
 |  |  +- org.apache.tomcat:tomcat-jdbc:jar:8.0.30:compile   
 |  |  |  \- org.apache.tomcat:tomcat-juli:jar:8.0.30:compile   
 |  |  \- org.springframework:spring-jdbc:jar:4.2.4.RELEASE:compile   
 |  +- javax.transaction:javax.transaction-api:jar:1.2:compile   
 |  \- org.springframework:spring-aspects:jar:4.2.4.RELEASE:compile   
 +- org.springframework.boot:spring-boot-starter-web:jar:1.3.1.RELEASE:compile   
 |  +- org.springframework.boot:spring-boot-starter-validation:jar:1.3.1.RELEASE:compile   
 |  |  \- org.hibernate:hibernate-validator:jar:5.2.2.Final:compile   
 |  |     +- javax.validation:validation-api:jar:1.1.0.Final:compile   
 |  |     \- com.fasterxml:classmate:jar:1.1.0:compile   
 |  +- com.fasterxml.jackson.core:jackson-databind:jar:2.6.4:compile   
 |  |  +- com.fasterxml.jackson.core:jackson-annotations:jar:2.6.4:compile   
 |  |  \- com.fasterxml.jackson.core:jackson-core:jar:2.6.4:compile   
 |  +- org.springframework:spring-web:jar:4.2.4.RELEASE:compile   
 |  \- org.springframework:spring-webmvc:jar:4.2.4.RELEASE:compile   
 |     \- org.springframework:spring-expression:jar:4.2.4.RELEASE:compile   
 +- org.springframework.boot:spring-boot-starter-tomcat:jar:1.3.1.RELEASE:compile   
 |  +- org.apache.tomcat.embed:tomcat-embed-core:jar:8.0.30:compile   
 |  +- org.apache.tomcat.embed:tomcat-embed-el:jar:8.0.30:compile   
 |  +- org.apache.tomcat.embed:tomcat-embed-logging-juli:jar:8.0.30:compile   
 |  \- org.apache.tomcat.embed:tomcat-embed-websocket:jar:8.0.30:compile   
 +- org.springframework.boot:spring-boot-starter-test:jar:1.3.1.RELEASE:test   
 |  +- junit:junit:jar:4.12:test   
 |  +- org.mockito:mockito-core:jar:1.10.19:test   
 |  |  \- org.objenesis:objenesis:jar:2.1:test   
 |  +- org.hamcrest:hamcrest-core:jar:1.3:test   
 |  +- org.hamcrest:hamcrest-library:jar:1.3:test   
 |  +- org.springframework:spring-core:jar:4.2.4.RELEASE:compile   
 |  \- org.springframework:spring-test:jar:4.2.4.RELEASE:test   
 +- mysql:mysql-connector-java:jar:5.1.38:compile   
 +- org.hibernate:hibernate-entitymanager:jar:4.3.11.Final:compile   
 |  +- org.jboss.logging:jboss-logging:jar:3.3.0.Final:compile   
 |  +- org.jboss.logging:jboss-logging-annotations:jar:1.2.0.Beta1:compile   
 |  +- org.hibernate:hibernate-core:jar:4.3.11.Final:compile   
 |  |  +- antlr:antlr:jar:2.7.7:compile   
 |  |  \- org.jboss:jandex:jar:1.1.0.Final:compile   
 |  +- dom4j:dom4j:jar:1.6.1:compile   
 |  |  \- xml-apis:xml-apis:jar:1.0.b2:compile   
 |  +- org.hibernate.common:hibernate-commons-annotations:jar:4.0.5.Final:compile   
 |  +- org.hibernate.javax.persistence:hibernate-jpa-2.1-api:jar:1.0.0.Final:compile   
 |  +- org.jboss.spec.javax.transaction:jboss-transaction-api_1.2_spec:jar:1.0.0.Final:compile   
 |  \- org.javassist:javassist:jar:3.18.1-GA:compile   
 +- org.springframework.data:spring-data-jpa:jar:1.9.2.RELEASE:compile   
 |  +- org.springframework.data:spring-data-commons:jar:1.11.2.RELEASE:compile   
 |  +- org.springframework:spring-orm:jar:4.2.4.RELEASE:compile   
 |  +- org.springframework:spring-context:jar:4.2.4.RELEASE:compile   
 |  +- org.springframework:spring-aop:jar:4.2.4.RELEASE:compile   
 |  |  \- aopalliance:aopalliance:jar:1.0:compile   
 |  +- org.springframework:spring-tx:jar:4.2.4.RELEASE:compile   
 |  +- org.springframework:spring-beans:jar:4.2.4.RELEASE:compile   
 |  +- org.aspectj:aspectjrt:jar:1.8.7:compile   
 |  +- org.slf4j:slf4j-api:jar:1.7.13:compile   
 |  \- org.slf4j:jcl-over-slf4j:jar:1.7.13:compile   
 \- com.google.code.gson:gson:jar:2.3.1:compile  