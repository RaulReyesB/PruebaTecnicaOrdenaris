package restaurante

import com.ordenaris.restaurante.TipoMenu
import com.ordenaris.restaurante.Platillo
import java.util.regex.*
class BootStrap {

    def init = { servletContext ->
        String.metaClass.soloNumeros = {
            def expresion = '^[0-9]*$' 
            def patter = Pattern.compile(expresion)
            def match = patter.matcher(delegate)
            return match.matches()
        }

        if( TipoMenu.count() == 0 ) {
            new TipoMenu([ nombre: "Desayuno" ]).save(flush:true)
            new TipoMenu([ nombre: "Comida" ]).save(flush:true)
            new TipoMenu([ nombre: "Especiales" ]).save(flush:true)
            new TipoMenu([ nombre: "Postres" ]).save(flush:true)
            new TipoMenu([ nombre: "Bebidas" ]).save(flush:true)
        }
        if(Platillo.count() == 0){
            
            new Platillo(nombre: "Tacos al Pastor", descripcion: "Orden de 3 tacos con todo", costo: 60, platillosDisponibles: 30, tipoMenu: 2).save(failOnError: true)
            new Platillo(nombre: "Burritos de Bistec", descripcion: "Burrito grande con arroz y frijoles", costo: 75, platillosDisponibles: 20, tipoMenu: 2).save(failOnError: true)
            new Platillo(nombre: "Enchiladas Suizas", descripcion: "Orden de 4 enchiladas con pollo y queso", costo: 80, platillosDisponibles: 15, tipoMenu: 2).save(failOnError: true)
            new Platillo(nombre: "Quesadillas de Champiñones", descripcion: "3 quesadillas de maíz con queso oaxaca", costo: 55, platillosDisponibles: 25, tipoMenu: 2).save(failOnError: true)

            new Platillo(nombre: "Huevos con Jamón", descripcion: "Huevos revueltos acompañados de frijoles", costo: 65, platillosDisponibles: 20, tipoMenu: 1).save(failOnError: true)
            new Platillo(nombre: "Chilaquiles Rojos con Pollo", descripcion: "Con queso, crema y cebolla", costo: 85, platillosDisponibles: 18, tipoMenu: 1).save(failOnError: true)

            new Platillo(nombre: "Flan Napolitano", descripcion: "Rebanada de flan casero con caramelo", costo: 40, platillosDisponibles: 15, tipoMenu: 4).save(failOnError: true)
            new Platillo(nombre: "Pastel de Chocolate", descripcion: "Deliciosa rebanada de pastel húmedo", costo: 50, platillosDisponibles: 10, tipoMenu: 4).save(failOnError: true)

            new Platillo(nombre: "Agua de Horchata 1L", descripcion: "Agua fresca de arroz con canela", costo: 35, platillosDisponibles: 40, tipoMenu: 5).save(failOnError: true)

            new Platillo(nombre: "Mole Poblano con Pollo", descripcion: "Platillo especial de la casa con arroz", costo: 120, platillosDisponibles: 12, tipoMenu: 3).save(failOnError: true)
        }
        
        

    }
    def destroy = {
    }
}
