=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
RabbitTrapper(CIS1200 Final Project) README
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

   Core Concepts 
=

  1. 2D Arrays
  I used 2D arrays to make a 2D Grass Array that contained all of the spots
  where the rabbit could move. I used this in GameBoard and this is a clear use of 2D arrays since I'm
  creating a grid of objects that can be easily accessible with position storage.

  2. Collections
  I used a collection when doing my BFS and this was because I needed to constantly add and remove
  values because I was tracking the possible areas my rabbit could go and at the end marking the
  shortest distance. This is a great way to use collections because of the changing length of the collection.

  3. File I/O
  I made a button that saves the game. This is clearly File I/O because I am writing and reading the file as I save
  and I am storing it to a filename within my computer. When I need to actually use it, I call on it and I read it.
  We can see this in my RabbitGame File.


  4. JUnit Testable Component
    I tested my game 10 different ways to make sure that
    there were no extenuating errors when the game ran or the player tried to break the game.
    This is a clear use of JUnit Testable Components since I'm testing possible ways to break my code
    and thus making sure each piece of code has correct functionality.

   File Structure Screenshot 
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named 
  "file_structure.png".


   My Implementation
  ==
  GameBoard --> This creates the game board.
  Grass --> This is a special class that tracks whether a spot is an edge, empty, filled, or contains the bunny.
  Rabbit --> This is the Rabbit that is tracked throughout the game to tell where on the Grass 2D array
  the rabbit is located
  RabbitGame --> This is where everything comes together and more actionable things are made like reset and save
  so that when we actually run the game the buttons are able to react with all of the aforementioned classes.
  RunMyGame -->
  RabbitCatcherTest --> This 10 tests which test some of the edge cases within my code.

Problems I had: 
I was mostly very stuck on how to make the searching for the Rabbit, this took me a REALLY long time since
its been a while since I've done BFS. If I had a chance to refactor I think that I would delete the Rabbit and I would only use Grass. I think
there's a lot of inefficiency in storage of variables and there is double counting of positions.


   External Resources 
========================

https://www.google.com/search?sca_esv=3b91e47ab754cb87&rlz=1C5CHFA_enUS1071US1071&q=cartoon+pixel+bunny&udm=2&fbs=
AEQNm0Aa4sjWe7Rqy32pFwRj0UkWd8nbOJfsBGGB5IQQO6L3J7pRxUp2pI1mXV9fBsfh39JqJxzRlphkmT2MeVSzs3MQEN5DgZmMeykT7pJra3boNLZnu_5
tndbt0B1M3XLn1qbCrHVm5u-tncY-lmea9PiStNZ9eJS94DUI766pUVC1fpio1ZP_3qV2amahtpN19jVnfw-T_BjghPRTftMGLk6z20h08A&sa=X&ved=2ah
UKEwj5ktj8t5yKAxVXKlkFHRAvBv4QtKgLegQIEhAB&biw=950&bih=1113&dpr=2#vhid=JnfQqZY_HZ98fM&vssid=mosaic

This is for the actual image for the bunny.

https://www.tutorialspoint.com/data_structures_algorithms/breadth_first_traversal.htm

I also used the above to learn and implement breadth first search.
