package traits.in.service

import grails.gorm.transactions.Transactional

@Transactional
class VehicleService implements GasolineVehicleService, ElectricVehicleService {

    def drive(Vehicle vehicle) {

    }
}
