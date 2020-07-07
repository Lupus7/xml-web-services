package carRent.service;

import carRent.model.Booking;
import carRent.model.Bundle;
import carRent.model.RequestState;
import carRent.model.dto.AdClientDTO;
import carRent.model.dto.BookingDTO;
import carRent.model.dto.GetBundleDTO;
import carRent.proxy.CarsAdsProxy;
import carRent.proxy.UserProxy;
import carRent.repository.BookingRepository;
import carRent.repository.BundleRepository;
import com.car_rent.agent_api.BookingDetails;
import com.car_rent.agent_api.BundleDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.*;

@Service
public class BundleService {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private BundleRepository bundleRepository;

    @Autowired
    UserProxy userProxy;

    @Autowired
    CarsAdsProxy carsAdsProxy;

    public boolean acceptBundleRequest(Long id, String name) {

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(name);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        Optional<Bundle> bundle = bundleRepository.findById(id);
        if (!bundle.isPresent())
            return false;

        List<Long> ads = new ArrayList<>();

        for (Booking b : bundle.get().getBookings()) {
            b.setState(RequestState.PAID);
            bookingRepo.save(b);
            carsAdsProxy.deactivateAd(b.getAd(), name + ";MASTER");
            ads.add(b.getAd());
        }


        for (Long adId : ads) {
            List<Booking> bookings = bookingRepo.findAllByAd(adId);
            for (Booking b : bookings) {
                if (!bundle.get().getBookings().contains(b) && b.getState().equals(RequestState.PENDING)) {
                    b.setState(RequestState.CANCELED);
                    bookingRepo.save(b);
                }

            }

        }

        return true;
    }


    public boolean rejectBundleRequest(Long id, String user) {

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(user);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        Optional<Bundle> bundle = bundleRepository.findById(id);
        if (!bundle.isPresent())
            return false;

        for (Booking b : bundle.get().getBookings()) {
            if (b.getState().equals(RequestState.PENDING)) {
                b.setState(RequestState.CANCELED);
                bookingRepo.save(b);
            }
        }

        return true;
    }

    public boolean cancelBundleRequest(Long id, String name) {

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(name);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return false;

        Long userId = userIdResponse.getBody();

        Optional<Bundle> bundle = bundleRepository.findById(id);
        if (!bundle.isPresent())
            return false;

        for (Booking booking : bundle.get().getBookings()) {
            if (booking.getState() == RequestState.PAID || booking.getState() == RequestState.ENDED || userId != booking.getLoaner())
                return false;

            booking.setState(RequestState.CANCELED);
            bookingRepo.save(booking);
        }

        return true;
    }

    public List<GetBundleDTO> getAllSentBundleRequests(String email) {

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(email);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return new ArrayList<>();

        List<GetBundleDTO> bundleDTOS = new ArrayList<>();
        List<Bundle> bundles = bundleRepository.findAllByLoaner(userIdResponse.getBody());
        for (Bundle bundle : bundles) {
            GetBundleDTO bundleDTO = new GetBundleDTO();
            bundleDTO.setId(bundle.getId());
            bundleDTO.setLoaner(userIdResponse.getBody());
            bundleDTO.setLoanerEmail(email);
            for (Booking booking : bundle.getBookings())
                bundleDTO.getBookings().add(new BookingDTO(booking));

            bundleDTOS.add(bundleDTO);
        }

        return bundleDTOS;

    }

    public Set<GetBundleDTO> getAllReceivedBundleRequests(String name) {

        Set<GetBundleDTO> bundleDTOS = new HashSet<>();
        HashMap<Long, Bundle> bundles = new HashMap<>();

        ResponseEntity<Long> userIdResponse = userProxy.getUserId(name);
        if (userIdResponse == null || userIdResponse.getBody() == null)
            return bundleDTOS;

        ResponseEntity<List<AdClientDTO>> adsResponse = carsAdsProxy.getClientAds(name + ";MASTER");
        if (adsResponse == null || adsResponse.getStatusCode().isError() || adsResponse.getBody() == null)
            return bundleDTOS;

        System.out.println(adsResponse.getBody().size());

        for (AdClientDTO ad : adsResponse.getBody()) {
            List<Booking> bookings = bookingRepo.findAllByAdAndBundleIdNotNull(ad.getAdId());
            for (Booking book : bookings) {
                if (!bundles.containsKey(book.getId()))
                    bundles.put(book.getBundle().getId(), book.getBundle());
            }
        }

        for (Bundle b : bundles.values()) {
            ResponseEntity<String> loaner = userProxy.getUserEmail(b.getLoaner());
            if (loaner != null && loaner.getBody() != null && !loaner.getBody().equals("")) {
                GetBundleDTO getBundleDTO = new GetBundleDTO(b, loaner.getBody());
                bundleDTOS.add(getBundleDTO);
            }
        }

        return bundleDTOS;

    }

    public Set<BundleDetail> mapBundleToDetail(Set<GetBundleDTO> bundlesDtos) throws DatatypeConfigurationException {

        Set<BundleDetail> bundleDetails = new HashSet<>();
        for (GetBundleDTO bundle : bundlesDtos) {
            BundleDetail bundleDetail = new BundleDetail();
            bundleDetail.setId(bundle.getId());
            bundleDetail.setLoaner(bundle.getLoanerEmail());
            for (BookingDTO b : bundle.getBookings()) {
                BookingDetails bookingDetail = new BookingDetails();
                bookingDetail.setAd(b.getAd());
                bookingDetail.setId(b.getId());
                bookingDetail.setPlace(b.getPlace());

                bookingDetail.setCreated(DatatypeFactory.newInstance().newXMLGregorianCalendar(b.getCreated().toLocalDate().toString()));
                bookingDetail.setStartDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(b.getStartDate().toLocalDate().toString()));
                bookingDetail.setEndDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(b.getEndDate().toLocalDate().toString()));

                if (b.getState().equals(RequestState.PENDING))
                    bookingDetail.setState(0);
                else if (b.getState().equals(RequestState.PAID))
                    bookingDetail.setState(1);
                else if (b.getState().equals(RequestState.CANCELED))
                    bookingDetail.setState(2);
                else if (b.getState().equals(RequestState.ENDED))
                    bookingDetail.setState(3);

                bundleDetail.getBooksDetails().add(bookingDetail);
            }

            bundleDetails.add(bundleDetail);
        }

        return bundleDetails;
    }
}
