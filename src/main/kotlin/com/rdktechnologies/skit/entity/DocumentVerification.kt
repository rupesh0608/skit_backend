package com.rdktechnologies.skit.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*


@Entity
@JsonIgnoreProperties(
        "user",
)
data class DocumentVerification(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,
        @ManyToOne(cascade = [CascadeType.ALL])
        var user:User?=null,
        @OneToMany(cascade = [CascadeType.ALL])
        var documents:Collection<Document>?=null,
        var status:String?="none",
        var message:String?="none"
)
