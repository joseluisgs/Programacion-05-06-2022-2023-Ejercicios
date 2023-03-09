package models

import java.sql.Date

interface IPaciente {
    val dni:String
    val nombre:String
    val fechaNacimiento:String
    val fechaIngreso:String
    var fechaAlta:String?
    var tipoPaciente:TipoPaciente
}

