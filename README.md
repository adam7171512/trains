# Trains

A Java project was created as a university assignment – it's a console app with a straightforward GUI panel for visualization that simulates a train management system.

Key aspects involved:
1. Concurrency, where each train is represented by an individual thread. The railroads could accommodate only one train at a time, forcing other trains to wait in line—this required the use of a lock.

2. Pathfinding. The project incorporates several algorithms including A*, Dijkstra, and DFS. These implementations of the IRouteFinder interface were utilized with the strategy pattern.

3. Validation: Certain types of loads can only be carried by specific Railroad Cars. Additionally, cars and locomotives have restrictions on weight and passenger limits, among other things.


[![Watch the video](https://img.youtube.com/vi/D2AIgk8s5Fo/maxresdefault.jpg)](https://youtu.be/D2AIgk8s5Fo)
