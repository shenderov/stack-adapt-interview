package me.shenderov.automation.tools;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.write.Point;

public class InfluxDBSender {

    private final InfluxDBClient client;

    public InfluxDBSender() {
        this.client = InfluxDBClientFactory.create(System.getProperty("influxUrl"), System.getProperty("influxToken").toCharArray());
    }

    public void send(final Point point){
        WriteApiBlocking writeApi = client.getWriteApiBlocking();
        writeApi.writePoint(System.getProperty("influxBucket"), System.getProperty("influxOrg"), point);
    }

    public void close(){
        client.close();
    }
}
