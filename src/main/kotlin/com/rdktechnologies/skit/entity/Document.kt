package com.rdktechnologies.skit.entity

import javax.persistence.*

@Entity
@Table(name = "document")
data class Document(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var type:String?=null,
    var documentUrl:String?=null
)