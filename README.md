# Build Your Own World Design Document

**Partner 1:**
Santhosh Mathialagan

**Partner 2:**
Noah Kim

## Classes and Data Structures
Class 1: NewWorld.java
Instance Variables:
   - WIDTH: static final variable representing the width of the world
   - HEIGHT: static final variable representing the height of the world
   - widthLim: the limit on how wide a given structure can be
   - heightLim: the limit on how tall a given structure can be
   - OrigWidth: the width of the first structure built
   - OrigHeight: the height of the first structure built 
   - OrigBLX: the x coordinate of the bottom left corner of the first structure built 
   - OrigBLY: the y coordinate of the bottom left corner of the first structure built
   - Width: the width of the next structure to be built
   - Height: the height of the next structure to be built
   - BottomLeftX: the bottom left x coordinate of the next structure to be built
   - BottomLeftY: the bottom left y coordinate of the next structure to be built
   - rd: our pseudorandom integer stream 
   - world: our 2D array of tiles
   - usage: the number of non-nothing tiles in our world

## Algorithms
Summary of our algorithm:
1. Build a room in the middle of the world to start
2. Use the coordinates of the last structure built to get the coordinates for the next structure to be built
3. Validate the new coordinates a certain number of times
4. If no valid coordinates are found, return to the middle and build from there
5. Repeate steps 2 - 4 until we either cannot find valid points in the middle, or we populate some percentage (i.e 90%) of the world with non-nothing tiles
   
NewWorld Class:
   - All of the work is done in this class
   - Methods:
      - Constructor: assigns starting values to our global variables if necessary
      - buildWorld():
           - builds rooms and hallways until a certain percentage of our world is populated with non-nothing tiles
           - after a room/hall is built, this method generates and validates the next set of coordinates to be used
           - validates using validateLoop()
           - gets next coordinates using gentNextHallCoords()  or getNextRoomCoords()
     - getNextRoom/HallCoords(): updates the global variables with the next coordinates to be used
     - validateLoop(): validates that the current next coordinates are both within the world, will ensure connectedness, and will not overlap with existing structures too much
         - if parameters do not work, validateLoop gets another set of coords using the getNextCoords methods and then validates those
      - numOverlaps(): returns the number of overlapping tiles that the next structure to be built will have (used within validateLoop())


         


