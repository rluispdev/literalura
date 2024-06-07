package rluispdev.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ManagerGutendex {
    public String getData(String address) {
        if (address == null || address.isEmpty()) {
            throw new IllegalArgumentException("O endereço não pode ser nulo ou vazio");
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address))
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (RuntimeException e) {
            throw new RuntimeException("Runtime exception during HTTP request", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted exception during HTTP request", e);
        } catch (IOException e) {
            throw new RuntimeException("IO exception during HTTP request", e);
        }

        return response.body();
    }
}