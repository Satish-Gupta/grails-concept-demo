package traits.in.service

import grails.test.mixin.*
import spock.lang.*

@TestFor(ElectricVehicleController)
@Mock(ElectricVehicle)
class ElectricVehicleControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null

        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
        assert false, "TODO: Provide a populateValidParams() implementation for this generated test suite"
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.electricVehicleList
            model.electricVehicleCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.electricVehicle!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def electricVehicle = new ElectricVehicle()
            electricVehicle.validate()
            controller.save(electricVehicle)

        then:"The create view is rendered again with the correct model"
            model.electricVehicle!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            electricVehicle = new ElectricVehicle(params)

            controller.save(electricVehicle)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/electricVehicle/show/1'
            controller.flash.message != null
            ElectricVehicle.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def electricVehicle = new ElectricVehicle(params)
            controller.show(electricVehicle)

        then:"A model is populated containing the domain instance"
            model.electricVehicle == electricVehicle
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def electricVehicle = new ElectricVehicle(params)
            controller.edit(electricVehicle)

        then:"A model is populated containing the domain instance"
            model.electricVehicle == electricVehicle
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/electricVehicle/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def electricVehicle = new ElectricVehicle()
            electricVehicle.validate()
            controller.update(electricVehicle)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.electricVehicle == electricVehicle

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            electricVehicle = new ElectricVehicle(params).save(flush: true)
            controller.update(electricVehicle)

        then:"A redirect is issued to the show action"
            electricVehicle != null
            response.redirectedUrl == "/electricVehicle/show/$electricVehicle.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/electricVehicle/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def electricVehicle = new ElectricVehicle(params).save(flush: true)

        then:"It exists"
            ElectricVehicle.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(electricVehicle)

        then:"The instance is deleted"
            ElectricVehicle.count() == 0
            response.redirectedUrl == '/electricVehicle/index'
            flash.message != null
    }
}
