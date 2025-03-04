package com.tevs.myapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PhonesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mPhonesList: ArrayList<PhoneModel> = ArrayList()

    fun setupPhones(phonesList: ArrayList<PhoneModel>) {
        mPhonesList.clear()
        mPhonesList.addAll(phonesList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PhonesViewHolder) {
            holder.bind(mPhones = mPhonesList[position])
        }
    }

    override fun getItemCount(): Int {
        return mPhonesList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.recyclerview_item, parent, false)
        return PhonesViewHolder(itemView)
    }
}

class PhonesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val tvPhoneName: TextView = itemView.findViewById(R.id.tv_phone_name)
    private val tvPrice: TextView = itemView.findViewById(R.id.tv_price)
    private val tvDate: TextView = itemView.findViewById(R.id.tv_date)
    private val tvScore: TextView = itemView.findViewById(R.id.tv_score)

    fun bind(mPhones: PhoneModel) {
        tvPhoneName.text = mPhones.name
        tvPrice.text = mPhones.price
        tvDate.text = mPhones.date
        tvScore.text = mPhones.score
    }
}