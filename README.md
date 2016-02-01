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
  
* ~~Fix the left tree display~~
* ~~Fix the wrong label for left tree view mode~~
* ~~Cleanup the imports in pom.xml~~
* Fix the books grid pagination
* Introduce ExtJS menus for all grids
* Info area redesign, and upload image display on left side, not center
* Spring security with login form
* ~~Creation of an error page and custom error handling using a custom ErrorController~~
* Return a ResponseEntity for all REST requests, for error and also for success
* File import for books, authors, etc
* Export to .csv, .doc, .xls, .pdf
* Form validation using vtypes
* ~~Improvements for error handling forms to parse the response~~
* ~~Fix inactive grid buttons after modify/delete operations~~
* ~~Send request to persistence when editing a grid record to check it's validity~~
* Filter to display image-only files on upload
* Junit tests
* Selenium tests
* Quartz scheduler to clean-up the upload folder
* Create a new tab containing a tree (files, directory listing?) to play around with more complicated tree structures
* Reload left tree when new book is created
* Create info area for authors, categories, publishing houses, to display the associated books
* ~~Display only items with records in the left tree (e.g. authors with books)~~
* Create filtering for authors with books only, etc
* Fix the books grid filtering to take into account current tree selection
* Fix upload is lost when editing + save directly bug
* Fix author save failed when using dataNasterii field value
    
<i>More future plans :-) </i>
  
* To port the UI to angular JS
* Create posility to choose your front-end
* Mobile testing
* Live application!
* Have more fun with this code ;-)
    
  