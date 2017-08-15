package grails.multitenant.database

import grails.mulitenant.database.SenderService
import org.grails.datastore.mapping.multitenancy.web.SessionTenantResolver
import org.springframework.http.HttpStatus

import javax.xml.bind.ValidationException

class SenderController {

    SenderService senderService
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        print params
        print session.getAttribute(SessionTenantResolver.ATTRIBUTE)
        respond senderService.list(params), model: [senderCount: senderService.count()]
    }

    def create() {
        respond new Sender(params)
    }

    def show(Long id) {
        Sender sender = id ? senderService.find(id) : null
        respond sender
    }

    def save(String name) {
        try {
            Sender sender = senderService.save(name);
            flash.message = 'Sender created'
            redirect sender
        } catch(ValidationException e) {
            respond e.errors, view: 'create'
        }
    }

    def update(Long id, String name) {
        try {
            Sender sender = senderService.update(id, name)
            if(sender == null) {
                notFound()
            } else {
                flash.message = 'Sender updated'
                redirect sender
            }
        } catch(ValidationException e) {
            respond e.errors, view: 'edit'
        }
    }

    protected void notFound() {
        fash.message = 'Sender not found'
        redirect uri: '/senders', status: HttpStatus.NOT_FOUND
    }

    def delete(Long id) {
        Sender sender = senderService.delete(id)
        if(sender == null) {
            notFound()
        } else {
            flash.message = 'Sender Deleted'
            redirect action: 'index', method: 'GET'
        }
    }
}
