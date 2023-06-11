package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.toServer.MapQuestImage;
import at.fhtw.swen2.tutorial.toServer.ToServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(TourManageViewModel.class);
    public String singlePdf(Long id) throws IOException {
        logger.debug("single pdf");
        response = toServer.getReq("/api/pdf/single/" + id, new ParameterizedTypeReference<String>() {});
        return response;
    }
    public String summaryPdf() throws IOException {
        logger.debug("summary pdf");
        response = toServer.getReq("/api/pdf/summary", new ParameterizedTypeReference<String>() {});
        return response;
    }
}
