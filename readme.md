# Student Store Scanner

This is a scanner made for a student-ran store in the Athenian School, students have the option to use unique QR code to 
store their money online into a database and use it whenever they visit the store. 

The project is built with JavaFX with some Swing components scrambled in. The hope is to integrate this into Flutter once
everything works

## TODO
* Implement Database client with Balance and Email.
    * If time allows, it will be in SQL, if not, it will use Redis with Jedis client. It'll probably be with Jedis, since it's 
    DUE IN 7 days.
* Fix problem with balance validation where balance is reset to 0 every time.   
* Might change the encryption method for QR since it's really long...  

## Disclaimer

* ~~Although I wish I had a collaborator~~ The project is made by one person, and it is very rough around the edges.

* The "encryption" is very bad and the QR code is easy to replicate if you know what you are doing. Further steps 
will be taken to make a better encryption once the project works.


## Dependencies

Dependencies are declared in `Build.gradle` and should be automatically downloaded, but I'll list it here again just for kicks

* Java 8, JavaFX
* Zxing 3.3.0 
* Sarxos Webcam Library 0.3.10
* Jedis 2.8.0 (Maybe)


## Problems
* There has been problems with ``Compile`` and ``Implementation`` in ``Build.gradle`` due to different gradle versions 