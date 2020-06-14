package CarsAdsApp.proxy;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "rent")
public interface CarRentProxy {
}
