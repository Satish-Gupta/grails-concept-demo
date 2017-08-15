package grails.multitenant.database

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: "MSB")
        "/senders"(controller: "sender")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
