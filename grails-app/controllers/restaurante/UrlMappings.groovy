package restaurante

class UrlMappings {

    static mappings = {
        group "/menu", {
            group "/tipo", {
                post "/nuevo"(controller: "menu", action: "nuevoTipo")
                get "/lista"(controller: "menu", action: "listaTipos")
                get "/ver"(controller: "menu", action: "paginarTipos")
                group "/$uuid", {
                    get "/informacion"(controller: "menu", action: "informacionTipo")
                    patch "/editar"(controller: "menu", action: "editarTipo")
                    patch "/activar"(controller: "menu", action: "editarEstatusTipo"){
                        estatus = 1
                    }
                    patch "/desactivar"(controller: "menu", action: "editarEstatusTipo") {
                        estatus = 0
                    }
                    delete "/eliminar"(controller: "menu", action: "editarEstatusTipo") {
                        estatus = 2
                    }
                }
            }
            group "/platillos", {
            get "/lista" (controller:"platillo", action:"listaPlatillos")
            post "/nuevoPlatillo" (controller:"platillo", action:"nuevoPlatillo")
            group "/$uuid", {
                get "/info" (controller: "platillo", action:"verPlatillo")
                patch "/editar" (controller: "platillo", action:"editarPlatillo")
                patch "/activar"(controller: "platillo", action: "editarEstatusTipo"){
                        estatus = 1
                }
                patch "/desactivar"(controller: "platillo", action: "editarEstatusTipo"){
                        estatus = 0
                }
                delete "/eliminar"(controller: "platillo", action: "editarEstatusTipo"){
                        estatus = 2
                }
            }
        }
        }
        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
