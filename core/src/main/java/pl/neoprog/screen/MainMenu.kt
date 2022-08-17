package pl.neoprog.screen

import com.badlogic.gdx.Gdx
//import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.Menu
import com.kotcrab.vis.ui.widget.MenuBar
import com.kotcrab.vis.ui.widget.MenuItem
import ktx.actors.stage
import ktx.app.KtxGame
import ktx.app.KtxScreen
import pl.neoprog.TestCollapsible
import pl.neoprog.TestListView
import pl.neoprog.TestTabbedPane
import pl.neoprog.TestWindow
import com.badlogic.gdx.Input
import ktx.scene2d.actors
import ktx.scene2d.vis.*

class MainMenu(val game: KtxGame<KtxScreen>) : KtxScreen {
//    private val mainMenuBar: MenuBar by lazy { MenuBar() }
    private val stage: Stage by lazy { stage(viewport = ScreenViewport()) }

    init {
        VisUI.setSkipGdxVersionCheck(true)
        VisUI.load(VisUI.SkinScale.X1)
        Gdx.input.inputProcessor = stage
        val root = Table()
        root.setFillParent(true)
        stage.addActor(root)
//        TODO move to game screen.
        createMenuBar(stage)
//        root.add(mainMenuBar.table).growX().row()
        root.add().grow()
        createMenus()
        stage.addActor(TestWindow())
    }

    override fun render(delta: Float) {
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(Gdx.graphics.deltaTime.coerceAtMost(1 / 30f))
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    private fun createMenus() {
        val startTestMenu = Menu("start test")
        val fileMenu = Menu("file")
        val editMenu = Menu("edit")
        startTestMenu.addItem(MenuItem("listview", object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
//                TODO change to screen.
                stage.addActor(TestListView())
            }
        }))
        startTestMenu.addItem(MenuItem("tabbed pane", object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
//                TODO change to screen.
                stage.addActor(TestTabbedPane())
            }
        }))
        startTestMenu.addItem(MenuItem("collapsible", object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
//                TODO change to screen.
                stage.addActor(TestCollapsible())
            }
        }))

        //creating dummy menu items for showcase
        fileMenu.addItem(MenuItem("menuitem #1"))
        fileMenu.addItem(MenuItem("menuitem #2").setShortcut("f1"))
        fileMenu.addItem(MenuItem("menuitem #3").setShortcut("f2"))
        editMenu.addItem(MenuItem("menuitem #4"))
        editMenu.addItem(MenuItem("menuitem #5"))
        editMenu.addSeparator()
        editMenu.addItem(MenuItem("menuitem #6"))
        editMenu.addItem(MenuItem("menuitem #7"))
//        mainMenuBar.addMenu(startTestMenu)
//        mainMenuBar.addMenu(fileMenu)
//        mainMenuBar.addMenu(editMenu)
    }

    private fun createMenuBar(stage: Stage) {
        stage.actors {
            visTable {
                menuBar { cell ->
                    cell.top().growX().expandY().row()
                    menu("File") {
                        menuItem("New") {
                            subMenu {
                                menuItem("Project")
                                menuItem("Module")
                                menuItem("File")
                            }
                        }
                        menuItem("Open") { /**/ }
                    }
                    menu ("Edit") {
                        menuItem("Undo") {
                            setShortcut(Input.Keys.CONTROL_LEFT, Input.Keys.Z)
                        }
                        menuItem("Redo") { /**/ }
                    }
                }
                setFillParent(true)
            }
        }
    }
}