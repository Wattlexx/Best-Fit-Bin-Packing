import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.ceil
import kotlin.random.Random

fun generatePackages() =
    sequence {
        val i = 100
        while (true) yield((i*Random.nextFloat()).toInt())
    }

fun generateBins() =
    sequence {
        var i = 0
        while (true) yield(Bin(i++))
    }

fun packBins (packages: Sequence<Int>, bins: Sequence<Bin>, numOfPackages: Int): MutableList<Bin> {
    val storageContainer: MutableList<Bin> = ArrayList()
    val iterPacks = packages.iterator()
    val iterBins = bins.iterator()
    var pack = iterPacks.next()
    var bin = iterBins.next()

    val total = ArrayList<Int>()
    for (i in 0 until numOfPackages) {
        when (i){
            0 -> {
                bin.addToBin(pack)          //add pack to bin
                storageContainer.add(bin)   //add bin to storageContainer
            }
            else -> {
                val least = storageContainer.filter{ it.availableSpace >= pack }.map{ it.availableSpace - pack}.sortedDescending()
                if (least.isNotEmpty()) {
                    val best = storageContainer.filter { (it.availableSpace - pack) == least[least.lastIndex] }
                    storageContainer[(best[0].binNumber)].addToBin(pack)
                } else {
                    val aBin = iterBins.next()
                    aBin.addToBin(pack)
                    storageContainer.add(aBin)
                }
            }
        }
        print("$pack ")
        total.add(pack)
        pack = iterPacks.next()
    }
    println()
    val totalOfItems = total.reduce{ acc, d -> acc + d }
    println("Total room required: $totalOfItems ")
    println("Will need at least ${(totalOfItems/100)+1} bins.\n")
    return storageContainer
}

fun main() {

/*    val reader = Scanner(System.`in`)
    print("How many items do you have?")
    val numItems = reader.nextInt()*/



    val packs = generatePackages()
    val bins = generateBins()
    val loadedContainers = packBins(packs, bins, 100)

    loadedContainers.forEach { println("Bin ${it.binNumber} Rem space: ${it.availableSpace} ${it.items}") }
}

