package dad.maven.imc;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IMC extends Application {

	private Label pesoLabel;
	private Label kgLabel;
	private TextField pesoText;
	private Label alturaLabel;
	private Label cmLabel;
	private TextField alturaText;
	private Label imcLabel;
	private Label resulLabel;
	private Label formLabel;

	DoubleProperty pesoProperty = new SimpleDoubleProperty();
	DoubleProperty alturaProperty = new SimpleDoubleProperty();

	@Override
	public void start(Stage primaryStage) throws Exception {

		pesoLabel = new Label("Peso: ");
		pesoText = new TextField();
		pesoText.maxWidth(70);
		kgLabel = new Label(" kg.");

		alturaLabel = new Label("Altura: ");
		alturaText = new TextField();
		alturaText.maxWidth(70);
		cmLabel = new Label(" cm.");

		imcLabel = new Label("IMC: ");
		resulLabel = new Label();
		formLabel = new Label();

		HBox pesoHbox = new HBox();
		pesoHbox.setSpacing(5);
		pesoHbox.getChildren().addAll(pesoLabel, pesoText, kgLabel);
		pesoHbox.setAlignment(Pos.CENTER);

		HBox alturaHbox = new HBox();
		alturaHbox.setSpacing(5);
		alturaHbox.getChildren().addAll(alturaLabel, alturaText, cmLabel);
		alturaHbox.setAlignment(Pos.CENTER);

		HBox imcHbox = new HBox();
		imcHbox.setSpacing(5);
		imcHbox.getChildren().addAll(imcLabel, formLabel);
		imcHbox.setAlignment(Pos.CENTER);

		VBox root = new VBox();
		root.setSpacing(5);
		root.setAlignment(Pos.CENTER);
		root.setFillWidth(false);
		root.getChildren().addAll(pesoHbox, alturaHbox, imcHbox, resulLabel);

		Scene scene = new Scene(root, 320, 200);

		primaryStage.setScene(scene);
		primaryStage.setTitle("IMC");
		primaryStage.show();

		Bindings.bindBidirectional(pesoText.textProperty(), pesoProperty, new NumberStringConverter());
		Bindings.bindBidirectional(alturaText.textProperty(), alturaProperty, new NumberStringConverter());

		DoubleBinding mBinding = alturaProperty.divide(100);

		DoubleBinding mcuadrado = mBinding.multiply(mBinding);

		DoubleBinding resulFinal = pesoProperty.divide(mcuadrado);

		formLabel.textProperty().bind(Bindings.concat(resulFinal.asString()));

		formLabel.textProperty().addListener((o, ov, nv) -> {

			double num = resulFinal.doubleValue();

			if (num < 18.5) {
				resulLabel.setText("Peso inferior al normal");
			}
			if (num >= 18.5 && num < 25) {
				resulLabel.setText("Normal");
			}
			if (num >= 25 && num < 30) {
				resulLabel.setText("Peso superior al normal");
			}
			if (num >= 30) {
				resulLabel.setText("Obesidad");
			}
		});
	}

	public static void main(String[] args) {
		launch(args);

	}

}
