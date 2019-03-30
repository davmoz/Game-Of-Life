package game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application {
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int CELL_SIZE = 10;
	private static final int BUTTON_SPACING = 20;
	private static Platform gameOfLife;
	private static Timeline timeline;
	private Label born;
	private Label dead;

	@Override
	public void start(Stage primaryStage) {

		gameOfLife = new Platform(CELL_SIZE, WIDTH, HEIGHT);
		born = new Label("Born: " + gameOfLife.getBorn());
		dead = new Label("Dead: " + gameOfLife.getDead());

		BorderPane pane = new BorderPane();
		Group root = new Group();
		HBox panel = new HBox();
		Button randomBtn = new Button("Randomize");
		Button startBtn = new Button("Start");
		Button stopBtn = new Button("Stop");
		Button resetBtn = new Button("Reset");
		Spinner<Double> speed = new Spinner<>();
		Label speedLabel = new Label("Speed (s): ");
		speed.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.01, 6.0, 0.01, 0.05));
		Label generationLabel = new Label("Generations (0 == infinity): ");
		Spinner<Integer> generations = new Spinner<>();
		generations.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5000000, 0, 500000));

		pane.setOnMousePressed(e -> {
			gameOfLife.toggleCell(e.getX(), e.getY());
		});

		pane.setOnMouseDragged(e -> {
			gameOfLife.toggleCell(e.getX(), e.getY());
		});

		randomBtn.setOnAction(e -> {
			gameOfLife.randomize();
		});

		startBtn.setOnAction(e -> {
			randomBtn.setDisable(true);
			resetBtn.setDisable(true);
			startBtn.setDisable(true);
			speed.setDisable(true);
			generations.setDisable(true);
			updateTimeLineDuration(speed.getValue(), generations.getValue());
			timeline.play();
		});

		stopBtn.setOnAction(e -> {
			randomBtn.setDisable(true);
			resetBtn.setDisable(false);
			startBtn.setDisable(false);
			timeline.pause();
		});

		resetBtn.setOnAction(e -> {

			generations.setDisable(false);
			randomBtn.setDisable(false);
			speed.setDisable(false);
			gameOfLife.reset();
			born.setText("Born: " + gameOfLife.getBorn());
			dead.setText("Dead: " + gameOfLife.getDead());
		});

		panel.getChildren().addAll(born, speedLabel, speed, randomBtn, startBtn, stopBtn, resetBtn, generationLabel,
				generations, dead);
		panel.setSpacing(BUTTON_SPACING);
		panel.setAlignment(Pos.CENTER);
		panel.setStyle("-fx-background-color: gray;");
		root.getChildren().addAll(gameOfLife.getCells());

		pane.setCenter(root);
		pane.setBottom(panel);

		Scene scene = new Scene(pane);
		primaryStage.setTitle("Game Of Life");
		primaryStage.getIcons().add(new Image("icon.png"));
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void updateTimeLineDuration(double duration, int numOfGenerations) {

		KeyFrame updateRatio = new KeyFrame(Duration.seconds(duration), e -> {

			gameOfLife.updateAll();
			born.setText("Born: " + gameOfLife.getBorn());
			dead.setText("Dead: " + gameOfLife.getDead());
		});
		timeline = new Timeline(updateRatio);

		timeline.setCycleCount((numOfGenerations == 0) ? Animation.INDEFINITE : numOfGenerations);

	}
}
