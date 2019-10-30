class Bin (val binNumber: Int){

    var availableSpace = 100
    val items: MutableList<Int> = ArrayList()

    fun addToBin(element: Int) {
        if (availableSpace < element){
            println("cannot add $element to bin $binNumber")
        } else {
            availableSpace -= element
            items.add(element)
        }
    }
}