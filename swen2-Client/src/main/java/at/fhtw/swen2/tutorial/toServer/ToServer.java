package at.fhtw.swen2.tutorial.toServer;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class ToServer {

    String serverUrl = "http://localhost:8080";

    public <T> T postReq(String dataEndpoint, T data) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<T> response = restTemplate.postForEntity(serverUrl + dataEndpoint, data, (Class<T>) data.getClass());
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Data processed successfully");
                return response.getBody();
            } else {
                System.out.println("Error processing data");
            }
        } catch (HttpClientErrorException e) {
            System.out.println("Error communicating with server");
        }
        return null;
    }
    public <T> T putReq(String dataEndpoint,  T data) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpEntity<T> request = new HttpEntity<>(data);
            ResponseEntity<T> response = restTemplate.exchange(serverUrl + dataEndpoint, HttpMethod.PUT, request, (Class<T>) data.getClass());
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Data processed successfully");
                return response.getBody();
            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                System.out.println("Tour not found");
            } else {
                System.out.println("Error processing data");
            }
        } catch (HttpClientErrorException e) {
            System.out.println("Error communicating with server");
        }
        return null;
    }

    public <T> T getReq(String endpoint, ParameterizedTypeReference<T> responseType) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<T> response = restTemplate.exchange(
                    serverUrl + endpoint,
                    HttpMethod.GET,
                    null,
                    responseType
            );
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Data retrieved successfully");
                System.out.println("this->" + response);
                return response.getBody();
            } else {
                System.out.println("Error retrieving data");
            }
        } catch (HttpClientErrorException e) {
            System.out.println("Error communicating with server"+e);
        }
        return null;
    }

    public boolean deleteReq(String endpoint, Long tourId) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.delete(serverUrl + endpoint + "/" + tourId);
            System.out.println("Tour deleted successfully");
            return true;
        } catch (HttpClientErrorException e) {
            System.out.println("Error communicating with server" +e);
        }
        return false;
    }

}
