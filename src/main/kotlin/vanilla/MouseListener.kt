package vanilla

import org.lwjgl.glfw.GLFW

object MouseListener{
    private var scroll = Pair(0.0, 0.0)
    private var pos = Pair(0.0 ,0.0)
    private var lastPos = Pair(0.0, 0.0)
    private var mouseButtonState = arrayOf(false, false, false)
    private var isDragging = false


    fun mousePosCallback(glfwWindow : Long, xPos: Double, yPos: Double ) {
        lastPos = pos
        pos = Pair(xPos, yPos)
        isDragging = mouseButtonState.contains(true)
    }

    fun mouseButtonCallback(glfwWindow : Long, button : Int, action : Int, mods : Int ) {
        if (button >= mouseButtonState.size) return

        mouseButtonState[button] = action == GLFW.GLFW_PRESS
        if (action == GLFW.GLFW_RELEASE) isDragging = false
    }

    fun mouseScrollCallback(glfwWindow: Long, xOffset: Double, yOffset: Double) {
        scroll = Pair(xOffset, yOffset)
    }

    private fun updateMouseData() {
        scroll = Pair(0.0, 0.0)
        lastPos = pos
    }

    // return change in mousePosition
    fun deltaPos() : Pair<Double, Double> {return Pair(lastPos.first - pos.first, lastPos.second - pos.second) }

    fun mouseButtonDown(button :Int) : Boolean {
        return if (button >= mouseButtonState.size) false else mouseButtonState[button]
    }
}