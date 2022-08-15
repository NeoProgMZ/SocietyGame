package pl.neoprog;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.VisUI.SkinScale;
import com.kotcrab.vis.ui.widget.Menu;
import com.kotcrab.vis.ui.widget.MenuBar;
import com.kotcrab.vis.ui.widget.MenuItem;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class SocietyGame extends ApplicationAdapter {
	private MenuBar menuBar;
	private Stage stage;

	@Override
	public void create () {
		VisUI.setSkipGdxVersionCheck(true);
		VisUI.load(SkinScale.X1);

		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		Table root = new Table();
		root.setFillParent(true);
		stage.addActor(root);

		menuBar = new MenuBar();
		root.add(menuBar.getTable()).growX().row();
		root.add().grow();

		createMenus();

		stage.addActor(new TestWindow());
	}

	private void createMenus () {
		Menu startTestMenu = new Menu("start test");
		Menu fileMenu = new Menu("file");
		Menu editMenu = new Menu("edit");

		startTestMenu.addItem(new MenuItem("listview", new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				stage.addActor(new TestListView());
			}
		}));

		startTestMenu.addItem(new MenuItem("tabbed pane", new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				stage.addActor(new TestTabbedPane());
			}
		}));

		startTestMenu.addItem(new MenuItem("collapsible", new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				stage.addActor(new TestCollapsible());
			}
		}));

		//creating dummy menu items for showcase
		fileMenu.addItem(new MenuItem("menuitem #1"));
		fileMenu.addItem(new MenuItem("menuitem #2").setShortcut("f1"));
		fileMenu.addItem(new MenuItem("menuitem #3").setShortcut("f2"));

		editMenu.addItem(new MenuItem("menuitem #4"));
		editMenu.addItem(new MenuItem("menuitem #5"));
		editMenu.addSeparator();
		editMenu.addItem(new MenuItem("menuitem #6"));
		editMenu.addItem(new MenuItem("menuitem #7"));

		menuBar.addMenu(startTestMenu);
		menuBar.addMenu(fileMenu);
		menuBar.addMenu(editMenu);
	}

	@Override
	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void dispose () {
		VisUI.dispose();
		stage.dispose();
	}
}