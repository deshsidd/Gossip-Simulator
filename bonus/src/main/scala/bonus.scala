import akka.actor._
import scala.util.Random
import scala.collection.mutable.StringBuilder
import scala.util.Random
import scala.concurrent.duration._
import scala.math
import scala.runtime.RichInt   
import java.util.concurrent.TimeUnit
  

object bonus
 {
  
case class Rumour(str:String)
case class Done(str:String)
case class Pushsum(s:Double,w:Double)
case class Clocktickrumour(str: String)
case class Clocktickpushsum
case class PushsumDone
case class Forcestop()
case class KillNodes(numKill:Int)
case class Die()
case class Forcedone()
import system.dispatcher
var starttime: Long = 0
val system = ActorSystem("GossipSystem")




 def main(args: Array[String])
 {
         

          
          var limit=0
          var numNodes = (readLine("Enter the number of nodes : ")).toInt
          var topology = readLine("Enter the topology (either 'line' , 'full' , '3d' or 'imp3d') : ")
          var algorithm = readLine("Enter the algorithm (either 'gossip' or 'pushsum' or 'pushsumaverage') : ")
          var numKill= (readLine("Enter the number of nodes to randomly kill: ")).toInt
          var choice= readLine("Do you want to enter the maximum amount of time the program should run? (enter 'yes' or 'no') : ")
          if(choice=="yes")
          {
                limit= (readLine("Enter the time in milliseconds : ")).toInt


          }
          else
          {
                println(" limit set to default value. ")
                 limit= 300*numNodes

          }
          
          
          if(topology=="3d" || topology=="imp3d")
          {
                var i=2
                while(numNodes > i*i*i)
                {
                      i+=1

                }
                numNodes=i*i*i
                println("number of nodes is " + numNodes)
          }

        
          val Master = system.actorOf(Props(new Master(numNodes,topology,algorithm,system, numKill,limit)), name = "Master")
          
 }









class Master(numNodes:Int,topology:String,algorithm:String, system: ActorSystem, numKill :Int , limit:Int) extends Actor
{
        var mastercount=0.0
        var percentcomplete=0.0
        var cubeindex= (Math.cbrt(numNodes)).toInt
        var numOfDigits=cubeindex.toString.length
        var done=false
 
        topology match 
        {

            case "line" => 
                          for( i <- 1 to numNodes) 
                         {
                                if(algorithm=="pushsumaverage")
                                {
                                val Worker = context.actorOf(Props(new Worker(i,"line",numNodes,i,1)) , name="Node" + i.toString)
                                }
                                else
                                {
                                val Worker = context.actorOf(Props(new Worker(i,"line",numNodes,i,0)) , name="Node" + i.toString)  
                                }
                                println("Node" + i + " created with name : Node" + i.toString)
                         }

            case "full" => 
                          for( i <- 1 to numNodes) 
                         {
                                if(algorithm=="pushsumaverage")
                                {
                                val Worker = context.actorOf(Props(new Worker(i,"full",numNodes,i,1)) , name="Node" + i.toString)
                                }
                                else
                                {
                                val Worker = context.actorOf(Props(new Worker(i,"full",numNodes,i,0)) , name="Node" + i.toString)
                                }
                                println("Node" + i + " created with name : Node" + i.toString)
                         }
          
            case "3d" => 
                          
                          var ctr=0
                          for( i <- 1 to cubeindex) 
                          {
                              for( j <- 1 to cubeindex) 
                              {
                                  for( k <- 1 to cubeindex) 
                                  {
                                  
                                        var id=((Math.pow(10, numOfDigits*2)*i)+(Math.pow(10, numOfDigits)*j)+(1*k)).toInt
                                        ctr=ctr+1
                                        if(algorithm=="pushsumaverage")
                                        {
                                        val Worker = context.actorOf(Props(new Worker(id,"3d",numNodes,ctr-1,1)) , name="Node" + id.toString)
                                        }
                                        else
                                        {
                                        val Worker = context.actorOf(Props(new Worker(id,"3d",numNodes,ctr-1,0)) , name="Node" + id.toString)
                                        }
                                        println("Node" + ctr + " created with name : Node" + id.toString)
                                        
                                  }
                              } 
                          }

            case "imp3d" =>
                            
                            var ctr=0
                            for( i <- 1 to cubeindex) 
                            {
                                for( j <- 1 to cubeindex) 
                                {
                                    for( k <- 1 to cubeindex) 
                                    {
                                          var id=((Math.pow(10, numOfDigits*2)*i)+(Math.pow(10, numOfDigits)*j)+(1*k)).toInt
                                          ctr=ctr+1
                                          if(algorithm=="pushsumaverage")
                                          {
                                          val Worker = context.actorOf(Props(new Worker(id,"imp3d",numNodes,ctr-1,1)) , name="Node" + id.toString)
                                          }
                                          else
                                          {
                                          val Worker = context.actorOf(Props(new Worker(id,"imp3d",numNodes,ctr-1,0)) , name="Node" + id.toString)
                                          }
                                          println("Node" + ctr + " created with name : Node" + id.toString)
                                          
                                    }
                                } 
                            }


        }

        starttime = System.currentTimeMillis

        



        algorithm match 
        {

            case "gossip" => if(topology=="line" || topology=="full")
                             {
                                  var rand=Random.nextInt(numNodes)+1
                                  val chosenOne = system.actorSelection("/user/Master/Node" + rand)
                                  chosenOne ! Rumour("sachin is a god")

                             }
                             else if(topology=="3d" || topology=="imp3d")
                            {
                                 
                                  var cubeindex= (Math.cbrt(numNodes)).toInt
                                  var randx=Random.nextInt(cubeindex)+1
                                  var randy=Random.nextInt(cubeindex)+1
                                  var randz=Random.nextInt(cubeindex)+1
                                  var tempid=((Math.pow(10, numOfDigits*2)*randx)+(Math.pow(10, numOfDigits)*randy)+(1*randz)).toInt
                                  val chosenOne = system.actorSelection("/user/Master/Node"+ tempid.toString)
                                  chosenOne ! Rumour("sachin is a god")

                            }

            case "pushsum" => if(topology=="line" || topology=="full")
                             {
                                  var rand=Random.nextInt(numNodes)+1
                                  val chosenOne = system.actorSelection("/user/Master/Node" + rand)
                                  chosenOne ! Pushsum(0,1)

                             }
                             else if(topology=="3d" || topology=="imp3d")
                            {
                                  var cubeindex= (Math.cbrt(numNodes)).toInt
                                  var randx=Random.nextInt(cubeindex)+1
                                  var randy=Random.nextInt(cubeindex)+1
                                  var randz=Random.nextInt(cubeindex)+1
                                  var tempid=((Math.pow(10, numOfDigits*2)*randx)+(Math.pow(10, numOfDigits)*randy)+(1*randz)).toInt
                                  val chosenOne = system.actorSelection("/user/Master/Node"+ tempid.toString)
                                  chosenOne ! Pushsum(0,1)
                            }


            case "pushsumaverage" =>  if(topology=="line" || topology=="full")
                             {
                                  var rand=Random.nextInt(numNodes)+1
                                  val chosenOne = system.actorSelection("/user/Master/Node" + rand)
                                  chosenOne ! Pushsum(0,0)

                             }
                             else if(topology=="3d" || topology=="imp3d")
                            {
                                  var cubeindex= (Math.cbrt(numNodes)).toInt
                                  var randx=Random.nextInt(cubeindex)+1
                                  var randy=Random.nextInt(cubeindex)+1
                                  var randz=Random.nextInt(cubeindex)+1
                                  var tempid=((Math.pow(10, numOfDigits*2)*randx)+(Math.pow(10, numOfDigits)*randy)+(1*randz)).toInt
                                  val chosenOne = system.actorSelection("/user/Master/Node"+ tempid.toString)
                                  chosenOne ! Pushsum(0,0)
                            }
















        }

            val killcountdown =system.scheduler.scheduleOnce(1000 milliseconds,self,KillNodes(numKill))////
            
         
         
          

          def receive = 
          {
                case  Rumour(str)=> 
                                    println("Rumour " + str + " sent to master.")

                case Done(str)=>
                                    var done=0
                                    mastercount+=1
                                    if(mastercount==1)
                                    {
                                          system.scheduler.scheduleOnce((limit) milliseconds, self,Forcedone())

                                    }
                                    percentcomplete=(mastercount/(numNodes-numKill))*100
                                    if(percentcomplete >= 90 && done==0)
                                    {
                                    done+=1
                                    if(done==1)
                                    {
                                    println("Opereration complete with percentage : " + percentcomplete)
                                    var duration=(System.currentTimeMillis-starttime).millis
                                    println("duration for " + percentcomplete+ "%  is :" + duration)
                                    context.system.shutdown()
                                    }
                                    }


                case Forcedone()=>
                                    percentcomplete=(mastercount/numNodes)*100
                                      println("gossip operation complete by force stop\n percentage of nodes that received pushsum is :" + percentcomplete)
                                        var duration=(System.currentTimeMillis-starttime).millis
                                        println("duration for " + percentcomplete+ "%  is :" + duration)
                                        context.system.shutdown()



                case PushsumDone=> 



                                      
                                      mastercount+=1
                                     
                                      if(mastercount==1)
                                      {
                                      system.scheduler.scheduleOnce((limit) milliseconds, self,Forcestop())

                                      }

                                      percentcomplete=(mastercount/numNodes)*100
                                      if(percentcomplete >= 50 && done==false)////
                                      {
                                        done=true
                                        
                                        println("pushsum operation complete \n percentage of nodes that received pushsum is :" + percentcomplete)
                                        var duration=(System.currentTimeMillis-starttime).millis
                                        println("duration for " + percentcomplete+ "%  is :" + duration)
                                        context.system.shutdown()
                                      }

                case Forcestop()=>
                
                                      percentcomplete=(mastercount/numNodes)*100
                                      println("pushsum operation complete by force stop\n percentage of nodes that received pushsum is :" + percentcomplete)
                                        var duration=(System.currentTimeMillis-starttime).millis
                                        println("duration for " + percentcomplete+ "%  is :" + duration)
                                        context.system.shutdown()


                case KillNodes(numKill)=>
                                         println("in kill nodes at master")
                                         for( i <- 1 to numKill) 
                                         {
                                                  if(topology=="line" || topology=="full")
                                                 {
                                                      var rand=Random.nextInt(numNodes)+1
                                                      val chosenOne = system.actorSelection("/user/Master/Node" + rand)
                                                      chosenOne ! Die()

                                                 }
                                                 else if(topology=="3d" || topology=="imp3d")
                                                 {
                                                      var cubeindex= (Math.cbrt(numNodes)).toInt
                                                      var randx=Random.nextInt(cubeindex)+1
                                                      var randy=Random.nextInt(cubeindex)+1
                                                      var randz=Random.nextInt(cubeindex)+1
                                                      var tempid=((Math.pow(10, numOfDigits*2)*randx)+(Math.pow(10, numOfDigits)*randy)+(1*randz)).toInt
                                                      val chosenOne = system.actorSelection("/user/Master/Node"+ tempid.toString)
                                                      chosenOne ! Die()
                                                 }

                                           
                                         }

                  



          }
}





class Worker(identity: Int, topology: String , numNodes:Int , stemp:Double , wtemp: Double) extends Actor
{
        var cubeindex= (Math.cbrt(numNodes)).toInt
        var numOfDigits=cubeindex.toString.length
        var counter=0
        var receivedrumour=0
        var receivedpushsum=0
        var id=identity
        var numOfNeighbours=0
        var neighbours=new Array[ActorSelection](numNodes)
        var x,y,z=0 
        var s=stemp
        var w=wtemp
        var rand=0
        var snew=0.0
        var wnew=0.0
        var sprev=0.0
        var wprev=0.0
        

        topology match 
        {

            case "line" => 
                            if(id==1)
                            {
                                  numOfNeighbours=1
                                 
                                  neighbours(0) = context.actorSelection("../Node"+(id+1))
                            }
                            
                            else if(id==numNodes)
                            {
                                  numOfNeighbours=1
                                 
                                  neighbours(0) = context.actorSelection("../Node"+(id-1))
                            }
                            else
                            {
                                  numOfNeighbours=2
                                  
                                  neighbours(0) = context.actorSelection("../Node"+(id-1))
                                  neighbours(1) = context.actorSelection("../Node"+(id+1))
                            }
                          
            case "full" =>
                            var j=0
                            for( i <- 1 to numNodes) 
                            {
                                  if(id!=i)
                                  {
                                        neighbours(j) = context.actorSelection("../Node"+(i))
                                        j+=1


                                  }


                            
                          }
                          numOfNeighbours=numNodes-1

            case "3d" =>  
                          var temp=id
                          z = temp % (Math.pow(10, numOfDigits)).toInt
                          temp=temp/(Math.pow(10, numOfDigits)).toInt
                          y=temp % (Math.pow(10, numOfDigits)).toInt
                          temp=temp/(Math.pow(10, numOfDigits)).toInt
                          x=temp % (Math.pow(10, numOfDigits)).toInt
                          
                          
                         

                          if(x!=1)
                          {
                                var a=x-1
                                var b=y
                                var c=z
                                var tempid=((Math.pow(10, numOfDigits*2)*a)+(Math.pow(10, numOfDigits)*b)+(1*c)).toInt
                                neighbours(numOfNeighbours) = context.actorSelection("../Node"+ tempid.toString)
                                //println("neighbour of" + id +"  is "+ tempid)
                                numOfNeighbours+=1
                          }
                          if(x!=cubeindex)
                          {
                                var a=x+1
                                var b=y
                                var c=z
                                var tempid=((Math.pow(10, numOfDigits*2)*a)+(Math.pow(10, numOfDigits)*b)+(1*c)).toInt
                                 neighbours(numOfNeighbours) = context.actorSelection("../Node"+ tempid.toString)
                                //println("neighbour of" + id +"  is "+ tempid)
                                numOfNeighbours+=1
                          }
                          if(y!=1)
                          {
                                var a=x
                                var b=y-1
                                var c=z
                                var tempid=((Math.pow(10, numOfDigits*2)*a)+(Math.pow(10, numOfDigits)*b)+(1*c)).toInt
                                 neighbours(numOfNeighbours) = context.actorSelection("../Node"+ tempid.toString)
                                //println("neighbour of" + id +"  is "+ tempid)
                                numOfNeighbours+=1
                          }
                          if(y!=cubeindex)
                          {
                                var a=x
                                var b=y+1
                                var c=z
                                var tempid=((Math.pow(10, numOfDigits*2)*a)+(Math.pow(10, numOfDigits)*b)+(1*c)).toInt
                                 neighbours(numOfNeighbours) = context.actorSelection("../Node"+ tempid.toString)
                                //println("neighbour of" + id +"  is "+ tempid)
                                numOfNeighbours+=1
                          }
                          if(z!=1)
                          {
                                var a=x
                                var b=y
                                var c=z-1
                                var tempid=((Math.pow(10, numOfDigits*2)*a)+(Math.pow(10, numOfDigits)*b)+(1*c)).toInt
                                 neighbours(numOfNeighbours) = context.actorSelection("../Node"+ tempid.toString)
                                //println("neighbour of" + id +"  is "+ tempid)
                                numOfNeighbours+=1
                          }
                          if(z!=cubeindex)
                          {
                                var a=x
                                var b=y
                                var c=z+1
                                var tempid=((Math.pow(10, numOfDigits*2)*a)+(Math.pow(10, numOfDigits)*b)+(1*c)).toInt
                                 neighbours(numOfNeighbours) = context.actorSelection("../Node"+ tempid.toString)
                                //println("neighbour of" + id +"  is "+ tempid)
                                numOfNeighbours+=1
                          }
                          //println("num neighbours of" + id +"  are  :" + numOfNeighbours)
                          
            case "imp3d" =>
                          var temp=id
                          z = temp % (Math.pow(10, numOfDigits)).toInt
                          temp=temp/(Math.pow(10, numOfDigits)).toInt
                          y=temp % (Math.pow(10, numOfDigits)).toInt
                          temp=temp/(Math.pow(10, numOfDigits)).toInt
                          x=temp % (Math.pow(10, numOfDigits)).toInt
                          
                            
                           

                            if(x!=1)
                            {
                                var a=x-1
                                var b=y
                                var c=z
                                var tempid=((Math.pow(10, numOfDigits*2)*a)+(Math.pow(10, numOfDigits)*b)+(1*c)).toInt
                                neighbours(numOfNeighbours) = context.actorSelection("../Node"+ tempid.toString)
                                //println("neighbour of" + id +"  is "+ tempid)
                                numOfNeighbours+=1
                            }
                            if(x!=cubeindex)
                            {
                                var a=x+1
                                var b=y
                                var c=z
                                var tempid=((Math.pow(10, numOfDigits*2)*a)+(Math.pow(10, numOfDigits)*b)+(1*c)).toInt
                                 neighbours(numOfNeighbours) = context.actorSelection("../Node"+ tempid.toString)
                                //println("neighbour of" + id +"  is "+ tempid)
                                numOfNeighbours+=1
                            }
                            if(y!=1)
                            {
                                var a=x
                                var b=y-1
                                var c=z
                                var tempid=((Math.pow(10, numOfDigits*2)*a)+(Math.pow(10, numOfDigits)*b)+(1*c)).toInt
                                 neighbours(numOfNeighbours) = context.actorSelection("../Node"+ tempid.toString)
                                //println("neighbour of" + id +"  is "+ tempid)
                                numOfNeighbours+=1
                            }
                            if(y!=cubeindex)
                            {
                                  var a=x
                                var b=y+1
                                var c=z
                                var tempid=((Math.pow(10, numOfDigits*2)*a)+(Math.pow(10, numOfDigits)*b)+(1*c)).toInt
                                 neighbours(numOfNeighbours) = context.actorSelection("../Node"+ tempid.toString)
                                //println("neighbour of" + id +"  is "+ tempid)
                                numOfNeighbours+=1
                            }
                            if(z!=1)
                            {
                                  var a=x
                                var b=y
                                var c=z-1
                                var tempid=((Math.pow(10, numOfDigits*2)*a)+(Math.pow(10, numOfDigits)*b)+(1*c)).toInt
                                 neighbours(numOfNeighbours) = context.actorSelection("../Node"+ tempid.toString)
                                //println("neighbour of" + id +"  is "+ tempid)
                                numOfNeighbours+=1
                            }
                            if(z!=cubeindex)
                            {
                                var a=x
                                var b=y
                                var c=z+1
                                var tempid=((Math.pow(10, numOfDigits*2)*a)+(Math.pow(10, numOfDigits)*b)+(1*c)).toInt
                                neighbours(numOfNeighbours) = context.actorSelection("../Node"+ tempid.toString)
                                //println("neighbour of" + id +"  is "+ tempid)
                                numOfNeighbours+=1
                            }
                            
                            var randx=Random.nextInt(cubeindex)+1
                            var randy=Random.nextInt(cubeindex)+1
                            var randz=Random.nextInt(cubeindex)+1

                            while(((randx-x).abs + (randy-y).abs + (randz-z).abs)<=1 )
                            {
                                   randx=Random.nextInt(cubeindex)+1
                                   randy=Random.nextInt(cubeindex)+1
                                   randz=Random.nextInt(cubeindex)+1
                            }

                            var tid=((Math.pow(10, numOfDigits*2)*randx)+(Math.pow(10, numOfDigits)*randy)+(1*randz)).toInt
                            neighbours(numOfNeighbours) = context.actorSelection("../Node"+ tid.toString)
                            //println("random neighbour of" + id +"  is "+ tid.toString)
                            numOfNeighbours+=1
                            //println("num neighbours of" + id +"  are  :" + numOfNeighbours)











        }

        
        






        def receive = 
        {
            case  Rumour(str)=> 
                                receivedrumour+=1
                                
                                if(receivedrumour==1)
                                {      
                                       val master = system.actorSelection("/user/Master")
                                       master ! Done("hi")
                                       val cancellable =system.scheduler.schedule(0 milliseconds,50 milliseconds,self,Clocktickrumour("rumour"))////

                                }

                               

                                

            case Pushsum(snewtemp,wnewtemp)=> 
                                      
                                      sprev=s
                                      wprev=w
                                      s=s+snewtemp
                                      w=w+wnewtemp

                                      receivedpushsum+=1
                                      if(receivedpushsum==1)
                                      {
                                      val cancellablePushsum =system.scheduler.schedule(0 milliseconds,250 milliseconds,self,Clocktickpushsum)////


                                      }
                                      

                                                       

                                                                           

                                      
                                 

                                      

                                      



            case Clocktickrumour(str)=> 
                                        rand=Random.nextInt(numOfNeighbours)
                                        neighbours(rand) ! Rumour(str)
                                        counter+=1
                                        
                                        if(counter==10)////
                                       {
                                                 
                                                   println("rumour limit reached for node "+ id)
                                                   context.stop(self)
                                                   
                                                                   
                                        
                                       }


            case Clocktickpushsum=> 
                                                

                                              
                                              
                                                        
                                                        s=s/2
                                                        w=w/2
                                                        
                                                        var rand=Random.nextInt(numOfNeighbours)
                                                        neighbours(rand) ! Pushsum(s,w)
                                                        
                                                        
                                                        if( ((sprev/wprev)-(s/w)).abs < Math.pow(10, -10)  )////
                                                        {

                                                                counter+=1
                                                                if(counter==5)////
                                                                {
                                                                println("pushsum result is for node " +id+" : " + s/w)
                                                                val master = system.actorSelection("/user/Master")
                                                                master ! PushsumDone
                                                                context.stop(self)


                                                               
                                                                }

                                                              


                                                        }


                case Die()=> 
                          println("node " + id + " is dead.")
                          context.stop(self)

                                              





                                              


                                                              




                                
                               



          }

}
}






















                                
                                
          










