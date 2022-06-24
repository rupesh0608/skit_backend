package com.rdktechnologies.skit.entity

import javax.persistence.*

@Entity
data class ServiceModule(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var image:String?=null,
    var name: String? = null,
    var isActive:Boolean?=false,
    @OneToMany(cascade = [CascadeType.ALL])
    var services:MutableList<Service>?= mutableListOf(),
)