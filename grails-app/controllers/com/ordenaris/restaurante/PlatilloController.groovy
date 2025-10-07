package com.ordenaris.restaurante


import grails.rest.*
import grails.converters.*

class PlatilloController {
	static responseFormats = ['json', 'xml']

    def PlatilloService

    def mapPlatillo = {
        tipo, lista -> 
        def obj = [
            nombre: tipo.nombre,
            descripcion: tipo.descripcion,
            costo: tipo.costo,
            platillosDisponibles:tipo.platillosDisponibles
        ]
    }

    def listaPlatillos(){
        println "------------------"
        println params.tipo
        def respuesta = PlatilloService.listaPlatillos(params.tipo)
        return respond([
            success:true, data:respuesta], status: 200
        )
    }

    def nuevoPlatillo(){
        def data = request.JSON
        println data
        //def list = TipoMenu.list()
        //println list.id
        //println data.tipoMenu
        
        if(!data.nombre){
            return respond([success:false, mensaje: "El nombre es obligatorio"], status: 400)
        }
        if(!data.costo){
            return respond([success:false, mensaje: "El costo es obligatorio"], status: 400)
        }
        if(!data.descripcion){
            return respond([success:false, mensaje: "La descripcion es obligatoria"], status: 400)
        }
        if(!data.tipoMenu){
            return respond([success:false, mensaje: "El TipoMenu es obligatorio"], status: 400)
        }
        if(data.nombre.soloNumeros() ) {
            return respond([success:false, mensaje: "El nombre no solo debe de contener numeros"], status: 400)
        }
        if( !data.costo.soloNumeros() ) {
            return respond([success:false, mensaje: "El costo debe tener solo numeros"], status: 400)
        }
        if( data.costo.toInteger() > 600 ) {
            return respond([success:false, mensaje: "El costo no debe ser mayor a 600"], status: 400)
        }
        if(data.disponibles){
        if( !data.disponibles.soloNumeros()) {
            return respond([success:false, mensaje: "Platillos Disponibles debe tener solo numeros"], status: 400)
        }
        if( data.disponibles < -1 || data.disponibles > 150){
            return respond([success:false, mensaje: "Platillos Disponible no debe de ser un numero menor a -1"], status: 400)
        }
        }
        if(!data.tipoMenu.soloNumeros() ) {
            return respond([success:false, mensaje: "El menuTipo debe tener solo numeros"], status: 400)
        }
        if( data.nombre.size() > 80 ) {
            return respond([success:false, mensaje: "El nombre no puede ser tan largo"], status: 400)
        }
        if( data.descripcion.size() > 100 ) {
            return respond([success:false, mensaje: "La descripcion no puede exceder el limite de 100 caracteres"], status: 400)
        }
        if( !data.fechaDisponible){
            request.status = 1
        }else{
            request.status = 0
        }
        def respuesta = PlatilloService.nuevoPlatillo(data.nombre, data.descripcion, data?.disponibles, data.costo, data.tipoMenu, data.fechaDisponible, request.status)
        //println respuesta
        return respond( respuesta.resp, status: respuesta.status)
    }

    def verPlatillo(){
        if( params.uuid.size() != 32 || !params.uuid ) {
            return respond([success:false, mensaje: "El uuid es invalido o no existe"], status: 400)
        }
        def respuesta = PlatilloService.verPlatillo(params.uuid)
        return respond(respuesta.resp, status: respuesta.status)
    }

    def editarPlatillo(){
        def data = request.JSON
        //println data
        if( data.nombre.soloNumeros() ) {
            return respond([success:false, mensaje: "El nombre debe contener letras y no solo numeros"], status: 400)
        }
        if( data.nombre.size() > 80 ) {
            return respond([success:false, mensaje: "El nombre no puede ser tan largo"], status: 400)
        }
        if( params.uuid.size() != 32 ) {
            return respond([success:false, mensaje: "El uuid es invalido"], status: 400)
        }
        if( data.descripcion.size() > 100 ) {
            return respond([success:false, mensaje: "La descripcion no puede ser tan largo"], status: 400)
        }
        //println "-----"
        if( data.tipoMenu){
            if(!data.tipoMenu.toString().soloNumeros() ) {
            return respond([success:false, mensaje: "El menuTipo debe tener solo numeros"], status: 400)
        }
        }
        def respuesta = PlatilloService.editarPlatillo(data.uuid, data?.nombre, data?.descripcion, data?.costo, data?.disponibles, data?.tipoMenu)
        return respond(respuesta.resp, status: respuesta.status)
    }

    def editarEstatusTipo(){
        println "hola"
        if( params.uuid.size() != 32 ) {
            return respond([success:false, mensaje: "El uuid es invalido"], status: 400)
        }
        println "Hola"
        println params.estatus
        def respuesta = PlatilloService.editarEstatusTipo(params.uuid, params.estatus)
        return respond(respuesta.resp, status: respuesta.status)
    }
    def paginarPlatillos(){
        if( !params.pagina ) {
            return respond([success:false, mensaje: "La pagina no puede ir vacio"], status: 400)
        }
        if( !params.pagina.soloNumeros() ) {
            return respond([success:false, mensaje: "La pagina debe contener solo numeros"], status: 400)
        }
        
        if( !params.columnaOrden ) {
            return respond([success:false, mensaje: "El columnaOrden no puede ir vacio"], status: 400)
        }
        if( !(params.columnaOrden in ["nombre", "status", "dateCreated"]) ) {
            return respond([success:false, mensaje: "El columnaOrden solo puede ser: nombre, status, dateCreated"], status: 400)
        }

        if( !params.orden ) {
            return respond([success:false, mensaje: "El orden no puede ir vacio"], status: 400)
        }
        if( !(params.orden in ["asc", "desc"]) ) {
            return respond([success:false, mensaje: "El orden solo puede ser: asc, desc"], status: 400)
        }

        if( !params.max ) {
            return respond([success:false, mensaje: "El max no puede ir vacio"], status: 400)
        }
        if( !params.max.soloNumeros() ) {
            return respond([success:false, mensaje: "El max debe contener solo numeros"], status: 400)
        }
        if( !(params.max.toInteger() in [ 2, 5, 10, 20, 50, 100 ]) ) {
            return respond([success:false, mensaje: "El max puede ser solo: 2, 5, 10, 20, 50, 100"], status: 400)
        }
        
        def respuesta = PlatilloService.paginarPlatillos( params.pagina.toInteger(), params.columnaOrden, params.orden, params.max.toInteger(), params.estatus?.toInteger(), params.query )
        return respond( respuesta.resp, status: respuesta.status )
    }
}