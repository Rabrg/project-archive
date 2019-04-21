import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class GrabUsernamesSmiteGuru {

	public static void main(String[] args) throws MalformedURLException, IOException {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        } };
        SSLContext sc = null;
		try {
			sc = SSLContext.getInstance("SSL");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
        try {
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        
        	for (int offset = 0; offset < Integer.MAX_VALUE; offset += 25) {
        		final URLConnection conn = new URL("https://smite.guru/leaderboards/getData?queue=" + 2 + "&offset=" + offset).openConnection();
        		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
        		final BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        		String line;
        		while((line = reader.readLine()) != null) {
        			int fromIndex = 0;
        			for (int i = 0; i < 25; i++) {
        				int start = line.indexOf("/stats/", fromIndex);
        				int end = line.indexOf('"', start);
        				System.out.println(line.substring(start, end).substring(7));
        				fromIndex = end;
        			}
        		}
        	}
	}

}
