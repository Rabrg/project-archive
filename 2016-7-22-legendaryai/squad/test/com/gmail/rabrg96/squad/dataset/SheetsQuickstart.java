package com.gmail.rabrg96.squad.dataset;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SheetsQuickstart {

    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";

    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/sheets.googleapis.com-java-quickstart.json");

    private static FileDataStoreFactory DATA_STORE_FACTORY;

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static HttpTransport HTTP_TRANSPORT;

    private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS);

    public static ValueRange response;
    public static UpdateValuesResponse request;

    public static void main(String[] args) throws Exception {
//        SheetsQuickstart.setValue("Sheet1", "A1", "B");
    }

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public static Credential authorize() throws IOException {
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(
                        new FileInputStream("client_secret_869006319608-l3rcqgckq10dd6mq6psfj1gcne4ejrfe.apps.googleusercontent.com.json")));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline").build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    public static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
    }

    public static void setValue(String SheetName, String RowStart, String RowEnd, List<Object> value) throws IOException {
        Sheets service = getSheetsService();
        String spreadsheetId = "1tKRtIyk-mvRS_zxWco-ufNJ4q0DTDYCUHgX21z5GjII";
        String range = RowStart + ":" + RowEnd;

        List<List<Object>> arrData = getData(value);

        ValueRange oRange = new ValueRange();
        oRange.setRange(range);
        oRange.setValues(arrData);

        List<ValueRange> oList = new ArrayList<>();
        oList.add(oRange);

        BatchUpdateValuesRequest oRequest = new BatchUpdateValuesRequest();
        oRequest.setValueInputOption("RAW");
        oRequest.setData(oList);

        service.spreadsheets().values().batchUpdate(spreadsheetId, oRequest).execute();
    }

    public static List<List<Object>> getData(List<Object> value) {
        List<List<Object>> data = new ArrayList<List<Object>>();
        data.add(value);
        return data;
    }

}