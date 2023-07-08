package com.moveis.new_fabw.model

import com.moveis.new_fabw.dto.FirestoreGenericResponseDTO
import com.moveis.new_fabw.dto.FirestoreResponseDTO
import com.moveis.new_fabw.helper.ConverterHelper
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.lang.Exception

open class BaseRepository < T : Any >() {
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var converter: ConverterHelper = ConverterHelper()

    suspend fun < T : Any > Adicionar(objeto: T) : FirestoreGenericResponseDTO
    {
        var response = FirestoreGenericResponseDTO(false, "")
        var tipoObjeto = objeto::class.java.simpleName

        try {
            var task = firestore.collection(tipoObjeto).add(objeto).await()
            response.Sucesso = true
            response.Mensagem = "$tipoObjeto adicionado!"
        } catch(e: Exception) {
            response.Sucesso = false
            response.Mensagem = e.message.toString()
        }

        return response
    }

    suspend fun ObterTodos(nomeColecao: String) : FirestoreResponseDTO {
        var response = FirestoreResponseDTO(false, null, null)

        try {
            var task = firestore.collection(nomeColecao).get().await()
            response.Sucesso = true
            response.Documentos = task.documents
        } catch (e: Exception) {
            response.Sucesso = false
            response.Mensagem = e.message
        }

        return response
    }
}