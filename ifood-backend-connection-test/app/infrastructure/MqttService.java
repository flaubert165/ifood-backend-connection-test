package infrastructure;

import com.typesafe.config.ConfigFactory;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import play.Logger;
import java.util.HashMap;

public class MqttService implements MqttCallbackExtended{

    private MqttClient client;
    private HashMap<String, Subject<MqttMessage>> maps;

    public MqttService() {

        try {
            maps = new HashMap();

            client = new MqttClient(ConfigFactory.load().getString("mqtt.broker.host"),
                    MqttClient.generateClientId(), new MemoryPersistence());
            client.setCallback(this);
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setAutomaticReconnect(true);
            mqttConnectOptions.setKeepAliveInterval(120);
            client.connect(mqttConnectOptions);

        } catch (MqttException e) {
            Logger.error("A conexão com MQTT broker não pôde ser estabelecida!", e);
        }
    }

    public Subject<MqttMessage> observer(String topic) {

        Subject<MqttMessage> subject;

        if (maps.containsKey(topic)) {
            subject = maps.get(topic);
        }
        else {
            try {

                Subject<MqttMessage> createdSubject = PublishSubject.create();
                maps.put(topic, createdSubject);
                client.subscribe(topic, (t, m) -> createdSubject.onNext(m));
                subject = createdSubject;

            } catch (MqttException e) {
                subject = null;
                e.printStackTrace();
            }
        }

        return subject;
    }

    public void publish(String topic, byte[] data, int qos) {
        try {
            MqttMessage mqttMessage = new MqttMessage(data);
            mqttMessage.setQos(qos);
            mqttMessage.setRetained(false);
            client.publish(topic, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {

        if (reconnect) {
            Logger.info("Conexão com broker MQTT reestabelecida com sucesso!");
        }
        else {
            Logger.info("MQTT broker conectado com sucesso!");
        }

    }

    @Override
    public void connectionLost(Throwable cause) {
        Logger.error("A conexão com broker MQTT foi perdida!", cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Logger.error(message.toString());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

}

