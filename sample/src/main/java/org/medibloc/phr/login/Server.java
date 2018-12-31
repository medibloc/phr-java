package org.medibloc.phr.login;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.medibloc.panacea.crypto.ECDSASignature;
import org.medibloc.panacea.crypto.Keys;
import org.medibloc.panacea.crypto.Sign;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Server {
    private static String randomNonce = null;
    private static final String pubKey = "02a48da8acc94fedf2aa0556e238eac3a1ec1026762299b599f022792f60c4c80f";

    public static void main(String[] args) throws Exception {
        int port = 8888;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/test", new MyHandler());
        server.createContext("/nonce", new OnNonceRequest());
        server.createContext("/challenge", new OnChallengeRequest());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Listening on " + port + "...");
    }

    static void sendSuccessResponse(HttpExchange t, String response) throws IOException {
        if (response == null) {
            response = "Success";
        }
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    static void sendFailResponse(HttpExchange t, String response) throws IOException {
        if (response == null) {
            response = "Fail";
        }
        t.sendResponseHeaders(400, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            sendSuccessResponse(t, null);
        }
    }

    static class OnNonceRequest implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            byte[] array = new byte[16];
            new Random().nextBytes(array);
            randomNonce = Util.bytesToHex(array);
            System.out.println(randomNonce);

            sendSuccessResponse(t, randomNonce);
        }
    }

    static class OnChallengeRequest implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            // How to limitate only POST?
            Scanner scanner = new Scanner(t.getRequestBody(), "utf-8");
            String line = scanner.useDelimiter("\\A").next();
            String query = line.substring(4);
            byte[] nonce = Util.hexStringToByteArray(query);
            Sign.SignatureData signatureData = new Sign.SignatureData(nonce[0], Arrays.copyOfRange(nonce, 1, 33), Arrays.copyOfRange(nonce, 33, 65));
            ECDSASignature sig = new ECDSASignature(new BigInteger(1, signatureData.getR()), new BigInteger(1, signatureData.getS()));
            try {
                BigInteger derived = Sign.recoverFromSignature(signatureData.getV() - 27, sig, Util.hexStringToByteArray(randomNonce));
                if (!Keys.compressPubKey(derived).equals(pubKey)) {
                    System.out.println("fail");
                    sendFailResponse(t, null);
                    return;
                }
                System.out.println("success");

                sendSuccessResponse(t, null);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
