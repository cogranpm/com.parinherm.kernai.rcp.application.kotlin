package com.parinherm.kernai.data.entity

data class Script  (var entityKey: EntityKey<Long> = EntityKey(), var name: String = "", var body: String = "", var engine: String = "")  {
}