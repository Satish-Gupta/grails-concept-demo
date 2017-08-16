package traits.in.service

import grails.gorm.transactions.Transactional

@Transactional
trait ElectricVehicleService {

    def drive(ElectricVehicle electricVehicle) {
        return 'driving electric vehicle of company: ' + electricVehicle.company + ' with charge: ' + electricVehicle.charge
    }
}
