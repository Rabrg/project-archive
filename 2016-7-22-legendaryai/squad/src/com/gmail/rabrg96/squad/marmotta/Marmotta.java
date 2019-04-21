package com.gmail.rabrg96.squad.marmotta;

import org.apache.marmotta.client.ClientConfiguration;
import org.apache.marmotta.client.MarmottaClient;
import org.apache.marmotta.client.model.sparql.SPARQLResult;

/**
 * Created by Rabrg on 7/12/2016.
 */
public class Marmotta {

    public static void main(final String[] args) {
        try {
            final ClientConfiguration configuration = new ClientConfiguration("http://desktop-4c0usdm:8080/marmotta/");
            final MarmottaClient client = new MarmottaClient(configuration);
            final SPARQLResult result = client.getSPARQLClient().select("SELECT * WHERE { ?subject ?property ?object } LIMIT 10");
            System.out.println(result);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
