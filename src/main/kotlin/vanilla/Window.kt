package vanilla

import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import util.Time
import kotlin.properties.Delegates

object Window {
    const val width: Int = 1920
    const val height : Int = 1080
    const val title : String = "Vanilla"
    private var glfwWindow by Delegates.notNull<Long>();


    private val gameScene = GameScene()
    private val editorScene = LevelEditor()
    private var curScene : Scene = editorScene


    fun switchScene() {
        curScene = if (curScene is LevelEditor) {
            gameScene
        } else {
            editorScene
        }
    }

    fun run() {
        println("Creating  $width x $height window with name $title")
        initGLFWWindow()
        loop()

        // free the memory
        glfwFreeCallbacks(glfwWindow)
        glfwDestroyWindow(glfwWindow)

        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
    }

    private fun initGLFWWindow() {
        GLFWErrorCallback.createPrint(System.err).set()
        if (!GLFW.glfwInit()) {
            throw IllegalStateException("Unable to init GLFW")
        }

        // setup window hints
        GLFW.glfwDefaultWindowHints()
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE)
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE)
        GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE)

        // create the glfw window
        glfwWindow = GLFW.glfwCreateWindow(width, height, title, 0, 0)
        if (glfwWindow <= 0) throw RuntimeException("Failed to create the GLFW Window")



        // setup callbacks
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback)
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback)
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback)
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback)

        // make the OpenGL context current
        GLFW.glfwMakeContextCurrent(glfwWindow)
        // enable v-sync
//        GLFW.glfwSwapInterval(1)

        // make the window visible
        GLFW.glfwShowWindow(glfwWindow)

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities()
    }

    private fun loop() {
        var startTime = Time.getTime()
        GL11.glClearColor(1.0f, 0.0f, 0.0f, 0.0f)
        while(!GLFW.glfwWindowShouldClose(glfwWindow)) {
            startTime = Time.getTime()
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
            GLFW.glfwSwapBuffers(glfwWindow)
            GLFW.glfwPollEvents()

            if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
                println("SPACE IS PRESSED")
            }

            var dt = Time.getTime() - startTime
        }
    }


}