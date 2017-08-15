package grails.multitenant.database

import grails.gorm.MultiTenant

class Sender implements MultiTenant<Sender>{

    String name

    static hasMany = [beneficiaries: Beneficiary]
    static constraints = {
    }
}
