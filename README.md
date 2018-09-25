Project provides simple implemenation of functional wrapper to validation

# usage
  The wrraper was made for validation of object which can be null and you dont need to check it
  
  Simple Example :
  
  ```
    Validate.ifPresent(request)
           .valid( request -> request.name.equals('Bartek')
           .isValid()
  ```          
  When object is null `isValid` method will be true, on other hand the object will be validated.
  
# api

  Static method for Validating an object :
   
   `ifPresent`
   
   `present`
   
  
  To validate parameter you have three methods :
  
   `valid` 

   `or`
   
   `and`
   
  To get result of validation you can use :

  `isValid()`
  
  or you can throw an excpetion if parameter is not valid by :
  
  `orElseThrown()`
 
 
   
  
  
  
   
   
   
   
   
   
  
  
  
    
    

  
  
  
  
