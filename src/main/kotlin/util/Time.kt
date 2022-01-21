package util

object Time {
    private val beginTime = System.nanoTime()
    fun getTime() : Double =  (System.nanoTime() - beginTime) * 1e-9
}