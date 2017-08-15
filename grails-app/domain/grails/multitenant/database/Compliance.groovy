package grails.multitenant.database

import grails.gorm.MultiTenant

class Compliance implements MultiTenant<Compliance> {
    BigDecimal limit

    static constraints = {
    }
}
