 AUTHOR: SIDDHANT DESHMUKH
 UFID  : 36568046
-------------------------------------------------------
 DISTRIBUTED OPERATING SYSTEM - GOSSIP SIMULATOR 
-------------------------------------------------------

CONTENTS OF THIS FILE 
---------------------
   
 * Required Answers  
 * Introduction
 * Requirements
 * Installation and Configuration
 * Program flow
 * Developer information




REQUIRED ANSWERS
----------------
* Team members
  
  I worked alone on this project in order to gain the most out of this course.


* What is working

  - Gossip is working for all the topologies and 100% convergence is being achieved each time. However, for large number of nodes (in the range of 10000+) 'full' topolgy makes the system go out of memery as each node has to keep track of all its neighbours(in the range of 10000+) which is not feasible.
  - 'Line' topology took an annoyingly large time to converege for 10000 nodes. Also I had to modify the rumour limit for each node to 30 to
     ensure the system converged. It took about half an hour!
  - Moreover, Gossip is working for '3d' and 'Imperfect 3d' for nodes in the range of 10000 without making the system go out of memory and quit fast(2000-3000 ms)
  - Pushsum took much longer to converge when compared to gossip as the sum from one end of the network had to reach the other end of the network which was not pssobile in many cases (large networks and line topology).

  - In conclusion, I have implemented all the topologies, pushsum, average and gossip algorithm. I have also implemented a failure model and tested it with parameter (number of killed nodes). I have tested the failure model on all the topologies and with both the algorithms, pushsum and gossip.


* What is the largest network you managed to deal with for each type of topology and algorithm

               | LINE          | FULL          | 3D            |  IMP 3D     |
 GOSSIP        | 10000 nodes   | 10000 nodes   | 10000 nodes   | 10000 nodes |
 PUSHSUM       | 27 nodes      | 10000 nodes   | 10000 nodes   | 10000 nodes |
 AVERAGE       | 27 nodes      | 10000 nodes   | 10000 nodes   | 10000 nodes |   

 Trying larger than the above for 'line' and getting the exact pushsum value took an alarmingly large time. For the other 3 topologies the memory usually ran if if I tried larger values. But im confident given the resources my program will be able to handle larger number of nodes.


INTRODUCTION
------------
The project folder dos-siddhant-36568046 contains 2 folders:

* gossip: Contains the folders related to the gossip simulator project. Important files being:
          - build.sbt
          - gossip.scala
          - report.pdf

* bonus: Contains the folders related to the bonus project.
         - build.sbt
         - bonus.scala
         - bonus-report.pdf



REQUIREMENTS
------------
The following need to be installed to run the project:
* Scala
* Akka
* Sbt



INSTALLATION AND CONFIGURATION
------------------------------
 Go to the folder 'gossip' or 'bonus' using command line tool and type 'sbt run'.


PROGRAM FLOW
------------
* GOSSIP
  - enter the number of nodes (will be rounded up for 3d and imp3d topologies)
  - enter the topology
  - enter the algorithm
  - enter the time limit for program to stop(if not entered default time used)

* BONUS
  - enter the number of nodes (will be rounded up for 3d and imp3d topologies)
  - enter the topology
  - enter the algorithm
  - enter the number of nodes to kill (used this feature for testing and analysing various topologies and algorithms)
  - enter the time limit for program to stop(if not entered default time used)


DEVELOPER INFORMATION
---------------------
Siddhant Deshmukh
Master in Computer Science at University of Florida
Email: siddhantdeshmukh@ufl.edu    or   siddhantd28@gmail.com
UFID: 36568046
Phone: 415-630-0891




*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=
*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=
*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=
*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=* THANK YOU *=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=
*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=
*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=
*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=



