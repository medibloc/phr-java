package org.medibloc.phr.login;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.medibloc.panacea.crypto.ECKeyPair;
import org.medibloc.panacea.crypto.Keys;
import org.medibloc.panacea.crypto.Sign;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();
    private static final String passphrase = "medibloc samsung hospital blockchain service";

    private static String requestNonce() throws Exception {
        HttpGet httpGet = new HttpGet("http://localhost:8888/nonce");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        String nonce;
        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            Scanner scanner = new Scanner(entity.getContent(), "utf-8");
            nonce = scanner.next();
            System.out.println(nonce);
            System.out.println("Done Request Nonce");
        } finally {
            response.close();
        }
        return nonce;
    }

    private static String makeSignature(String nonce) throws Exception {
        ECKeyPair keypair = Keys.generateKeysFromPassphrase(passphrase);
        Sign.SignatureData signatureData = Sign.signMessage(Util.hexStringToByteArray(nonce), keypair);

        byte[] message = new byte[65];
        message[0] = signatureData.getV();
        System.arraycopy(signatureData.getR(), 0, message, 1, 32);
        System.arraycopy(signatureData.getS(), 0, message, 33, 32);

        return Util.bytesToHex(message);
    }

    private static void challengeRequest(String signature) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8888/challenge");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("SIG", signature));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse response = httpClient.execute(httpPost);

        try {
            System.out.println(response.getStatusLine());
            System.out.println("Done Challenge");
        } finally {
            response.close();
        }
    }

    public static void main(String[] args) throws Exception {
        String nonce = requestNonce();

        String signature = makeSignature(nonce);
        challengeRequest(signature);
    }
}
