package grails.mulitenant.database

import grails.gorm.multitenancy.CurrentTenant
import grails.gorm.services.Join
import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import grails.multitenant.database.Compliance
import grails.multitenant.database.Sender
import groovy.transform.CompileStatic

@Service(Sender)
@CurrentTenant
@CompileStatic
abstract class SenderService {

    def serviceMethod() {

    }

    @Join('beneficiaries')
    abstract List<Sender> list(Map args )

    abstract Integer count()

    @Join('beneficiaries')
    abstract Sender find(Serializable id)

    abstract Sender save(String name)

    abstract Sender delete(Serializable id)

    @Transactional
    Sender update( Serializable id,
                    String name) {
        Sender sender = find(id)
        if (sender != null) {
            sender.name = name
            sender.save(failOnError:true)
        }
        sender
    }
}
