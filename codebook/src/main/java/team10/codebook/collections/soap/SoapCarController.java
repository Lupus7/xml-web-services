package team10.codebook.collections.soap;

import com.car_rent.agent_api.BrandDetails;
import com.car_rent.agent_api.GetAllSpecsRequest;
import com.car_rent.agent_api.GetAllSpecsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import team10.codebook.models.dto.CodebookItemDTO;
import team10.codebook.services.CodebookService;
import team10.codebook.soap.SoapProperties;

import java.util.stream.Collectors;

@Endpoint
public class SoapCarController {

    @Autowired
    CodebookService codebookService;

    @PayloadRoot(namespace = SoapProperties.NAMESPACE_URI, localPart = "getAllSpecsRequest")
    @ResponsePayload
    public GetAllSpecsResponse getAllSpecsResponse(@RequestPayload GetAllSpecsRequest request) {
        GetAllSpecsResponse response = new GetAllSpecsResponse();
        response.getBrands().addAll(
                codebookService
                        .getAllBrands()
                        .stream()
                        .map(brand -> {
                            BrandDetails bd = new BrandDetails();
                            bd.setName(brand.getName());
                            bd.getModels().addAll(codebookService
                                    .getAllModelsByBrand(brand.getName())
                                    .stream()
                                    .map(CodebookItemDTO::getName)
                                    .collect(Collectors.toList()));
                            return bd;
                        })
                        .collect(Collectors.toList())
        );
        response.getCarClasses().addAll(codebookService.getAllClasses().stream().map(CodebookItemDTO::getName).collect(Collectors.toList()));
        response.getFuels().addAll(codebookService.getAllFuels().stream().map(CodebookItemDTO::getName).collect(Collectors.toList()));
        response.getTransmissions().addAll(codebookService.getAllTransmissions().stream().map(CodebookItemDTO::getName).collect(Collectors.toList()));
        return response;
    }
}
