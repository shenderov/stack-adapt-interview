package me.shenderov.automation.tools;

import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;

/**
 * Test listener can perform actions on different test events(e.g. screenshot on fail).
 * Refer to method names to identify test event types
 *
 * @author Konstantin Shenderov
 *
 */
public class TestListener implements ITestListener {

    private final Boolean influxDBEnabled = Boolean.getBoolean(System.getProperty("enableInfluxDB"));
    private InfluxDBSender sender;

    @Override
    public void onTestStart(ITestResult result) {
        String sessionName;
        if(System.getProperty("jobName") != null && System.getProperty("buildNumber") != null){
            sessionName = String.format("%s - %s Build: %s", result.getTestClass().getRealClass().getSimpleName(), System.getProperty("jobName"), System.getProperty("buildNumber"));
        }else{
            sessionName = result.getTestClass().getRealClass().getSimpleName();
        }
        if(System.getProperty("driverType").equals("browserstack")){
            String sessionId = System.getProperty("remote.session.id");
            try {
                setBrowserstackSessionName(sessionId, sessionName);
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if(influxDBEnabled){
            this.postTestMethodStatus(result, "PASS");
        }
        if(System.getProperty("driverType").equals("browserstack")){
            String sessionId = System.getProperty("remote.session.id");
            try {
                setBrowserstackSessionStatus(sessionId, "passed");
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if(influxDBEnabled){
            this.postTestMethodStatus(result, "FAIL");
        }
        if(System.getProperty("driverType").equals("browserstack")){
            String sessionId = System.getProperty("remote.session.id");
            try {
                setBrowserstackSessionStatus(sessionId, "failed");
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
        }
        ScreenshotTool.takeScreenshot(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if(influxDBEnabled){
            this.postTestMethodStatus(result, "SKIPPED");
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {
        if(influxDBEnabled){
            sender = new InfluxDBSender();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        if(influxDBEnabled){
            this.postTestClassStatus(context);
            sender.close();
        }
    }

    private void postTestMethodStatus(ITestResult iTestResult, String status) {
        Point point = Point
                .measurement("testmethod")
                .time(Instant.now(), WritePrecision.NS)
                .addTag("testclass", iTestResult.getTestClass().getName())
                .addTag("name", iTestResult.getName())
                .addTag("description", iTestResult.getMethod().getDescription())
                .addTag("result", status)
                .addField("duration", (iTestResult.getEndMillis() - iTestResult.getStartMillis()));
        sender.send(point);
    }

    private void postTestClassStatus(ITestContext iTestContext) {
        Point point = Point
                .measurement("testclass")
                .time(Instant.now(), WritePrecision.NS)
                .addTag("name", iTestContext.getAllTestMethods()[0].getTestClass().getName())
                .addField("duration", (iTestContext.getEndDate().getTime() - iTestContext.getStartDate().getTime()));
        sender.send(point);
    }

    private void setBrowserstackSessionStatus(String sessionId, String status) throws URISyntaxException, IOException {
        URI uri = new URI(String.format("https://%s:%s@api.browserstack.com/automate/sessions/%s.json", System.getProperty("browserstackUsername"), System.getProperty("browserstackKey"), sessionId));
        HttpPut putRequest = new HttpPut(uri);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add((new BasicNameValuePair("status", status)));
        nameValuePairs.add((new BasicNameValuePair("reason", "")));
        putRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpClientBuilder.create().build().execute(putRequest);
    }

    private void setBrowserstackSessionName(String sessionId, String name) throws URISyntaxException, IOException {
        URI uri = new URI(String.format("https://%s:%s@api.browserstack.com/automate/sessions/%s.json", System.getProperty("browserstackUsername"), System.getProperty("browserstackKey"), sessionId));
        HttpPut putRequest = new HttpPut(uri);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add((new BasicNameValuePair("name", name)));
        putRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpClientBuilder.create().build().execute(putRequest);
    }
}
