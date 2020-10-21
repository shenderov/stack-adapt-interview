package me.shenderov.automation.tools;

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
import java.util.ArrayList;

/**
 * Test listener can perform actions on different test events(e.g. screenshot on fail).
 * Refer to method names to identify test event types
 *
 * @author Konstantin Shenderov
 *
 */
public class TestListener implements ITestListener {

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
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
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
