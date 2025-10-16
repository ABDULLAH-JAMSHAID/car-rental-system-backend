package Service;

import DTO.CarDTO.CarRequestDTO;
import Enums.CarStatus;
import Handler.AppException;
import Repository.CarRepository;

import java.util.List;

public class CarService {

    private final CarRepository carRepository=new CarRepository();


    public CarRequestDTO addCar(CarRequestDTO body) {

        return carRepository.addCar(body);

    }

    public CarRequestDTO updateCar(CarRequestDTO body,String registration_no) {

        CarRequestDTO car=carRepository.findByRegistrationNo(registration_no);
        if (car==null){
            return null;
        }

        if (!(body.getName()==null || body.getName().isEmpty())){
            car.setName(body.getName());
        }
        if (!(body.getType()==null || body.getType().isEmpty())){
            car.setType(body.getType());
        }
        if (!(body.getCapacity()==null)){
            car.setCapacity(body.getCapacity());
        }
        if (!(body.getFuel_capacity()==null || body.getFuel_capacity().isEmpty())){
            car.setFuel_capacity(body.getFuel_capacity());
        }
        if (!(body.getTransmission()==null || body.getTransmission().isEmpty())){
            car.setTransmission(body.getTransmission());
        }
        if (!(body.getDescription()==null || body.getDescription().isEmpty())){
            car.setTransmission(body.getTransmission());
        }
        if (!(body.getPrize_per_day()==null)){
            car.setPrize_per_day(body.getPrize_per_day());
        }
        if (!(body.getStatus()==null || body.getStatus().name().isEmpty())){
            car.setStatus(body.getStatus());
        }

        return carRepository.updateCar(car,registration_no);
    }

    public CarRequestDTO getCarByRegistrationNo(String registrationNo) {

        return carRepository.findByRegistrationNo(registrationNo);
    }

    public List<CarRequestDTO> getAllCars() {
        return carRepository.getAllCars();
    }

    public CarRequestDTO deleteCarByRegistrationNo(String registrationNo){
        CarRequestDTO carRequestDTO=carRepository.findByRegistrationNo(registrationNo);
        if (carRequestDTO==null){
                return null;
        }
        if (carRequestDTO.getStatus().name().equals("RENTED")){
            throw new AppException(400,"Car is currently rented and cannot be deleted");
        }
        carRequestDTO.setStatus(CarStatus.DELETED);
        return carRepository.updateCar(carRequestDTO,registrationNo);
    }
}

