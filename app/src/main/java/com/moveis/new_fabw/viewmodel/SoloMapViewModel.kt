package com.moveis.new_fabw.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moveis.new_fabw.dto.FirestoreResponseDTO
import com.moveis.new_fabw.dto.GenericResponseDTO
import com.moveis.new_fabw.model.Org
import com.moveis.new_fabw.model.OrgRepo
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch

class SoloMapViewModel : ViewModel() {
    private var repository: OrgRepo = OrgRepo()
    private var _mapa = MutableLiveData<GenericResponseDTO<Org>>()
    val mapa: LiveData<GenericResponseDTO<Org>> = _mapa

    fun ObterDenuncias() {
        viewModelScope.launch {
            val response = repository.ObterTodos(Org::class.java.simpleName)
            var orgs = mutableListOf<Org>()

            if(response.Sucesso) {
                response.Documentos?.forEach { documento ->
                    orgs.add(documento.toObject<Org>() as Org)
                }
                _mapa.value = GenericResponseDTO(response.Sucesso, orgs, response.Mensagem)

            } else {
                _mapa.value = GenericResponseDTO(response.Sucesso, null, response.Mensagem)
            }
        }
    }
}
