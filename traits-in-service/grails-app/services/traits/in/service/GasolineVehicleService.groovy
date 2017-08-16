package traits.in.service

import grails.gorm.transactions.Transactional

@Transactional
trait GasolineVehicleService {

    def drive(GasolineVehicle gasolineVehicle) {
        return 'polluting environment of company: ' + gasolineVehicle.company + ' with charge tank capacity: ' + gasolineVehicle.tankCapacity
    }
}
