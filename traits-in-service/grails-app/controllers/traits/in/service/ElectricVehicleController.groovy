package traits.in.service

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ElectricVehicleController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def vehicleService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ElectricVehicle.list(params), model:[electricVehicleCount: ElectricVehicle.count()]
    }

    def show(ElectricVehicle electricVehicle) {
        render vehicleService.drive(electricVehicle)
    }

    def create() {
        respond new ElectricVehicle(params)
    }

    @Transactional
    def save(ElectricVehicle electricVehicle) {
        if (electricVehicle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (electricVehicle.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond electricVehicle.errors, view:'create'
            return
        }

        electricVehicle.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'electricVehicle.label', default: 'ElectricVehicle'), electricVehicle.id])
                redirect electricVehicle
            }
            '*' { respond electricVehicle, [status: CREATED] }
        }
    }

    def edit(ElectricVehicle electricVehicle) {
        respond electricVehicle
    }

    @Transactional
    def update(ElectricVehicle electricVehicle) {
        if (electricVehicle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (electricVehicle.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond electricVehicle.errors, view:'edit'
            return
        }

        electricVehicle.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'electricVehicle.label', default: 'ElectricVehicle'), electricVehicle.id])
                redirect electricVehicle
            }
            '*'{ respond electricVehicle, [status: OK] }
        }
    }

    @Transactional
    def delete(ElectricVehicle electricVehicle) {

        if (electricVehicle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        electricVehicle.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'electricVehicle.label', default: 'ElectricVehicle'), electricVehicle.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'electricVehicle.label', default: 'ElectricVehicle'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
