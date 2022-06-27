package com.rdktechnologies.skit.entity

import javax.persistence.*


@Entity
data class Eligibility(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,
        var name: String? = null,
)
