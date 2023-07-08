package com.moveis.new_fabw.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moveis.new_fabw.dto.FirestoreGenericResponseDTO
import com.moveis.new_fabw.model.Org
import com.moveis.new_fabw.model.OrgRepo
import com.moveis.new_fabw.model.BaseRepository
import kotlinx.coroutines.launch

class AddContentViewModel : ViewModel() {
    private var repository: BaseRepository<Org> = OrgRepo()
    private var _addContent = MutableLiveData<FirestoreGenericResponseDTO>()
    val addOrgContent: LiveData<FirestoreGenericResponseDTO> = _addContent

    fun addOrg(org: Org) {
        viewModelScope.launch {
            try {
                var response = repository.Adicionar(org)

                _addContent.value = response
            }
            catch (e: Exception) {

            }
        }
    }
}