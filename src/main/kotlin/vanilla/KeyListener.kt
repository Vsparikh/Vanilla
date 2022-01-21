package vanilla

import org.lwjgl.glfw.GLFW

object KeyListener {
    private val keyStateArr = BooleanArray(350) { _ -> false }

    fun keyCallback(glfwWindow: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        if (key >= keyStateArr.size) return
        keyStateArr[key] = action == GLFW.GLFW_PRESS
    }

    fun isKeyPressed(key : Int) : Boolean {
        return keyStateArr[key]
    }
}