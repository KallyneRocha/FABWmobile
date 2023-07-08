package com.moveis.new_fabw.dto

data class GenericResponseDTO < T : Any>(
    var Sucesso: Boolean,
    var Documentos: List<T>?,
    var Mensagem: String?
)