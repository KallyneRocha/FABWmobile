package com.moveis.new_fabw.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moveis.new_fabw.dto.GenericResponseDTO
import com.moveis.new_fabw.model.Org
import com.moveis.new_fabw.model.OrgRepo
import com.moveis.new_fabw.model.BaseRepository
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private var repository: BaseRepository<Org> = OrgRepo()
    private var _home = MutableLiveData<GenericResponseDTO<Org>>()
    val home: LiveData<GenericResponseDTO<Org>> = _home

    fun GetOrgs() {
        viewModelScope.launch {
            val response = repository.ObterTodos(Org::class.java.simpleName)
            var orgs = mutableListOf<Org>()

            if(response.Sucesso)
            {
                response.Documentos?.forEach { documento ->
                    orgs.add(documento.toObject<Org>() as Org)
                }

                _home.value = GenericResponseDTO(response.Sucesso, orgs, response.Mensagem)
            } else {
                _home.value = GenericResponseDTO(response.Sucesso, null, response.Mensagem)
            }
        }
    }
}