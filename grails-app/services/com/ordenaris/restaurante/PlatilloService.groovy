package com.ordenaris.restaurante

import grails.gorm.transactions.Transactional

@Transactional
class PlatilloService {
    def mapPlatillo = { Platillo platillo -> 
    def obj = [
        nombre: platillo.nombre,
        costo: platillo.costo/100,
        disponibles: platillo.platillosDisponibles,
        descripcion: platillo.descripcion,
        uuid: platillo.uuid
    ]
    return obj
    }
    def listaPlatillos(){
        //Con este se trae toda la tabla sin excepcion de datos
        //def list = Platillo.list()
        try{
        def list = Platillo.findAllByStatusNotEquals(2)
        return list.collect{ platillo ->
        return mapPlatillo(platillo)
        }
        }
        catch(e){
            return [
                resp: [ success: false, mensaje: e.getMessage() ],
                status: 500
            ]
        }
    }
    def nuevoPlatillo(nombre, descripcion, disponibles, costo, tipoMenu, fechaDisponible ){
        try{
            if(fechaDisponible != null){
            def fecha = "T00:15:43Z"
            fechaDisponible = fechaDisponible + fecha
        }
        costo = costo + 0 + 0
        def tipoM = TipoMenu.findById(tipoMenu)
        println "costo: $costo" 
        if( !tipoM){
            return [
                resp: [ success: false, mensaje: "El id del Tipo de menu no es valido o no existe" ],
                status: 400
            ]
        }
        if( disponibles == null){
            disponibles = -1
        }
            def nuevoP = new Platillo([nombre:nombre, descripcion: descripcion, platillosDisponibles:disponibles, costo:costo, tipoMenu:tipoMenu, fechaDisponible:fechaDisponible]).save(flush:true, failOnError:true)
        return [
            resp:[success: true, data: nuevoP.uuid],
            status: 201
        ]
        }
        catch(e){
            return [
                resp: [ success: false, mensaje: e.getMessage() ],
                status: 500
            ]
        }
    }
    def verPlatillo(uuid){
        try{
        def platilloId = Platillo.findByUuid(uuid)
        
        def info = Platillo.findByStatusNotEqualsStatusAndUuid(2, platilloId.uuid)
        //println info
        
        if(platilloId.status != 1){
            return [
            resp:[success: false, respuesta:"El platillo a sido eliminado con anterioridad o desactivado" ],
            status: 400
        ]
        }
        def respuesta =info.collect{
            platillo ->
            return mapPlatillo(platillo)
        }
        //println respuesta

        return [
            resp:[success: true, respuesta:respuesta ],
            status: 201
        ]
        }
        catch(e){
            return [
                resp: [ success: false, mensaje: e.getMessage() ],
                status: 500
            ]
        }
        
    }
    def editarPlatillo(uuid, nombre, descripcion, costo, disponibles, tipoMenu){
        try{
        def platilloId = Platillo.findByUuid(uuid)
        def tipoMenuId = TipoMenu.findById(tipoMenu)
        if(!uuid){
            return [
            resp:[success: false, respuesta:"El platillo que quiere editar no fue encontrado" ],
            status: 201
        ]        
        }
        if(nombre){
        platilloId.nombre = nombre
        }
        if(descripcion){
        platilloId.descripcion = descripcion

        }
        if(costo){
        platilloId.costo = costo
        }
        if(disponibles != null){
            if(platilloId.platillosDisponibles){
            println platilloId.platillosDisponibles
            if( !platilloId.platillosDisponibles.toString().soloNumeros()) {
                return respond([success:false, mensaje: "Platillos Disponibles debe tener solo numeros"], status: 400)
            }
            if( platilloId.platillosDisponibles < -1 || platilloId.platillosDisponibles > 150){
                return respond([success:false, mensaje: "Platillos Disponible no debe de ser un numero menor a -1"], status: 400)
            }
            if( platilloId.costo.toInteger() > 600 ) {
                return respond([success:false, mensaje: "El costo no debe ser mayor a 600"], status: 400)
            }
        }
        platilloId.platillosDisponibles = disponibles
        }
        if(tipoMenu){
        platilloId.tipoMenu = tipoMenuId
        }
        
        def respuesta = platilloId.save()
        println "------------------"
        println respuesta
        
        return [
            resp:[success: true, respuesta:respuesta ],
            status: 201
        ]
        }
        catch(e){
        return [
                resp: [ success: false, mensaje: e.getMessage() ],
                status: 500
            ]
        }
    }
    def editarEstatusTipo(uuid, estatus){
        try{/*
        def tipoM = TipoMenu.findById(tipoMenu)
        if( !tipoM){
            return [
                resp: [ success: false, mensaje: "El id del Tipo de menu no es valido" ],
                status: 400
            ]
        }
        */
        def data = Platillo.findByUuid(uuid)
        data.status = estatus
        data.save(flush:true, failOnError:true)
        return [
            resp:[success: true, data:data.uuid ],
            status: 201
        ]
        }
        catch(e){
        return [
                resp: [ success: false, mensaje: e.getMessage() ],
                status: 500
            ]
        }
    }
}