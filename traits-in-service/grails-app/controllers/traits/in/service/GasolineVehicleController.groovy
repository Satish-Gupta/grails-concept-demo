package traits.in.service

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class GasolineVehicleController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def vehicleService
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond GasolineVehicle.list(params), model:[gasolineVehicleCount: GasolineVehicle.count()]
    }

    def show(GasolineVehicle gasolineVehicle) {
        render vehicleService.drive(gasolineVehicle)
    }

    def create() {
        respond new GasolineVehicle(params)
    }

    @Transactional
    def save(GasolineVehicle gasolineVehicle) {
        if (gasolineVehicle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (gasolineVehicle.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond gasolineVehicle.errors, view:'create'
            return
        }

        gasolineVehicle.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'gasolineVehicle.label', default: 'GasolineVehicle'), gasolineVehicle.id])
                redirect gasolineVehicle
            }
            '*' { respond gasolineVehicle, [status: CREATED] }
        }
    }

    def edit(GasolineVehicle gasolineVehicle) {
        respond gasolineVehicle
    }

    @Transactional
    def update(GasolineVehicle gasolineVehicle) {
        if (gasolineVehicle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (gasolineVehicle.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond gasolineVehicle.errors, view:'edit'
            return
        }

        gasolineVehicle.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'gasolineVehicle.label', default: 'GasolineVehicle'), gasolineVehicle.id])
                redirect gasolineVehicle
            }
            '*'{ respond gasolineVehicle, [status: OK] }
        }
    }

    @Transactional
    def delete(GasolineVehicle gasolineVehicle) {

        if (gasolineVehicle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        gasolineVehicle.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'gasolineVehicle.label', default: 'GasolineVehicle'), gasolineVehicle.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'gasolineVehicle.label', default: 'GasolineVehicle'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
