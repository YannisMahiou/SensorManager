package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import metier.ISensors;
import metier.Sensor;
import metier.algorithm.IAlgorithmStrategy;
import metier.algorithm.FenetreGlissante;
import metier.algorithm.IntervalValue;
import metier.algorithm.RandomValue;
import model.SensorModel;

public class SensorListViewUserControlController {

    @FXML
    Label sensorName;
    @FXML
    ImageView state;
    @FXML
    ComboBox algoBox;

    Image start = new Image("/img/play.png");
    Image stop = new Image("/img/stop.png");

    ObservableList<ISensors> sensorModel;
    ISensors sensor;

    public void sup(){ sensorModel.remove(sensor); }

    public void setSensorModel(ObservableList<ISensors> sensorModel)
    {
        this.sensorModel = sensorModel;
    }
    public void setSensor(ISensors sensor)
    {
        this.sensor = sensor;
    }
    public ISensors getSensor(){return this.sensor;}

    public void startAndStopThread(){
        IAlgorithmStrategy generator = null;
        String algorithm = null;

        try {
            algorithm = algoBox.getValue().toString();
        } catch (NullPointerException e){
            algorithm = "Random";
        }
        switch(algorithm){
            case "Random" :
                generator = new RandomValue(sensor);
                break;

            case "Interval" :
                generator = new IntervalValue(sensor);
                break;

            case "RandInterval" :
                //generator = new FenetreGlissante(sensor);
                break;

            default :
                System.out.println("pas d'algo de selectionné");
                break;
        }

        if(sensor.getThread() != null){
            state.setImage(start);
            sensor.stopSensorThread();
        }else {
            sensor.startSensorThread(generator);
            state.setImage(stop);
        }
    }

    public void setLabel(String value) {
        sensorName.setText(value);
    }

}
