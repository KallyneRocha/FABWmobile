package com.moveis.new_fabw.dto

import com.google.firebase.firestore.DocumentSnapshot

data class FirestoreResponseDTO(
    var Sucesso: Boolean,
    var Documentos: List<DocumentSnapshot>?,
    var Mensagem: String?
)