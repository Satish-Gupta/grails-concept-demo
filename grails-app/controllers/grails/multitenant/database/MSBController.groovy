package grails.multitenant.database

import grails.gorm.transactions.ReadOnly
import groovy.transform.CompileStatic
import org.grails.datastore.mapping.multitenancy.web.SessionTenantResolver

//@CompileStatic
class MSBController {

    @ReadOnly
    def index() {
        render view: '/MSB/index', model: [MSBList: MSB.list()]
    }


    @ReadOnly
    def show(Long id) {
        MSB msb = MSB.where {
            id == id
        }.first()
        if(msb) {
            session.setAttribute(SessionTenantResolver.ATTRIBUTE, msb.name.toLowerCase())
            redirect controller: 'sender'
        } else {
            render status: 404
        }
    }
}
