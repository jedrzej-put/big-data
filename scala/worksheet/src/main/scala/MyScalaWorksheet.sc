//val stolice = List("Warszawa", "Berlin", "Praga")
//val kraje = List("Polska", "Niemcy", "Czechy")
//val stoliceKrajow = (kraje zip stolice).toMap
//val capOfCzechy = stoliceKrajow.get("Czechy")
//for (capital <- capOfCzechy) println(capital)
//// task2

//def australiaZones():Array[String] = {
//  val s = java.util.TimeZone.getAvailableIDs()
//  val sorted_s = s.filter(_.contains("Australia/")).map(x => x.split("/").last).sorted
//  sorted_s
//}
//var rand = new util.Random(45)
//println(rand.shuffle(australiaZones().toList).map(_.hashCode()).reduceRight(_-_))

// task 3
// tab = Seq.fill(100)(util.Random.nextInt(200)-100).toArray

//def funTab1(arr:Array[Int]):Array[Int] = {
//  arr.sorted
//}
//var rand = new util.Random(28)
//var tab = Seq.fill(100)(rand.nextInt(1000000)-500000).toArray
//rand = new util.Random(28)
//var res = rand.shuffle(funTab1(tab).toList).map(_.hashCode()).reduceRight(_-_)

//def funTab2(arr:Array[Int]):Array[Int] = {
//  val even = arr.filter(x=>(x%2==0)).sorted
//  val odd = arr.filter(x=>(x%2==1)).sorted.reverse
//  even ++ odd
//}
//var rand = new util.Random(187)
//var tab = Seq.fill(100)(rand.nextInt(1000000)-500000).toArray
//rand = new util.Random(187)
//var res = rand.shuffle(funTab2(tab).toList).map(_.hashCode()).reduceRight(_-_)

//def funTab3(arr:Array[Int]):Array[Int] = {
//  arr.sortBy(_.abs)
//}
//var rand = new util.Random(3)
//var tab = Seq.fill(100)(rand.nextInt(1000000)-500000).toArray
//rand = new util.Random(3)
//var res = rand.shuffle(funTab3(tab).toList).map(_.hashCode()).reduceRight(_-_)

//task4
//import scala.io.Source
//val file = "http://www.textfiles.com/etext/AUTHORS/DOYLE/doyle-hound-383.txt"
//val html = Source.fromURL(file)
//val s = html.mkString.replaceAll("""[\p{Punct}]""", "").toLowerCase()
//val tokens = s.split("\\W+").toList
//
//val words = tokens.groupBy((word: String) => word).map(x=>(x._1, x._2.length))
//
//val results = words.toSeq.sortBy(_._2).reverse
//val top1 = words.filter(_._1.contains("murder"))
//val top2 = words.filter(_._1.contains("scream"))
//val top3 = words.filter(_._1.contains("watson"))

//task 5
//val a = Array("Mr.","Sherlock","Holmes,","who","was",
//  "usually","very","late","in","the","mornings,",
//  "save","upon","those","not","infrequent","occasions","when","he","was","up",
//  "all","night,","was","seated","at","the","breakfast","table.")
//println(a.reduceLeft())

//task 6
//import java.util.TimeZone.getAvailableIDs
//def howManyZonesInRegions(): Seq[(String, Int)] = {
//  val zones = getAvailableIDs();
//  zones.filter(_.contains("/"))
//    .map(_.split("/"))
//    .groupBy(x => x.head)
//    .map(x => (x._1, x._2.length))
//    .toSeq
//    .filter(x => Array("US", "Asia", "Europe").contains(x._1))
//}
//howManyZonesInRegions()