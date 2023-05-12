package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.toServer.MapQuestImage;
import at.fhtw.swen2.tutorial.toServer.ToServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class TourManageViewModel {
    @Autowired
    ToServer toServer;
    String response;
    public String singlePdf(Long id) throws IOException {
        response = toServer.getReq("/api/pdf/single/" + id, new ParameterizedTypeReference<String>() {});
        return response;
    }
    public String summaryPdf() throws IOException {
        response = toServer.getReq("/api/pdf/summary", new ParameterizedTypeReference<String>() {});
        return response;
    }
}
