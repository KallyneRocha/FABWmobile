package com.moveis.new_fabw.adapter

import com.moveis.new_fabw.model.Org
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moveis.new_fabw.databinding.OrgBinding

class OrgAdapter(var orgList: List<Org>)
    : RecyclerView.Adapter<OrgAdapter.OrgHolder>() {

    class OrgHolder(val binding: OrgBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(org: Org) {
            binding.txtViewName.text = org.Name
            binding.txtViewCategories.text = org.Categories
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrgHolder {
        val binding = OrgBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return OrgHolder(binding)
    }

    override fun onBindViewHolder(holder: OrgHolder, position: Int) {
        holder.bind(orgList.get(position))
    }

    override fun getItemCount(): Int {
        return orgList.size
    }

    fun setOrgs(orgs: List<Org>) {
        orgList = orgs
        notifyDataSetChanged()
    }

}